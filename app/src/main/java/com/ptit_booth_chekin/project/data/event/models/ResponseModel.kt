package com.ptit_booth_chekin.project.data.event.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisteredIDResponse(
    @SerialName("id") var id: Int? = null,
    @SerialName("event_id") var eventId: String? = null,
    @SerialName("registration_id") var registrationId: String? = null,
    @SerialName("is_registered") var isRegistered: Boolean? = null,
    @SerialName("registered_at") var registeredAt: String? = null
)