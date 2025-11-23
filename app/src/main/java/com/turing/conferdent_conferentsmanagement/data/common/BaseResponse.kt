package com.turing.conferdent_conferentsmanagement.data.common

import kotlinx.serialization.Serializable




@Serializable
data class BaseResponse<out T : Any>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T
)

@Serializable
data class BaseResponseState(
    val status: Int,
    val success: Boolean,
    val message: String
)