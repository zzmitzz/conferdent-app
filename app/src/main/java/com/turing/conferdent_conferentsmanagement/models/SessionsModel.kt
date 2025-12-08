package com.turing.conferdent_conferentsmanagement.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionsModel(
    @SerialName("id"               ) var id              : Int?              = null,
    @SerialName("event_id"         ) var eventId         : String?           = null,
    @SerialName("title"            ) var title           : String?           = null,
    @SerialName("description"      ) var description     : String?           = null,
    @SerialName("start_time"       ) var startTime       : String?           = null,
    @SerialName("end_time"         ) var endTime         : String?           = null,
    @SerialName("place"            ) var place           : String?           = null,
    @SerialName("capacity"         ) var capacity        : Int?              = null,
    @SerialName("is_active"        ) var isActive        : Boolean?          = null,
    @SerialName("session_type"     ) var sessionType     : String?           = null,
    @SerialName("tags"             ) var tags            : ArrayList<String> = arrayListOf(),
    @SerialName("created_at"       ) var createdAt       : String?           = null,
    @SerialName("updated_at"       ) var updatedAt       : String?           = null,
    @SerialName("registered_count" ) var registeredCount : Int?              = null,
    @SerialName("waitlist_count"   ) var waitlistCount   : Int?              = null,
    @SerialName("available_spots"  ) var availableSpots  : Int?              = null,
    @SerialName("speakers") var speakers: ArrayList<SpeakersModel> = arrayListOf()
)

@Serializable
data class SpeakersModel(
    @SerialName("id"         ) var id        : Int,
    @SerialName("photo_url" ) var avatar : String

)