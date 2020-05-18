package com.huoergai.scalable_image_view.prac

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.huoergai.scalable_image_view.Util

/**
 * D&T: 2020-05-10 10:35 AM
 * Des: 协作型/焦点为各手指的中值
 *      1.以各手指的中点为实际焦点位置。
 *      2.在 ACTION_POINTER_UP 时, 要注意 pointerCount 的实际值。
 *      3.
 */
class CooperateTouchView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val bitmap = Util.getAvatar(resources, Util.dp2px(300f).toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f

    private var focusX: Float = 0f
    private var focusY: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            var sumX = 0f
            var sumY = 0f
            for (i in 0 until it.pointerCount) {
                // 在 ACTION_POINTER_UP 时, 此时抬起手指的坐标不应该计入
                if (it.actionMasked != MotionEvent.ACTION_POINTER_UP || i != it.actionIndex) {
                    sumX += it.getX(i)
                    sumY += it.getY(i)
                }
            }
            val count = if (it.actionMasked == MotionEvent.ACTION_POINTER_UP) {
                it.pointerCount - 1
            } else {
                it.pointerCount
            }
            focusX = sumX / count
            focusY = sumY / count
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                    downX = focusX
                    downY = focusY
                }
                MotionEvent.ACTION_MOVE -> {
                    offsetX = focusX - downX + originalOffsetX
                    offsetY = focusY - downY + originalOffsetY

                    invalidate()
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

}