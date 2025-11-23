package com.turing.conferdent_conferentsmanagement.data.auth.remote

import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponseDetail(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expire_in")
    val expire: Long
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    @SerialName("full_name")
    val fullName: String
)

@Serializable
data class RegisterResponseDetail(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expire_in")
    val expire: Long
)


interface AuthenticationService {
    @POST("/registrations/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BaseResponse<LoginResponseDetail>>


    @POST("/registrations/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BaseResponse<RegisterResponseDetail>>

    @POST("/registrations/auth/logout")
    suspend fun logout(
    ): Response<BaseResponse<String>>

}