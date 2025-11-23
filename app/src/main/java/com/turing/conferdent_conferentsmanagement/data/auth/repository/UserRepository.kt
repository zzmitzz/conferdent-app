package com.turing.conferdent_conferentsmanagement.data.auth.repository

import com.turing.conferdent_conferentsmanagement.data.auth.remote.RegistrationDetail
import com.turing.conferdent_conferentsmanagement.data.auth.remote.UserRepositoryService
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.UserProfile
import com.turing.conferdent_conferentsmanagement.utils.parseToMultiplePart
import com.turing.conferdent_conferentsmanagement.utils.parseToRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRepositoryService: UserRepositoryService
) {
    suspend fun getMe(): APIResult<RegistrationDetail> {
        return withContext(Dispatchers.IO) {
            val response = userRepositoryService.getMe()
            return@withContext if (response.isSuccessful && response.body()?.data != null) {
                APIResult.Success(response.body()!!.data)
            } else {
                APIResult.Error(response.message())
            }
        }
    }
    suspend fun updateProfile(userProfile: UserProfile): APIResult<String> {
        return withContext(Dispatchers.IO) {
            val response = userRepositoryService.updateProfile(
                dob = userProfile.dob!!.parseToRequestBody(),
                phone = userProfile.phone!!.parseToRequestBody(),
                bio = userProfile.bio!!.parseToRequestBody(),
//                avatar = getFileFromUri(userProfile.avatarURL!!)?.parseToMultiplePart("avatar") ?: null,
//                avatar = null,
                address = userProfile.address!!.parseToRequestBody()
            )
            return@withContext if (response.isSuccessful) {
                APIResult.Success("Success")
            } else {
                APIResult.Error(response.message())
            }
        }
    }


    private fun getFileFromUri(uri: String): File?{
        try {
            val file = File(uri)
            return file
        } catch (e: Exception) {
            return null
        }
    }
}