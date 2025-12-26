
package com.ptit_booth_chekin.project.data.event.models

import com.google.gson.annotations.SerializedName
import com.ptit_booth_chekin.project.models.FormFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FormData(
    @SerialName("_id") var Id: String? = null,
    @SerialName("event_id") var eventId: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("is_public") var isPublic: Boolean? = null,
    @SerialName("created_at") var createdAt: String? = null,
    @SerialName("updated_at") var updatedAt: String? = null,
    @SerialName("fields") var fields: List<FormFields> = listOf()

)