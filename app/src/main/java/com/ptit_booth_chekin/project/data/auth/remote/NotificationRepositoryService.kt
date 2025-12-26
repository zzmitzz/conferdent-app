package com.ptit_booth_chekin.project.data.auth.remote

import com.ptit_booth_chekin.project.data.auth.remote.model.ReceivedNotification
import com.ptit_booth_chekin.project.data.common.BaseResponse
import retrofit2.Response
import retrofit2.http.GET


interface NotificationRepositoryService {
    @GET
    suspend fun getReceivedNotification(): Response<BaseResponse<
            List<ReceivedNotification>>>
}