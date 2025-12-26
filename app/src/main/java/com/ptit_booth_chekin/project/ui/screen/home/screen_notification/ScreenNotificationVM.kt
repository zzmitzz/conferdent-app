package com.ptit_booth_chekin.project.ui.screen.home.screen_notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.data.auth.remote.model.ReceivedNotification
import com.ptit_booth_chekin.project.data.auth.repository.UserRepository
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components.NotificationUI
import com.ptit_booth_chekin.project.utils.formatNotificationTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScreenNotificationUIState() {

    data class UISuccess(
        val notifications: List<NotificationUI> = emptyList()
    ): ScreenNotificationUIState()

    object UILoading : ScreenNotificationUIState()
    data class UIError(val message: String) : ScreenNotificationUIState()

}

sealed class ScreenNotificationEffect() {
    data class ShowToast(val message: String) : ScreenNotificationEffect()
}

@HiltViewModel
class ScreenNotificationVM @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ScreenNotificationUIState>(ScreenNotificationUIState.UILoading)
    val uiState = _uiState

    init {
        _uiState.value = ScreenNotificationUIState.UILoading
        viewModelScope.launch {
            userRepository.getUserReceivedNotification().let {
                if (it is APIResult.Success) {
                    _uiState.value = ScreenNotificationUIState.UISuccess(
                        it.data.map { receivedNotification ->
                            NotificationUI(
                                id = receivedNotification._id,
                                title = receivedNotification.notification.title,
                                body = receivedNotification.notification.body,
                                imageUrl = receivedNotification.notification.image_url,
                                formattedTime = formatNotificationTime(receivedNotification.sent_at),
                                isRead = receivedNotification.opened_at != null,
                                actionType = receivedNotification.notification.action_type,
                                actionUrl = receivedNotification.notification.action_data.url
                            )
                        }
                    )
                } else if (it is APIResult.Error) {
                    _uiState.value = ScreenNotificationUIState.UIError(it.message)
                }
            }
        }
    }
}