package com.ptit_booth_chekin.project.data.auth.remote

import com.ptit_booth_chekin.project.data.common.BaseResponse
import com.ptit_booth_chekin.project.data.common.BaseResponseAuthentication
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
    ): Response<BaseResponseAuthentication<LoginResponseDetail>>


    @POST("/registrations/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BaseResponse<RegisterResponseDetail>>



}