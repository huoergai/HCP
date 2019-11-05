package com.huoergai.hcp.lesson12

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

class CircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = Utils.dp2px(8f)
        color = Color.CYAN
    }

    private var radius = Utils.dp2px(50f)

    fun getRadius(): Float {
        return radius
    }

    fun setRadius(r: Float) {
        radius = r
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
    }

}