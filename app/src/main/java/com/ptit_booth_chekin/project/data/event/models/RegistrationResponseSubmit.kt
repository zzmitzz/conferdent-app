package com.ptit_booth_chekin.project.data.event.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Responses(
    @SerialName("form_fields_id") var formFieldsId: String? = null,
    @SerialName("response") var response: String? = null
)

@Serializable
data class RegistrationResponseSubmit(
    @SerialName("event_id") var eventId: String? = null,
    @SerialName("responses") var responses: List<Responses> = emptyList()
)
