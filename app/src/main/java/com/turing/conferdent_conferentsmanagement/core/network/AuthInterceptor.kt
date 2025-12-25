package com.turing.conferdent_conferentsmanagement.core.network

import com.turing.conferdent_conferentsmanagement.core.data.IPersistentStorage
import com.turing.conferdent_conferentsmanagement.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    val persistentStorage: IPersistentStorage
): Interceptor{
    var cacheToken: String? = null
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (cacheToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer $cacheToken")
        } else {
            runBlocking(Dispatchers.IO) {
                persistentStorage.readKeySuspend(Constants.USER_TOKEN).let {
                    cacheToken = it
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
            }
        }
        return chain.proceed(requestBuilder.build())
    }
    
    fun clearToken() {
        cacheToken = null
    }
}