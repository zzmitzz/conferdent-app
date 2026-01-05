package com.ptit_booth_chekin.project.util

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
    moduleRoundFactor: Float = 0.35f,     // subtle rounding (data modules)           // 0.9 -> circles nearly fill cell
    logo: Bitmap? = null,                         // optional logo to overlay
    logoScale: Float = 0.20f                      // logo covers 20% of QR width
): Bitmap {
    val hints = hashMapOf<EncodeHintType, Any>().apply {
        put(EncodeHintType.MARGIN, marginModules)
        put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
    }

    val bitMatrix = MultiFormatWriter()
        .encode(content, BarcodeFormat.QR_CODE, size, size, hints)

    val matrixSize = bitMatrix.width
    val cell = size / matrixSize.toFloat()
    val actualSize = cell * matrixSize
    val offset = (size - actualSize) / 2f

    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(if (backgroundTransparent) Color.TRANSPARENT else Color.WHITE)

    val dataPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(
            0f, 0f, size.toFloat(), size.toFloat(),
            foregroundStartColor,
            foregroundEndColor,
            Shader.TileMode.CLAMP
        )
        style = Paint.Style.FILL
    }

    val finderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = foregroundEndColor
        style = Paint.Style.FILL
    }

    fun isFinder(col: Int, row: Int): Boolean {
        val size = 7
        return (col < size && row < size) ||
                (col >= matrixSize - size && row < size) ||
                (col < size && row >= matrixSize - size)
    }

    // draw modules
    for (row in 0 until matrixSize) {
        for (col in 0 until matrixSize) {
            if (!bitMatrix.get(col, row)) continue

            val left = offset + col * cell
            val top = offset + row * cell
            val rect = RectF(left, top, left + cell, top + cell)

            if (isFinder(col, row)) {
                canvas.drawRect(rect, finderPaint)
            } else {
                val r = cell * moduleRoundFactor
                canvas.drawRoundRect(rect, r, r, dataPaint)
            }
        }
    }

    // draw finder inner eyes
    fun drawFinderEye(cx: Float, cy: Float) {
        val outer = cell * 7f
        val inner = cell * 3f

        val outerRect = RectF(
            cx - outer / 2,
            cy - outer / 2,
            cx + outer / 2,
            cy + outer / 2
        )

        val innerRect = RectF(
            cx - inner / 2,
            cy - inner / 2,
            cx + inner / 2,
            cy + inner / 2
        )

        canvas.drawRoundRect(outerRect, cell, cell, finderPaint)

        val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }
        canvas.drawRoundRect(
            RectF(
                innerRect.left - cell,
                innerRect.top - cell,
                innerRect.right + cell,
                innerRect.bottom + cell
            ),
            cell / 2,
            cell / 2,
            whitePaint
        )

        canvas.drawRoundRect(innerRect, cell / 2, cell / 2, finderPaint)
    }

    drawFinderEye(offset + cell * 3.5f, offset + cell * 3.5f)
    drawFinderEye(offset + actualSize - cell * 3.5f, offset + cell * 3.5f)
    drawFinderEye(offset + cell * 3.5f, offset + actualSize - cell * 3.5f)

    // logo overlay
    logo?.let { bmp ->
        val scale = (size * logoScale / bmp.width).coerceAtMost(1f)
        val w = bmp.width * scale
        val h = bmp.height * scale

        val left = (size - w) / 2f
        val top = (size - h) / 2f

        val bgPad = w * 0.18f
        val bgRect = RectF(
            left - bgPad,
            top - bgPad,
            left + w + bgPad,
            top + h + bgPad
        )

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }

        canvas.drawRoundRect(bgRect, cell, cell, bgPaint)
        canvas.drawBitmap(bmp, null, RectF(left, top, left + w, top + h), null)
    }

    return bitmap
}
