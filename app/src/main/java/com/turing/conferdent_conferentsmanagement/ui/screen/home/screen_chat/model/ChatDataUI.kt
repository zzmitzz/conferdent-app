package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_chat.model

import java.time.LocalDateTime

data class ChatDataUI(
    val id: Int,
    val fromBot: Boolean = false,
    val message: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)