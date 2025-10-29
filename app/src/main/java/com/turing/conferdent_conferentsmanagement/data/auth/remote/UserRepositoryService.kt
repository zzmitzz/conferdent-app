package com.turing.conferdent_conferentsmanagement.data.auth.remote

import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET




@Serializable
data class RegistrationDetail(
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("dob") val dob: String? = null,
    @SerialName("gender") val gender: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("bio") val bio: String? = null
)


interface UserRepositoryService {
    @GET("/registrations/auth/me")
    suspend fun getMe(): Response<BaseResponse<RegistrationDetail>>
}