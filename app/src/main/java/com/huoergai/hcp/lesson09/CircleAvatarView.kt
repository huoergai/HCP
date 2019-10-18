package com.huoergai.hcp.lesson09

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils


class CircleAvatarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val edge_color = Color.parseColor("#4081ED")
    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val edge_width = Utils.dp2px(10f)

    constructor(context: Context) : this(context, null)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmap = Utils.scaleImage(resources, width.toFloat(), height.toFloat())

        // 画边缘
        paint.color = edge_color
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            bitmap.width.coerceAtMost(bitmap.height) / 2f + edge_width,
            paint
        )

        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.strokeWidth = Utils.dp2px(4f)
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            bitmap.width.coerceAtMost(bitmap.height) / 2f + edge_width,
            paint
        )

        paint.xfermode = xFermode
        canvas.drawBitmap(
            bitmap,
            width / 2f - bitmap.width / 2f,
            height / 2f - bitmap.height / 2f,
            paint
        )
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)
    }
}