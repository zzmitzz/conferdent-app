package com.ptit_booth_chekin.project.data.auth.remote

import com.ptit_booth_chekin.project.data.common.BaseResponse
import com.ptit_booth_chekin.project.data.common.BaseResponseAuthentication
import com.ptit_booth_chekin.project.data.common.BaseResponseState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part


@Serializable
data class RegistrationDetail(
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("dob") val dob: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("bio") val bio: String? = null,
    @SerialName("avatar") val avatar: String? = null
)


interface UserRepositoryService {
    @GET("/registrations/auth/me")
    suspend fun getMe(): Response<BaseResponse<RegistrationDetail>>

    @Multipart
    @PUT("/registrations/auth/update-profile") // Example endpoint
    suspend fun updateProfile(
        @Part("dob") dob: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<BaseResponseState>

    @POST("/registrations/auth/logout")
    suspend fun logout(
    ): Response<BaseResponseAuthentication<String>>

}