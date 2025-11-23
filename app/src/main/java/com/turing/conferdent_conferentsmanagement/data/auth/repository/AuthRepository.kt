package com.turing.conferdent_conferentsmanagement.data.auth.repository

import com.turing.conferdent_conferentsmanagement.data.auth.remote.AuthenticationService
import com.turing.conferdent_conferentsmanagement.data.auth.remote.LoginRequest
import com.turing.conferdent_conferentsmanagement.data.auth.remote.LoginResponseDetail
import com.turing.conferdent_conferentsmanagement.data.auth.remote.RegisterRequest
import com.turing.conferdent_conferentsmanagement.data.auth.remote.RegisterResponseDetail
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.data.common.BaseResponse
import javax.inject.Inject


class AuthRepository @Inject constructor(
    val authService: AuthenticationService
) {
    suspend fun doLogin(
        email: String,
        password: String
    ): APIResult<LoginResponseDetail> {
        val response = authService.login(LoginRequest(email, password))
        return if (response.isSuccessful && response.body()?.data != null) {
            APIResult.Success(response.body()!!.data)
        }else{
            APIResult.Error(response.message())
        }
    }


    suspend fun doRegister(
        email: String,
        password: String,
        fullName: String
    ): APIResult<RegisterResponseDetail> {
        val response = authService.register(RegisterRequest(email, password, fullName))
        return if (response.isSuccessful && response.body()?.data != null) {
            APIResult.Success(response.body()!!.data)
        }else{
            APIResult.Error(response.message())
        }
    }

    suspend fun doLogout(): APIResult<BaseResponse<String>> {
        val response = authService.logout()
        return if (response.isSuccessful) {
            APIResult.Success(response.body()!!)
        }else{
            APIResult.Error(response.message())
        }
    }
}