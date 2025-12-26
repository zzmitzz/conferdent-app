package com.ptit_booth_chekin.project.ui.screen.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.base.BaseViewModelMVVM
import com.ptit_booth_chekin.project.core.data.IPersistentStorage
import com.ptit_booth_chekin.project.data.auth.remote.LoginResponseDetail
import com.ptit_booth_chekin.project.data.auth.remote.RegisterResponseDetail
import com.ptit_booth_chekin.project.data.auth.repository.AuthRepository
import com.ptit_booth_chekin.project.data.auth.repository.UserRepository
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.ui.screen.home.screen_setting.SettingVMState
import com.ptit_booth_chekin.project.ui.screen.home.screen_setting.UserProfile
import com.ptit_booth_chekin.project.utils.Constants
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import com.ptit_booth_chekin.project.utils.UserAccount
import com.ptit_booth_chekin.project.utils.parseLocalDateToFormat
import com.ptit_booth_chekin.project.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import javax.inject.Inject


@Serializable
sealed class LoginScreenVMState {
    object Initial : LoginScreenVMState()
    object Loading : LoginScreenVMState()
    data class Success(val data: LoginResponseDetail) : LoginScreenVMState()
    data class Error(val message: String) : LoginScreenVMState()
}

sealed class RegisterScreenVMState {
    object Initial : RegisterScreenVMState()
    object Loading : RegisterScreenVMState()
    data class Success(val data: RegisterResponseDetail) : RegisterScreenVMState()
    data class ErrorInput(
        val name: String? = null,
        val email: String? = null,
        val password: String? = null,
        val confirmPassword: String? = null
    ) : RegisterScreenVMState()

    data class Error(val message: String) : RegisterScreenVMState()
}

@HiltViewModel
class AuthenticationVM
@Inject constructor(
    private val authRepository: AuthRepository,
    private val persistentStorage: IPersistentStorage,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _uiState: MutableStateFlow<LoginScreenVMState> =
        MutableStateFlow(LoginScreenVMState.Initial)

    val uiState: StateFlow<LoginScreenVMState> = _uiState.asStateFlow()

    private var _registerState: MutableStateFlow<RegisterScreenVMState> =
        MutableStateFlow(RegisterScreenVMState.Initial)

    val registerState: StateFlow<RegisterScreenVMState> = _registerState.asStateFlow()


    private fun updateState(state: LoginScreenVMState) {
        _uiState.value = state
    }

    fun checkSavedAccount(){
        viewModelScope.launch {
            val email = persistentStorage.readKeySuspend(Constants.USER_NAME)
            val password = persistentStorage.readKeySuspend(Constants.USER_PASSWORD)
            if (email != null && password != null) {
                doLogin(email, password)
            }
        }
    }

    fun doLogin(email: String, password: String) {
        updateState(LoginScreenVMState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = authRepository.doLogin(email, password)
                delay(2000L)
                when (result) {
                    is APIResult.Success -> {
                        persistentStorage.saveKeySuspend(Constants.USER_TOKEN, result.data.accessToken)
                        persistentStorage.saveKeySuspend(Constants.USER_NAME, email)
                        persistentStorage.saveKeySuspend(Constants.USER_PASSWORD, password)
                        getUserProfile()
                        updateState(LoginScreenVMState.Success(result.data))
                    }

                    is APIResult.Error -> {
                        updateState(LoginScreenVMState.Error(result.message))
                    }
                }
            }catch (e: Exception){
                updateState(LoginScreenVMState.Error(e.message ?: "Unknown error"))
            }

        }
    }

    private fun updateRegisterState(state: RegisterScreenVMState) {
        _registerState.value = state
    }

    fun doRegister(fullName: String, email: String, password: String, confirmPassword: String) {
        updateRegisterState(RegisterScreenVMState.Loading)
        viewModelScope.launch(Dispatchers.IO){
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                updateRegisterState(
                    RegisterScreenVMState.ErrorInput(
                        name = if (fullName.isEmpty()) "Name is required" else null,
                        email = if (email.isEmpty()) "Email is required" else null,
                        password = if (password.isEmpty()) "Password is required" else null,
                        confirmPassword = if (confirmPassword != password) "Confirm password and password not match" else null,
                    )
                )
                return@launch
            }
            delay(2000L)
            val result = authRepository.doRegister(email, password, fullName)
            when (result) {
                is APIResult.Success -> {
                    persistentStorage.saveKeySuspend(Constants.USER_TOKEN, result.data.accessToken)
                    updateRegisterState(RegisterScreenVMState.Success(result.data))
                }

                is APIResult.Error -> {
                    updateRegisterState(RegisterScreenVMState.Error(result.message))
                }
            }

        }
    }

    suspend fun getUserProfile() {
        withContext(Dispatchers.IO){
            val result = userRepository.getMe()
            if (result is APIResult.Success) {
                val user = result.data

                UserAccount.userProfile = UserProfile(
                    email = user.email ?: "Chưa cung cấp",
                    phone = user.phone ?: "Chưa cung cấp",
                    fullName = user.fullName,
                    dob = user.dob?.let {
                        parseLocalDateToFormat(
                            parseTimeFromServer(user.dob),
                            DateTimeFormatPattern.PATTERN_SERVER
                        )
                    } ?: "Chưa cung cấp",
                    address = user.address ?: "Chưa cung cấp",
                    bio = user.bio?: "Chưa cung cấp",
                    avatarURL = user.avatar
                )
            }
        }
    }
}