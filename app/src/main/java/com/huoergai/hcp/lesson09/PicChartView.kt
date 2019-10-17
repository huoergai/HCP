package com.huoergai.hcp.lesson09

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class PicChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    companion object {
        private val colors =
            intArrayOf(Color.CYAN, Color.RED, Color.YELLOW, Color.LTGRAY, Color.GREEN)
        private val angles = floatArrayOf(80f, 15f, 60f, 45f, 120f)
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val radius = 200
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var index = 0
        var currentAngle = 0f
        while (index < angles.size) {
            paint.color = colors[index]

            if (index == 2) {
                canvas.save()
                canvas.translate(
                    -20 * cos(currentAngle + angles[index] / 2),
                    -20 * sin(currentAngle + angles[index] / 2)
                )
            }

            canvas.drawArc(
                width / 2f - radius,
                height / 2f - radius,
                width / 2f + radius,
                height / 2f + radius,
                currentAngle,
                angles[index],
                true,
                paint
            )

            if (index == 2) {
                canvas.restore()
            }
            currentAngle += angles[index]
            index++
        }

    }

}