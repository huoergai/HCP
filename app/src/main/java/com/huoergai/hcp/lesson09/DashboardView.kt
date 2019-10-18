package com.huoergai.hcp.lesson09

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils
import kotlin.math.cos
import kotlin.math.sin

class DashboardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    companion object {
        private var radius: Float = 120f
        private const val open_angle: Int = 90
        private const val index_count: Int = 20
        private const val current_index: Int = 10
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var indexPathEffect: PathDashPathEffect? = null
    private val linearGradient = LinearGradient(
        100f,
        0f,
        300f,
        0f,
        Color.GREEN,
        Color.YELLOW,
        Shader.TileMode.CLAMP
    )
    private var sweepGradient: SweepGradient? = null
    private var archPath: Path = Path()
    private var currentIndexAngle: Double = 0.0
    private val pointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = w.coerceAtMost(h)/2 - Utils.dp2px(10f)

        archPath.reset()
        sweepGradient = SweepGradient(
            w / 2f, h / 2f, intArrayOf(
                Color.RED,
                Color.parseColor("#76ff03"),
                Color.GREEN,
                Color.parseColor("#e65100"),
                Color.RED
            ), floatArrayOf(0.06f, 0.28f, 0.6f, 0.9f, 1f)
        )

        val indexDashPath = Path()
        indexDashPath.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(20f), Path.Direction.CCW)

        archPath.addArc(
            w / 2f - radius,
            h / 2f - radius,
            w / 2f + radius,
            h / 2f + radius,
            90 + open_angle / 2f,
            (360 - open_angle).toFloat()
        )

        val pathMeasure = PathMeasure(archPath, false)

        indexPathEffect = PathDashPathEffect(
            indexDashPath,
            (pathMeasure.length - Utils.dp2px(2f)) / index_count,
            0f,
            PathDashPathEffect.Style.ROTATE
        )

        currentIndexAngle =
            (90 + open_angle / 2 + (current_index / index_count) * (360 - open_angle)).toDouble()

        // paint.color = Color.GREEN

        pointerPaint.strokeWidth = Utils.dp2px(3.5f)
        pointerPaint.color = Color.parseColor("#1E88E5")
        pointerPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2f)

        // 弧
        paint.color = Color.parseColor("#001624")
        canvas.drawPath(archPath, paint)

        // 刻度
        paint.shader = sweepGradient
        paint.pathEffect = indexPathEffect
        canvas.drawPath(archPath, paint)

        // 指针
        canvas.drawLine(
            width / 2f,
            height / 2f,
            (width / 2 + 0.6f * radius * cos(currentIndexAngle)).toFloat(),
            (height / 2 + 0.6f * radius * sin(currentIndexAngle)).toFloat(),
            pointerPaint
        )
    }
}