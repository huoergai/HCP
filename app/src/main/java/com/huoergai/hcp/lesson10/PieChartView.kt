package com.huoergai.hcp.lesson10

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils
import kotlin.math.cos
import kotlin.math.sin

class PieChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    companion object {
        private val colors = intArrayOf(
            Color.parseColor("#5EA033"),
            Color.parseColor("#0C8448"),
            Color.parseColor("#EA6B21"),
            Color.parseColor("#E25652"),
            Color.parseColor("#4081ED"),
            Color.parseColor("#EFA92C")
        )
        private val angles = floatArrayOf(80f, 20f, 60f, 45f, 120f, 35f)
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val STANDOUT_OFFSET = Utils.dp2px(10f)
        private var radius = 120f
        private const val STANDOUT_INDEX = 4
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w.coerceAtMost(h) / 2 - Utils.dp2px(10f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        angles.sort()
        var index = 0
        var currentAngle = 0f
        while (index < angles.size) {
            paint.color = colors[index]

            if (index == STANDOUT_INDEX) {
                canvas.save()
                canvas.translate(
                    (STANDOUT_OFFSET * cos(Math.toRadians((currentAngle + angles[index] / 2).toDouble())).toFloat()),
                    (STANDOUT_OFFSET * sin(Math.toRadians((currentAngle + angles[index] / 2).toDouble()))).toFloat()
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

            if (index == STANDOUT_INDEX) {
                canvas.restore()
            }
            currentAngle += angles[index]
            index++
        }

    }

}