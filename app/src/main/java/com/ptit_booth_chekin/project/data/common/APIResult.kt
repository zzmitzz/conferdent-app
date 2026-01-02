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

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Error) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(this)
    }

    inline fun <R> mapSuccess(transform: (T) -> R): APIResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    fun getOrNull(): T? = (this as? Success)?.data
    fun errorOrNull(): Error? = this as? Error
}