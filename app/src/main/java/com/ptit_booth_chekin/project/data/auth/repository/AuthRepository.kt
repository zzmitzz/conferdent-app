package com.ptit_booth_chekin.project.data.auth.repository

import com.ptit_booth_chekin.project.data.auth.remote.AuthenticationService
import com.ptit_booth_chekin.project.data.auth.remote.LoginRequest
import com.ptit_booth_chekin.project.data.auth.remote.LoginResponseDetail
import com.ptit_booth_chekin.project.data.auth.remote.RegisterRequest
import com.ptit_booth_chekin.project.data.auth.remote.RegisterResponseDetail
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.common.BaseResponse
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