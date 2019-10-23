package com.huoergai.hcp.lesson11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

class SportView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var radius = Utils.dp2px(110f)
    private val circleWidth = Utils.dp2px(16f)
    private val text = "holiday"
    private val startAngle = 30f
    private var process = 0.8f
    private val fontMetrics = Paint.FontMetrics()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w.coerceAtMost(h) / 2f - Utils.dp2px(circleWidth + 5)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleWidth
        paint.color = Color.LTGRAY
        paint.strokeCap = Paint.Cap.ROUND

        textPaint.typeface = Typeface.SANS_SERIF
        textPaint.color = Color.parseColor("#009688")
        textPaint.textSize = Utils.dp2px(30f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 背景
        canvas.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius, startAngle + 360 * process, 360 * (1 - process), false, paint
        )
        // 进度
        paint.color = Color.RED
        canvas.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            startAngle,
            360 * process,
            false,
            paint
        )

        // 画文本
        paint.getFontMetrics(fontMetrics)
        val textWidth = textPaint.measureText(text)
        canvas.drawText(
            text,
            width / 2f - textWidth / 2f,
            height / 2f + (fontMetrics.bottom - fontMetrics.top + paint.fontSpacing) / 2,
            textPaint
        )
    }
}