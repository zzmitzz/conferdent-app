package com.ptit_booth_chekin.project.ui.screen.home.screen_notification.components

data class NotificationUI(
    val id: String,
    val title: String,
    val body: String,
    val imageUrl: String,
    val formattedTime: String,
    val isRead: Boolean,
    val actionType: String,
    val actionUrl: String
)
