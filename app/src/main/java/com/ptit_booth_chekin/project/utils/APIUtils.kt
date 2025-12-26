package com.ptit_booth_chekin.project.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


fun String.parseToRequestBody() : RequestBody {
    return this.toRequestBody(MediaType.TEXT.string.toMediaTypeOrNull())
}

enum class MediaType(val string: String) {
    IMAGE("image/*"),
    VIDEO("video/*"),
    TEXT("text/plain")
}

fun File.parseToMultiplePart(field: String) : MultipartBody.Part {
    val requestFile = this.asRequestBody("image/png".toMediaType())
    return MultipartBody.Part.createFormData(field, name, requestFile)
}