package com.ptit_booth_chekin.project.data.auth.repository

import android.util.Log
import com.google.gson.Gson
import com.ptit_booth_chekin.project.data.auth.remote.AuthenticationService
import com.ptit_booth_chekin.project.data.auth.remote.LoginRequest
import com.ptit_booth_chekin.project.data.auth.remote.LoginResponseDetail
import com.ptit_booth_chekin.project.data.auth.remote.RegisterRequest
import com.ptit_booth_chekin.project.data.auth.remote.RegisterResponseDetail
import com.ptit_booth_chekin.project.data.auth.remote.UserRepositoryService
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.data.common.BaseResponse
import com.ptit_booth_chekin.project.data.common.BaseResponseAuthentication
import javax.inject.Inject


class AuthRepository @Inject constructor(
    val authService: AuthenticationService,
    val userRepositoryService: UserRepositoryService,
) {
    suspend fun doLogin(
        email: String,
        password: String
    ): APIResult<LoginResponseDetail> {
        val response = authService.login(LoginRequest(email, password))
        Log.d("doLogin", response.toString())
        return if (response.isSuccessful && response.body()?.data != null) {
            APIResult.Success(response.body()?.data!!)
        }else{
            val errorJson = response.errorBody()?.string()
            val error = Gson().fromJson(
                errorJson,
                BaseResponseAuthentication::class.java
            )
            APIResult.Error(error.message)
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

    suspend fun doLogout(): APIResult<BaseResponseAuthentication<String>> {
        val response = userRepositoryService.logout()
        return if (response.isSuccessful) {
            APIResult.Success(response.body()!!)
        }else{
            APIResult.Error(response.message())
        }
    }
}