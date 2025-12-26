package com.ptit_booth_chekin.project.ui.screen.home.screen_setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.core.data.IPersistentStorage
import com.ptit_booth_chekin.project.data.auth.repository.AuthRepository
import com.ptit_booth_chekin.project.data.auth.repository.UserRepository
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.utils.Constants
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import com.ptit_booth_chekin.project.utils.UserAccount
import com.ptit_booth_chekin.project.utils.parseLocalDateToFormat
import com.ptit_booth_chekin.project.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UserProfile(
    val email: String?,
    val phone: String?,
    val fullName: String?,
    val dob: String?,
    val address: String?,
    val bio: String?,
    val avatarURL: String?,
)


sealed class SettingVMState {
    object Loading : SettingVMState()
    data class Success(
        val userProfile: UserProfile,
        val isNotificationEnabled: Boolean = false,
        val isAutoFilled: Boolean = false,
        val isEditMode: Boolean = false
    ) : SettingVMState()

    data class Error(val message: String) : SettingVMState()
}


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val persistentStorage: IPersistentStorage,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val authInterceptor: com.ptit_booth_chekin.project.core.network.AuthInterceptor
) : ViewModel() {

    private val _state = MutableStateFlow<SettingVMState>(SettingVMState.Loading)
    val state: StateFlow<SettingVMState> = _state.asStateFlow()

    init {
        getUserProfile()
    }

    fun logout(
        onLogoutSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _state.value = SettingVMState.Loading
            persistentStorage.saveKeySuspend(Constants.USER_TOKEN, null)
            persistentStorage.saveKeySuspend(Constants.USER_NAME, null)
            persistentStorage.saveKeySuspend(Constants.USER_PASSWORD, null)
            authRepository.doLogout()
            // Clear the cached token from the interceptor to prevent token persistence
            authInterceptor.clearToken()
            UserAccount.userProfile = null
            onLogoutSuccess()
        }
    }

    fun switchEditMode(){
        _state.value = (_state.value as SettingVMState.Success).copy(
            isEditMode = !(_state.value as SettingVMState.Success).isEditMode
        )
    }
    fun updateUserProfile(newData: UserProfile, context: Context){
        viewModelScope.launch(Dispatchers.IO ) {
            _state.value = SettingVMState.Loading
            val result = userRepository.updateProfile(newData,context)
            if (result is APIResult.Success) {
                getUserProfile()
            } else {
                _state.value = SettingVMState.Error((result as APIResult.Error).message)
            }
        }
    }
    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = SettingVMState.Loading
            val result = userRepository.getMe()
            if (result is APIResult.Success) {
                val user = result.data
                _state.value = SettingVMState.Success(
                    userProfile = UserProfile(
                        email = user.email,
                        phone = user.phone,
                        fullName = user.fullName,
                        dob = user.dob?.let {
                            parseLocalDateToFormat(
                                parseTimeFromServer(user.dob),
                                DateTimeFormatPattern.PATTERN_SERVER
                            )
                        },
                        address = user.address ,
                        bio = user.bio,
                        avatarURL = user.avatar
                    ),
                    isNotificationEnabled = false,
                    isAutoFilled = false,
                    isEditMode = false
                )

            } else {
                _state.value = SettingVMState.Error((result as APIResult.Error).message)
            }
        }
    }


}