package com.huoergai.size_layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-05-18 04:57 PM
 * Des:
 */
class CircleView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = Utils.dp2px(80f)
    private val padding = Utils.dp2px(30f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = (padding + radius).toInt() * 2

        // val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        // val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(radius + padding, radius + padding, radius, paint)
    }
}