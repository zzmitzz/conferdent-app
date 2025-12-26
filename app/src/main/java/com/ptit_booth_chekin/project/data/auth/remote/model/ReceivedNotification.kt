package com.ptit_booth_chekin.project.data.auth.remote.model

import kotlinx.serialization.Serializable
@Serializable
data class ActionData(
    val url: String
)

@Serializable
data class Notification(
    val _id: String,
    val action_data: ActionData,
    val action_type: String,
    val body: String,
    val image_url: String,
    val sent_at: String,
    val title: String
)
@Serializable
data class ReceivedNotification(
    val _id: String,
    val created_at: String,
    val delivered_at: String,
    val device_id: String,
    val error_message: String?,
    val fcm_message_id: String,
    val notification: Notification,
    val notification_id: String,
    val opened_at: String?,
    val registration_id: String,
    val sent_at: String,
    val status: String,
    val updated_at: String
)