package com.huoergai.hcp.l11animation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

class SportView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var radius = Utils.dp2px(110f)
    private val circleWidth = Utils.dp2px(16f)
    private val text = "80%"
    private val startAngle = 30f
    private var process = 0.8f
    private val fontMetrics = Paint.FontMetrics()
    private val textBounds = Rect()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w.coerceAtMost(h) / 2f - Utils.dp2px(circleWidth) - 5

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = circleWidth
        paint.color = Color.LTGRAY
        paint.strokeCap = Paint.Cap.ROUND
        // 水平居中
        paint.textAlign = Paint.Align.CENTER
        val typeface = Typeface.createFromAsset(context.assets, "ConcertOne-Regular.ttf")
        paint.typeface = typeface
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
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#009688")
        paint.textSize = Utils.dp2px(40f)
        // 纵向居中方式1：此法是绝对居中，但中心会随文字内容底部和顶部不同而上下。示例：bhgy
        paint.getTextBounds(text, 0, text.length, textBounds)
        val offset = (textBounds.bottom + textBounds.top) / 2f
        // 纵向居中方式2：
        // paint.getFontMetrics(fontMetrics)
        // val offset = (fontMetrics.ascent + fontMetrics.descent) / 2f
        canvas.drawText(text, width / 2f, height / 2f - offset, paint)

        // 文字贴边
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(text, (-textBounds.left).toFloat(), (-textBounds.top).toFloat(), paint)

        canvas.drawText(
            text, (-textBounds.left).toFloat(), -textBounds.top + paint.fontSpacing, paint
        )
    }
}