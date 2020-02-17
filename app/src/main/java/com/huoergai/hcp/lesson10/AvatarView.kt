package com.huoergai.hcp.lesson10

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils


class AvatarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val edgeColor = Color.parseColor("#4081ED")
    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val edgeWidth = Utils.dp2px(5f)
    private var radius: Float = 0.0f
    private val gap_offset = Utils.dp2px(2f)

    constructor(context: Context) : this(context, null)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmap = Utils.scaleImage(resources, 0.5f*width, 0.5f*width)
        radius = bitmap.width.coerceAtMost(bitmap.height) / 2f
        // 画边缘
        paint.color = edgeColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = edgeWidth
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            radius,
            paint
        )

        paint.style = Paint.Style.FILL
        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint)
        paint.color = Color.WHITE
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            radius - paint.strokeWidth / 2 - gap_offset,
            paint
        )

        paint.xfermode = xFermode
        // 画头像
        canvas.drawBitmap(
            bitmap,
            width / 2f - bitmap.width / 2 + paint.strokeWidth / 2,
            height / 2f - bitmap.height / 2 + paint.strokeWidth / 2,
            paint
        )
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)
    }
}