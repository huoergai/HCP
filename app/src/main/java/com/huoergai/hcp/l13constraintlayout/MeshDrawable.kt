package com.huoergai.hcp.l13constraintlayout

import android.graphics.*
import android.graphics.drawable.Drawable
import com.huoergai.hcp.Utils

class MeshDrawable : Drawable() {
    private val INTERVAL = Utils.dp2px(20f)

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        strokeWidth = Utils.dp2px(4f)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
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

    override fun draw(canvas: Canvas) {
        paint.shader = LinearGradient(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat(),
            Color.GREEN,
            Color.RED,
            Shader.TileMode.CLAMP
        )

        // vertical lines
        var i: Float = bounds.left.toFloat()
        while (i <= bounds.right) {
            canvas.drawLine(i, bounds.top.toFloat(), i, bounds.bottom.toFloat(), paint)
            i += INTERVAL
        }

        // last vertical line
        canvas.drawLine(
            bounds.right.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat(),
            paint
        )


        // horizontal line
        var j: Float = bounds.top.toFloat()
        while (j <= bounds.bottom) {
            canvas.drawLine(bounds.left.toFloat(), j, bounds.right.toFloat(), j, paint)
            j += INTERVAL
        }
        // last horizontal line
        canvas.drawLine(
            bounds.left.toFloat(),
            bounds.bottom.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat(),
            paint
        )

    }
}