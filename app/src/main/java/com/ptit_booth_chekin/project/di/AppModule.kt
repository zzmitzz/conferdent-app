package com.ptit_booth_chekin.project.di

import androidx.navigation.Navigator
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ptit_booth_chekin.project.core.data.IPersistentStorage
import com.ptit_booth_chekin.project.core.data.PersistentStorage
import com.ptit_booth_chekin.project.core.network.AuthInterceptor
import com.ptit_booth_chekin.project.data.auth.remote.AuthenticationService
import com.ptit_booth_chekin.project.data.auth.remote.NotificationRepositoryService
import com.ptit_booth_chekin.project.data.event.EventEndpoint
import com.ptit_booth_chekin.project.data.auth.remote.UserRepositoryService
import com.ptit_booth_chekin.project.data.chatbot.ChatbotService
import com.ptit_booth_chekin.project.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAuthInterceptor(persistentStorage: IPersistentStorage): AuthInterceptor {
        return AuthInterceptor(persistentStorage)
    }

    @Provides
    @Named("Auth")
    fun provideAuthRetrofit(
        authInterceptor: AuthInterceptor
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.MILLISECONDS)
            .build()

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true // ignores extra JSON fields
            prettyPrint = false
            isLenient = true
        }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Named("NoAuth")
    fun provideNoAuthRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.MILLISECONDS)
            .build()
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true // ignores extra JSON fields
            prettyPrint = false
            isLenient = true
        }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }


    @Provides
    @Named("Chatbot")
    fun provideChatbotRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(Constants.REQUEST_TIME_OUT*2, TimeUnit.MILLISECONDS)
            .build()
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true // ignores extra JSON fields
            prettyPrint = false
            isLenient = true
        }
        return Retrofit.Builder()
            .baseUrl(Constants.CHATBOT_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideChatbotEndpoint(
        @Named("Chatbot") retrofit: Retrofit): ChatbotService {
        return retrofit.create(ChatbotService::class.java)
    }



    @Provides
    @Singleton
    fun provideAuthEndpoint(
        @Named("NoAuth") retrofit: Retrofit
    ): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepositoryService(
        @Named("Auth") retrofit: Retrofit
    ): UserRepositoryService {
        return retrofit.create(UserRepositoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventEndpoint(
        @Named("Auth") retrofit: Retrofit
    ): EventEndpoint {
        return retrofit.create(EventEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificationEndpoint(
        @Named("Auth") retrofit: Retrofit
    ): NotificationRepositoryService {
       return retrofit.create(NotificationRepositoryService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ImplementationsModule {
    @Binds
    abstract fun bindPersistentStorage(persistentStorage: PersistentStorage): IPersistentStorage

}