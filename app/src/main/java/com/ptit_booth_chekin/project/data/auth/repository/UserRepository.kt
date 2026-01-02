package com.ptit_booth_chekin.project.data.auth.repository

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import com.ptit_booth_chekin.project.BuildConfig
import com.ptit_booth_chekin.project.data.auth.remote.NotificationConfig
import com.ptit_booth_chekin.project.data.auth.remote.NotificationRepositoryService
import com.ptit_booth_chekin.project.data.auth.remote.RegistrationDetail
import com.ptit_booth_chekin.project.data.auth.remote.UserRepositoryService
import com.ptit_booth_chekin.project.data.auth.remote.model.ReceivedNotification
import com.ptit_booth_chekin.project.data.auth.remote.model.UserDevice
import com.ptit_booth_chekin.project.data.common.APIResult
import com.ptit_booth_chekin.project.ui.screen.home.screen_setting.UserProfile
import com.ptit_booth_chekin.project.utils.parseToMultiplePart
import com.ptit_booth_chekin.project.utils.parseToRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRepositoryService: UserRepositoryService,
    private val userNotificationService: NotificationRepositoryService
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


    suspend fun getUserReceivedNotification(deviceName: String) : APIResult<List<ReceivedNotification>>{
        return withContext(Dispatchers.IO){
            val result = userNotificationService.getReceivedNotification(deviceName)
            if(result.isSuccessful && result.body() != null){
                return@withContext APIResult.Success(result.body()?.data?.notifications ?: emptyList())
            }else{
                return@withContext APIResult.Error(result.message())
            }
        }
    }

    suspend fun getRegisteredUserDevice(): APIResult<List<UserDevice>> {
        return withContext(Dispatchers.IO){
            val result = userNotificationService.getUserDevice()
            if(result.isSuccessful && result.body() != null){
                return@withContext APIResult.Success(result.body()?.data ?: emptyList())
            }else{
                return@withContext APIResult.Error(result.message())
            }
        }
    }

    suspend fun registerUserDevice(context: Context, fcmToken: String): APIResult<UserDevice> {
        val deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        return withContext(Dispatchers.IO){
            val result = userNotificationService.registerUserDevice(
                UserDevice(
                    deviceId = androidId,
                    deviceType = "android",
                    fcmToken = fcmToken,
                    deviceName = deviceName,
                    osVersion = Build.VERSION.RELEASE,
                    appVersion = BuildConfig.VERSION_CODE.toString()
                )
            )
            if(result.isSuccessful && result.body()?.data != null) {
                return@withContext APIResult.Success(result.body()!!.data)
            }else{
                return@withContext APIResult.Error(result.message())

            }

        }
    }

    suspend fun updateNotificationEnable(context: Context, isEnable: Boolean){
        val androidId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val result = userNotificationService.updateOnOffNotification(
            deviceId = androidId,
            NotificationConfig(
                enableNotification = isEnable,
                deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"
            )
        )
        if(!result.isSuccessful){
            throw Exception(result.message())
        }
    }


}