package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turing.conferdent_conferentsmanagement.core.data.IPersistentStorage
import com.turing.conferdent_conferentsmanagement.data.auth.repository.AuthRepository
import com.turing.conferdent_conferentsmanagement.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject





sealed class SettingVMState {
    object Loading : SettingVMState()
    data class Success(
        val email: String?,
        val phone: String?,
        val fullName: String?,
        val dob: String?,
        val avatarURL: String?,
        val bio: String?,
        val isNotificationEnabled: Boolean = false,
        val isAutoFilled: Boolean = false
    ) : SettingVMState()
    data class Error(val message: String) : SettingVMState()
}



@HiltViewModel
class SettingViewModel @Inject constructor(
    private val persistentStorage: IPersistentStorage,
    private val authRepository: AuthRepository
): ViewModel(){

    fun logout() {
        viewModelScope.launch {
            persistentStorage.saveKeySuspend(Constants.USER_TOKEN, null)
            persistentStorage.saveKeySuspend(Constants.USER_NAME, null)
            persistentStorage.saveKeySuspend(Constants.USER_PASSWORD, null)
            authRepository.doLogout()
        }
    }




}