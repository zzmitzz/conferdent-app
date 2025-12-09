package com.turing.conferdent_conferentsmanagement.data.event.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourceItem(
    @SerialName("id") val id: Int,
    @SerialName("event_id") val eventId: String? = null,
    @SerialName("session_id") val sessionId: Int? = null,
    @SerialName("resource_type") val resourceType: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("url_source") val urlSource: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("file_size_bytes") val fileSizeBytes: Long? = null,
    @SerialName("mime_type") val mimeType: String? = null,
    @SerialName("is_public") val isPublic: Boolean = true,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("download_count") val downloadCount: Int = 0,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("upload_date") val uploadDate: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class ResourceListWrapper(
    @SerialName("data") val data: List<ResourceItem> = emptyList(),
    @SerialName("total") val total: Int = 0
)
