package com.huoergai.hcp.l12bitmap_drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

/**
 *  动图：反转折叠
 */
class DynamicView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // val bitmap = Utils.getAvatar(resources, R.drawable.shaw, (0.8f * width).toInt())
        val bitmap = Utils.scaleImage(resources, 0.1f * width, 0.1f * width)
        canvas.drawBitmap(
            bitmap,
            width / 2f - bitmap.width / 2f,
            height / 2f - bitmap.height / 2f,
            paint
        )

    }
}