package com.huoergai.hcp.l12bitmap_drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

class PointView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        strokeWidth = Utils.dp2px(20f)
        strokeCap = Paint.Cap.ROUND
        color = Color.RED
    }
    private var point = PointF(50f, 50f)

    fun getPoint(): PointF {
        return point
    }

    fun setPoint(p: PointF) {
        point = p
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPoint(point.x, point.y, paint)
    }
}