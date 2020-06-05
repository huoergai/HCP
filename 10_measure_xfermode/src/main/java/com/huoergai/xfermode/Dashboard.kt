package com.huoergai.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-06-04 02:09 PM
 * Des:
 */
class Dashboard : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    private val openAngle = 120
    private val radius = Utils.dp2px(150f)
    private val scaleCount: Int = 10

    private val scalePath = Path()

    private lateinit var sgs: SweepGradient
    private lateinit var dashEffect: PathDashPathEffect

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(25f)
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = Color.parseColor("#e65100")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        sgs = SweepGradient(
            w / 2f,
            height / 2f,
            intArrayOf(
                Color.GREEN,
                Color.YELLOW,
                Color.RED,
                Color.GREEN
            )
            , floatArrayOf(0f, 0.4f, 0.7f, 1f)
        )
        paint.shader = sgs

        scalePath.addRect(
            0f,
            0f,
            Utils.dp2px(2f),
            Utils.dp2px(15f),
            Path.Direction.CCW
        )

        dashEffect = PathDashPathEffect(scalePath, 50f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            // draw arc
            it.save()
            it.rotate(150f, width / 2f, height / 2f)
            it.drawArc(
                width / 2f - radius,
                height / 2f - radius,
                width / 2f + radius,
                height / 2f + radius,
                0f,
                240f,
                false,
                paint
            )
            it.rotate(-150f, width / 2f, height / 2f)
            it.restore()
            // draw scale

            paint.shader = null
            paint.color = Color.WHITE
            paint.pathEffect = dashEffect
            /*it.drawArc(
                width / 2f - radius,
                height / 2f - radius,
                width / 2f + radius,
                height / 2f + radius, 150f,
                240f,
                false,
                paint
            )*/

            // draw pointer

        }


    }

}