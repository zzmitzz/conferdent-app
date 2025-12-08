package com.turing.conferdent_conferentsmanagement.util

import android.graphics.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

fun generateStyledQr(
    content: String,
    size: Int = 500,
    marginModules: Int = 1,                       // small quiet zone
    foregroundStartColor: Int = Color.parseColor("#333333"),
    foregroundEndColor: Int = Color.parseColor("#111111"),
    backgroundTransparent: Boolean = true,
    roundModuleFactor: Float = 0.9f,              // 0.9 -> circles nearly fill cell
    logo: Bitmap? = null,                         // optional logo to overlay
    logoScale: Float = 0.20f                      // logo covers 20% of QR width
): Bitmap {
    // 1) ZXing hints
    val hints = hashMapOf<EncodeHintType, Any>().apply {
        put(EncodeHintType.MARGIN, marginModules) // controls quiet zone
        put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
        // optionally set CHARACTER_SET if needed:
        // put(EncodeHintType.CHARACTER_SET, "UTF-8")
    }

    // 2) create bit matrix (request size x size)
    val bitMatrix = try {
        MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    } catch (e: Exception) {
        throw RuntimeException("QR encode failed", e)
    }

    val matrixWidth = bitMatrix.width
    val matrixHeight = bitMatrix.height

    // 3) compute module (cell) size in pixels and offsets for centering
    val cellSize = size / matrixWidth.toFloat()           // float to handle rounding
    val actualSize = (cellSize * matrixWidth).toInt()
    val offset = ((size - actualSize) / 2f)

    // 4) prepare bitmap & canvas
    val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    canvas.drawColor(if (backgroundTransparent) Color.TRANSPARENT else Color.WHITE)

    // 5) paint for modules (with gradient if provided)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // create gradient shader spanning the whole QR area
    val shader = LinearGradient(
        0f, 0f, size.toFloat(), size.toFloat(),
        foregroundStartColor, foregroundEndColor,
        Shader.TileMode.CLAMP
    )
    paint.shader = shader
    paint.style = Paint.Style.FILL

    // 6) draw each module as rounded circle (soft)
    val radius = (cellSize * 0.5f) * roundModuleFactor // circle radius inside module
    for (row in 0 until matrixHeight) {
        for (col in 0 until matrixWidth) {
            if (bitMatrix.get(col, row)) {
                val cx = offset + col * cellSize + cellSize / 2f
                val cy = offset + row * cellSize + cellSize / 2f
                canvas.drawCircle(cx, cy, radius, paint)
            }
        }
    }

    // 7) optional logo overlay (center)
    logo?.let { logoBmp ->
        val scale = (size * logoScale / logoBmp.width.toFloat()).coerceAtMost(1f)
        val logoW = (logoBmp.width * scale).toInt()
        val logoH = (logoBmp.height * scale).toInt()
        val left = (size - logoW) / 2f
        val top = (size - logoH) / 2f

        // Draw a white rounded background behind logo for scan safety
        val bgPadding = (logoW * 0.12f).toInt()
        val bgRect = RectF(left - bgPadding, top - bgPadding, left + logoW + bgPadding, top + logoH + bgPadding)
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            // small shadow could be added if desired (requires setShadowLayer and hardware/software layer adjustments)
        }
        canvas.drawRoundRect(bgRect, 12f, 12f, bgPaint)

        // draw the scaled logo
        val src = Rect(0, 0, logoBmp.width, logoBmp.height)
        val dst = RectF(left, top, left + logoW, top + logoH)
        canvas.drawBitmap(logoBmp, src, dst, null)
    }

    return bmp
}
