package com.ptit_booth_chekin.project.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.provider.MediaStore
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale


@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (Double, Double) -> Unit
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onLocationReceived(location.latitude, location.longitude)
            } else {
            }
        }
}

fun getAddressFromLatLng(context: Context, lat: Double, lon: Double): Address? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        addresses?.firstOrNull()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


fun saveBitmapToMediaStore(context: Context, bitmap: Bitmap, fileName: String = "qr_${System.currentTimeMillis()}.png") {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp") // optional folder
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    uri?.let { savedUri ->
        resolver.openOutputStream(savedUri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(savedUri, contentValues, null, null)
    }
}