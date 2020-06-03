package com.huoergai.bitmap_drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * D&T: 2020-06-03 10:50 AM
 * Des:
 */
class MeshDrawable : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val interval = 50

    override fun draw(canvas: Canvas) {
        for (i in bounds.left until bounds.right step interval) {
            canvas.drawLine(
                i.toFloat(),
                bounds.top.toFloat(),
                i.toFloat(),
                bounds.bottom.toFloat(),
                paint
            )
        }

        for (i in bounds.top until bounds.bottom step interval) {
            canvas.drawLine(
                bounds.left.toFloat(),
                i.toFloat(),
                bounds.right.toFloat(),
                i.toFloat(),
                paint
            )
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> PixelFormat.TRANSLUCENT
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}