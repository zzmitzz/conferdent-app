package com.ptit_booth_chekin.project.data.auth.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDevice(
    @SerialName("device_id")
    val deviceId: String,

    @SerialName("device_type")
    val deviceType: String,

    @SerialName("fcm_token")
    val fcmToken: String?,

    @SerialName("device_name")
    val deviceName: String,

    @SerialName("os_version")
    val osVersion: String,

    @SerialName("app_version")
    val appVersion: String
)