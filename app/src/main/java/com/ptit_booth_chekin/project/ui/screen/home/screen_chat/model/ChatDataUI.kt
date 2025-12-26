package com.ptit_booth_chekin.project.ui.screen.home.screen_chat.model

import java.time.LocalDateTime

data class ChatDataUI(
    val id: Int,
    val fromBot: Boolean = false,
    val message: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)