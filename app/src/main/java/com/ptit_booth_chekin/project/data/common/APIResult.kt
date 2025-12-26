package com.ptit_booth_chekin.project.data.common

import kotlinx.serialization.Serializable

@Serializable
sealed class APIResult<out T> {
    @Serializable
    data class Success<out T>(val data: T) : APIResult<T>()

    @Serializable
    data class Error(
        val message: String,
        val detail: String? = null,

    ) : APIResult<Nothing>()
}