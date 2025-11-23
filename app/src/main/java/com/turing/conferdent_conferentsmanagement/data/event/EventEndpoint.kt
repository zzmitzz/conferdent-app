package com.turing.conferdent_conferentsmanagement.data.event

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import com.turing.conferdent_conferentsmanagement.data.event.models.FormData
import com.turing.conferdent_conferentsmanagement.data.event.models.RegistrationResponseSubmit
import com.turing.conferdent_conferentsmanagement.models.FormFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    @SerialName("is_registered") val isRegistered: Boolean = false

    )

@Serializable
data class EventListWrapper(
    @SerialName("items") val items: List<EventDetail> = emptyList(),
    @SerialName("total") val total: Int = 0,
    @SerialName("page") val page: Int = 0,
    @SerialName("limit") val limit: Int = 0,
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
    ): Response<String>
}