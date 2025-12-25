package com.turing.conferdent_conferentsmanagement.data.chatbot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


@Serializable
data class ChatMessageSend(
    val message: String,
    @SerialName("session_id")
    val sessionID: String
)

@Serializable
data class ChatMessageResponse(
    val response: String,
    @SerialName("session_id")
    val sessionID: String
)


interface ChatbotService {
    @POST("/chat")
    suspend fun sendMessage(
        @Body message: ChatMessageSend
    ): Response<ChatMessageResponse>

}