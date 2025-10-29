package com.turing.conferdent_conferentsmanagement.data.auth.repository

import com.turing.conferdent_conferentsmanagement.data.auth.remote.RegistrationDetail
import com.turing.conferdent_conferentsmanagement.data.auth.remote.UserRepositoryService
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRepositoryService: UserRepositoryService
) {
    suspend fun getMe(): APIResult<RegistrationDetail> {
        val response = userRepositoryService.getMe()
        return if (response.isSuccessful && response.body()?.data != null) {
            APIResult.Success(response.body()!!.data)
        }else{
            APIResult.Error(response.message())
        }
    }
}