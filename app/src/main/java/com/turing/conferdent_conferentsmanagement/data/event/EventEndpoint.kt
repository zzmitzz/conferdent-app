package com.turing.conferdent_conferentsmanagement.data.event

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Serializable
data class EventDetail(
    @SerialName("_id") var Id: String? = null,
    @SerialName("organizer_id") var organizerId: String? = null,
    @SerialName("name") var name: String? = null,
    @SerialName("thumbnail") var thumbnail: String? = null,
    @SerialName("logo") var logo: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("start_time") var startTime: String? = null,
    @SerialName("end_time") var endTime: String? = null,
    @SerialName("location") var location: String? = null,
    @SerialName("lat") var lat: Double? = null,
    @SerialName("lng") var lng: Double? = null,
    @SerialName("category_id") var categoryId: String? = null,
    @SerialName("tags") var tags: String? = null,
    @SerialName("status") var status: String? = null,
    @SerialName("state") var state: String? = null,
    @SerialName("pin_code") var pinCode: String? = null,
    @SerialName("approver_id") var approverId: String? = null,
    @SerialName("created_at") var createdAt: String? = null,
    @SerialName("updated_at") var updatedAt: String? = null,

    )

@Serializable
data class RegisteredEventListWrapper(
    @SerialName("items") val items: List<EventDetail> = emptyList()

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

    @GET("/registrations/events/registered/{month}")
    suspend fun getRegisteredEventsByMonth(
        @Path("month") month: Int,
    ): Response<BaseResponse<List<EventDetail>>>

    @GET("/registrations/registered-events")
    suspend fun getAllRegisteredEvents(
    ): Response<BaseResponse<List<EventDetail>>>
}