package com.turing.conferdent_conferentsmanagement.data.auth.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.turing.conferdent_conferentsmanagement.data.auth.remote.RegistrationDetail
import com.turing.conferdent_conferentsmanagement.data.auth.remote.UserRepositoryService
import com.turing.conferdent_conferentsmanagement.data.common.APIResult
import com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_setting.UserProfile
import com.turing.conferdent_conferentsmanagement.utils.parseToMultiplePart
import com.turing.conferdent_conferentsmanagement.utils.parseToRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
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
    suspend fun updateProfile(userProfile: UserProfile, context: Context): APIResult<String> {
        return withContext(Dispatchers.IO) {
            val response = userRepositoryService.updateProfile(
                dob = userProfile.dob!!.parseToRequestBody(),
                phone = userProfile.phone!!.parseToRequestBody(),
                bio = userProfile.bio!!.parseToRequestBody(),
                avatar = if(userProfile.avatarURL != null) getFileFromUri(context, userProfile.avatarURL.toUri())?.parseToMultiplePart("avatar") else null,
                address = userProfile.address!!.parseToRequestBody()
            )
            return@withContext if (response.isSuccessful) {
                APIResult.Success("Success")
            } else {
                APIResult.Error(response.message())
            }
        }
    }


    @SuppressLint("Recycle")
    fun getFileFromUri(context: Context, uri: Uri, fileName: String = "${System.currentTimeMillis()}_temp_file.png"): File?{
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw IOException("Could not open input stream")
            val tempFile = File(context.cacheDir, fileName)
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
            return tempFile
        }catch (e: Exception){
            return null
        }
    }
}