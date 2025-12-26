package com.ptit_booth_chekin.project.ui.screen.home.screen_chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptit_booth_chekin.project.core.network.AuthInterceptor
import com.ptit_booth_chekin.project.data.chatbot.ChatMessageSend
import com.ptit_booth_chekin.project.data.chatbot.ChatbotService
import com.ptit_booth_chekin.project.ui.screen.home.screen_chat.model.ChatDataUI
import com.ptit_booth_chekin.project.ui.screen.home.screen_setting.UserProfile
import com.ptit_booth_chekin.project.utils.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScreenChatVM
@Inject constructor(
    private val chatbotService: ChatbotService,
    private val authInterceptor: AuthInterceptor
) : ViewModel() {
    val listChat: MutableStateFlow<List<ChatDataUI>> = MutableStateFlow(
        listOf(
            ChatDataUI(
                id = 0,
                fromBot = true,
                message = "Chào bạn, bạn cần giúp đỡ gì ạ?"
            )
        )
    )

    fun userSendChat(message: String) {
        listChat.value += ChatDataUI(
            id = listChat.value.size,
            fromBot = false,
            message = message
        )

        viewModelScope.launch(Dispatchers.IO) {
            val response = chatbotService.sendMessage(
                message = ChatMessageSend(
                    message = message,
                    sessionID = authInterceptor.cacheToken ?: "Default"
                )
            )
            if (response.isSuccessful) {
                listChat.value += ChatDataUI(
                    id = listChat.value.size,
                    fromBot = true,
                    message = response.body()?.response ?: "Default"
                )
            } else {
                listChat.value += ChatDataUI(
                    id = listChat.value.size,
                    fromBot = true,
                    message = "Chatbot overloaded, please try again later"
                )
            }
        }

    }
}