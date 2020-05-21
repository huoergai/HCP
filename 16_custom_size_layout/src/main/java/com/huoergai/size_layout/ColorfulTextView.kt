package com.huoergai.size_layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.random.Random

/**
 * D&T: 2020-05-19 03:55 PM
 * Des:
 */
class ColorfulTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val colors = listOf<Int>(
        // Color.YELLOW,
        // Color.BLUE,
        // Color.LTGRAY,
        // Color.CYAN,
        // Color.GRAY,
        // Color.GREEN,
        // Color.MAGENTA
        Color.parseColor("#E91E63"),
        Color.parseColor("#673AB7"),
        Color.parseColor("#3F51B5"),
        Color.parseColor("#2196F3"),
        Color.parseColor("#009688"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#FF5722"),
        Color.parseColor("#795548")
    )
    private val textSizes = listOf<Int>(16, 20, 24)
    private val cornerRadius = Utils.dp2px(20f)
    private val xPadding = Utils.dp2px(16f).toInt()
    private val yPadding = Utils.dp2px(8f).toInt()

    init {
        paint.color = colors[Random.nextInt(colors.size)]
        setTextColor(Color.WHITE)
        setPadding(xPadding, yPadding, xPadding, yPadding)
        textSize = textSizes[Random.nextInt(textSizes.size)].toFloat()
        // textSize = 16f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            cornerRadius,
            cornerRadius,
            paint
        )
        super.onDraw(canvas)
    }

}