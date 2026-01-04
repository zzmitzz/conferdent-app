package com.ptit_booth_chekin.project.ui.screen.home.screen_setting

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.CoilUtils.result
import com.google.firebase.messaging.FirebaseMessaging
import com.ptit_booth_chekin.project.core.data.IPersistentStorage
import com.ptit_booth_chekin.project.data.auth.remote.RegistrationDetail
import com.ptit_booth_chekin.project.data.auth.remote.model.UserDevice
import com.ptit_booth_chekin.project.data.auth.repository.AuthRepository
import com.ptit_booth_chekin.project.data.auth.repository.UserRepository
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.utils.Constants
import com.ptit_booth_chekin.project.utils.DateTimeFormatPattern
import com.ptit_booth_chekin.project.utils.UserAccount
import com.ptit_booth_chekin.project.utils.parseLocalDateToFormat
import com.ptit_booth_chekin.project.utils.parseTimeFromServer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val authInterceptor: com.ptit_booth_chekin.project.core.network.AuthInterceptor,
    @ApplicationContext val mContext: Context
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
            authRepository.doLogout()
            // Clear the cached token from the interceptor to prevent token persistence
            authInterceptor.clearToken()
            persistentStorage.saveKeySuspend(Constants.USER_TOKEN, null)
            persistentStorage.saveKeySuspend(Constants.USER_NAME, null)
            persistentStorage.saveKeySuspend(Constants.USER_PASSWORD, null)
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
        viewModelScope.launch {
            _state.value = SettingVMState.Loading
            userRepository.updateProfile(newData, context).fold(
                onSuccess = { refreshUserProfile() },
                onError = { error -> _state.value = SettingVMState.Error(error.message) }
            )
        }
    }

    @SuppressLint("HardwareIds")
    private suspend fun refreshUserProfile() {
        val userResult = userRepository.getMe()

        if (userResult is APIResult.Success) {
            val user = userResult.data

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

        val deviceResult = userRepository.getRegisteredUserDevice()

        userResult.fold(
            onSuccess = { user ->
                val devices = deviceResult.getOrNull() ?: emptyList()
                val deviceName = Settings.Secure.getString(
                    mContext.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                if (!devices.map { it.deviceName }.contains(deviceName)) {
                    registerUserDevice()
                }
                _state.value = SettingVMState.Success(
                    userProfile = user.toUserProfile(),
                    isNotificationEnabled = devices.isNotEmpty(),
                    isAutoFilled = false,
                    isEditMode = false
                )
            },
            onError = { error ->
                _state.value = SettingVMState.Error(error.message.ifEmpty { "Có lỗi xảy ra, vui lòng đăng nhập lại" })
            }
        )
    }

    fun getUserProfile() {
        viewModelScope.launch {
            _state.value = SettingVMState.Loading
            refreshUserProfile()
        }
    }

    private fun RegistrationDetail.toUserProfile() = UserProfile(
        email = email,
        phone = phone,
        fullName = fullName,
        dob = dob?.let {
            parseLocalDateToFormat(
                parseTimeFromServer(dob),
                DateTimeFormatPattern.PATTERN_SERVER
            )
        },
        address = address,
        bio = bio,
        avatarURL = avatar
    )

    private var userDevice: List<UserDevice> = emptyList()


    private fun registerUserDevice(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM", "Fetching FCM token failed", task.exception)
                    return@addOnCompleteListener
                }

                val fcmToken = task.result
                Log.d("FCM", "Token: $fcmToken")
                viewModelScope.launch {
                    val result = userRepository.registerUserDevice(mContext, fcmToken = fcmToken)
                    if(result is APIResult.Success){
                        val result = userRepository.getRegisteredUserDevice()
                        if(result is APIResult.Success){
                            userDevice = result.data
                            if(_state.value is SettingVMState.Success){
                                _state.value = (_state.value as SettingVMState.Success).copy(
                                    isNotificationEnabled = !userDevice.isEmpty()
                                )
                            }
                        }
                    }else{
                        Log.e("FCM", "Fetching FCM token failed", task.exception)
                    }
                }
            }
    }

    fun toggleNotificationEnable(enable: Boolean){
        viewModelScope.launch {
            try {
                userRepository.updateNotificationEnable(mContext, enable)
                _state.value = (_state.value as SettingVMState.Success).copy(
                    isNotificationEnabled = enable
                )
            }catch (e: Exception){
                _state.value = SettingVMState.Error("Có lỗi xảy ra, vui lòng thử lại")
            }
        }
    }
}

