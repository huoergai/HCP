package com.huoergai.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-05-31 09:00 AM
 * Des:
 */
class CircleView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var radius: Float = Utils.dp2px(50f)
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(10f)
        paint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            // it.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            it.drawCircle(width / 2f, height / 2f, radius, paint)
        }
    }

}