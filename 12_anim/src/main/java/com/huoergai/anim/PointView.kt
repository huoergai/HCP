package com.huoergai.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-06-02 04:00 PM
 * Des:
 */
class PointView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var point = PointF()
        set(value) {
            field = value
            invalidate()
        }

    init {
        // paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(30f)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawPoint(point.x, point.y, paint)
        }
    }

}