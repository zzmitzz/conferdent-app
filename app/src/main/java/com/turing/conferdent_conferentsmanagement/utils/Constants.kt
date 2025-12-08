package com.turing.conferdent_conferentsmanagement.utils


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.datastore.preferences.core.stringPreferencesKey
import com.turing.conferdent_conferentsmanagement.R

object Constants {

    const val REQUEST_TIME_OUT = 10000L
    const val BASE_URL = "http://192.168.1.35:3456"

    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PASSWORD = stringPreferencesKey("user_password")
    val USER_TOKEN = stringPreferencesKey("user_token")

    enum class EventCategory(
        @DrawableRes val icon: Int,
        @StringRes val showName: Int
    ) {
        ENVIRONMENT(
            R.drawable.ic_env,
            R.string.environment
        ),
        TECH(
            R.drawable.ic_tech,
            R.string.tech

        ),
        ECONOMIC(
            R.drawable.ic_eco,
            R.string.economic
        ),
        EDUCATION(
            R.drawable.ic_edu,
            showName = R.string.education
        ),
        MEDICAL(
            R.drawable.ic_med,
            showName = R.string.medical,
        ),


    }
}