package com.ptit_booth_chekin.project.data.auth.remote

import com.google.firebase.firestore.auth.User
import com.google.gson.annotations.SerializedName
import com.ptit_booth_chekin.project.data.auth.remote.model.ReceiveNotificationPagination
import com.ptit_booth_chekin.project.data.auth.remote.model.ReceivedNotification
import com.ptit_booth_chekin.project.data.auth.remote.model.UserDevice
import com.ptit_booth_chekin.project.data.common.BaseResponse
import com.ptit_booth_chekin.project.data.common.BaseResponseNotification
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


interface NotificationRepositoryService {
    @GET("/registrations/devices/notifications/{device_name}")
    suspend fun getReceivedNotification(
        @Path("device_name") deviceName: String,
    ): Response<BaseResponse<ReceiveNotificationPagination>>

    @POST("/registrations/devices/devices")
    suspend fun registerUserDevice(
        @Body userDevice: UserDevice
    ): Response<BaseResponse<UserDevice>>


    @GET("/registrations/devices/devices")
    suspend fun getUserDevice(): Response<BaseResponse<List<UserDevice>>>

    @PUT("/registrations/devices/devices/{device_id}")
    suspend fun updateOnOffNotification(
        @Path("device_id") deviceId: String,
        @Body notificationConfig: NotificationConfig
    ): Response<BaseResponse<UserDevice>>
}

@Serializable
data class NotificationConfig(
    @SerialName("notifications_enabled") val enableNotification: Boolean ,
    @SerialName("device_name") val deviceName: String = "",
)