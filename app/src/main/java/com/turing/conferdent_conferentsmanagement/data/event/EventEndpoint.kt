package com.turing.conferdent_conferentsmanagement.data.event

import com.turing.conferdent_conferentsmanagement.data.event.models.RegisteredIDResponse
import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import com.turing.conferdent_conferentsmanagement.data.event.models.FormData
import com.turing.conferdent_conferentsmanagement.data.event.models.RegistrationResponseSubmit
import com.turing.conferdent_conferentsmanagement.data.event.models.ResourceListWrapper
import com.turing.conferdent_conferentsmanagement.models.SessionsModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query



@Serializable
data class EventOrganizer(
    @SerialName("name"     ) var name     : String? = null,
    @SerialName("describe" ) var describe : String? = null,
    @SerialName("avatar"   ) var avatar   : String? = null
)

@Serializable
data class EventSpeakers(
    @SerialName("id"                 ) var id                : Int?    = null,
    @SerialName("full_name"          ) var fullName          : String? = null,
    @SerialName("bio"                ) var bio               : String? = null,
    @SerialName("event_id"           ) var eventId           : String? = null,
    @SerialName("email"              ) var email             : String? = null,
    @SerialName("phone"              ) var phone             : String? = null,
    @SerialName("photo_url"          ) var photoUrl          : String? = null,
    @SerialName("professional_title" ) var professionalTitle : String? = null,
    @SerialName("linkedin_url"       ) var linkedinUrl       : String? = null,
    @SerialName("created_at"         ) var createdAt         : String? = null,
    @SerialName("updated_at"         ) var updatedAt         : String? = null
)

@Serializable
data class EventDetail(
    @SerialName("_id") val Id: String? = null,
    @SerialName("organizer_id") val organizerId: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("thumbnail") val thumbnail: String? = null,
    @SerialName("logo") val logo: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lng") val lng: Double? = null,
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("tags") val tags: List<String>? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("capacity") val capacity: Int? = null,
    @SerialName("pin_code") val pinCode: String? = null,
    @SerialName("approver_id") val approverId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    @SerialName("is_registered") val isRegistered: Boolean = false,
    @SerialName("organizer") val organizers: EventOrganizer? = null,
    @SerialName("speakers") val speakers: List<EventSpeakers> = emptyList(),
    @SerialName("maps") val maps: String? = null
    )

@Serializable
data class EventListWrapper(
    @SerialName("items") val items: List<EventDetail> = emptyList(),
    @SerialName("total") val total: Int = 0,
    @SerialName("page") val page: Int = 0,
    @SerialName("limit") val limit: Int = 0,
)

@Serializable
data class SpeakerSessionItem(
    @SerialName("id") var id: Int? = null,
    @SerialName("event_id") var eventId: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("start_time") var startTime: String? = null,
    @SerialName("end_time") var endTime: String? = null,
    @SerialName("place") var place: String? = null,
    @SerialName("capacity") var capacity: Int? = null,
    @SerialName("is_active") var isActive: Boolean? = null,
    @SerialName("session_type") var sessionType: String? = null,
    @SerialName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerialName("created_at") var createdAt: String? = null,
    @SerialName("updated_at") var updatedAt: String? = null,
    @SerialName("speaker_role") var speakerRole: String? = null,
    @SerialName("speaking_order") var speakingOrder: Int? = null,
    @SerialName("speaking_duration_minutes") var speakingDurationMinutes: Int? = null
)

@Serializable
data class SpeakerSessionsResponse(
    @SerialName("speaker") val speaker: EventSpeakers,
    @SerialName("sessions") val sessions: List<SpeakerSessionItem> = emptyList(),
    @SerialName("total") val total: Int = 0
)



@Serializable
data class FormDetailData(
    @SerialName("created_at"      ) var createdAt      : String? = null,
    @SerialName("updated_at"      ) var updatedAt      : String? = null,
    @SerialName("_id"             ) var Id             : String? = null,
    @SerialName("form_fields_id"  ) var formFieldsId   : String? = null,
    @SerialName("response"        ) var response       : String? = null,
    @SerialName("event_id"        ) var eventId        : String? = null,
    @SerialName("registration_id" ) var registrationId : String? = null

)

interface EventEndpoint {
    @GET("/registrations/events")
    suspend fun getEvents(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<BaseResponse<EventListWrapper>>
    @GET("/registrations/events/search")
    suspend fun searchEvent(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<BaseResponse<EventListWrapper>>

    @GET("/registrations/registered-events")
    suspend fun getAllRegisteredEvents(
    ): Response<BaseResponse<List<EventDetail>>>

    @GET("/registrations/events/{id}")
    suspend fun getEventDetail(
        @Path("id") id: String,
    ): Response<BaseResponse<EventDetail>>

    @GET("/registrations/events/{id}/register")
    suspend fun registerEvent(
        @Path("id") id: String
    ): Response<BaseResponse<FormData>>

    @POST("/registrations/responses/submit")
    suspend fun submitResponse(
        @Body body: RegistrationResponseSubmit
    ): Response<BaseResponse<List<FormDetailData>>>


    @GET("/registrations/events/{id}/registered")
    suspend fun getRegistrationForm(
        @Path("id") id: String
    ): Response<BaseResponse<RegisteredIDResponse>>


    @GET("/registrations/session-registrations/event/{id}/sessions")
    suspend fun getEventSession(
        @Path("id") id: String
    ) : Response<BaseResponse<List<SessionsModel>>>

    @GET("/registrations/events/speakers/{speakerId}")
    suspend fun getSpeakerDetail(
        @Path("speakerId") speakerId: String
    ): Response<BaseResponse<EventSpeakers>>

    @GET("/registrations/events/speakers/{speakerId}/sessions")
    suspend fun getSpeakerSessions(
        @Path("speakerId") speakerId: String
    ): Response<BaseResponse<SpeakerSessionsResponse>>

    @GET("/registrations/resources/event/{eventId}")
    suspend fun getEventResources(
        @Path("eventId") eventId: String
    ): Response<BaseResponse<ResourceListWrapper>>
}