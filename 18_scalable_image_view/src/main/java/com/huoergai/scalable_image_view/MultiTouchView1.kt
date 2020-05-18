package com.huoergai.scalable_image_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * D&T: 2020-05-08 09:10 PM
 * Des: 协作型
 */
class MultiTouchView1 : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imgWidth = Util.dp2px(300f)
    private val bitmap = Util.getAvatar(resources, imgWidth.toInt())

    private var curScale = 0f
    private var bigScale = 1f
    private var smallSmall = 0f

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    private var downX: Float = 0f
    private var downY: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            var sumX = 0f
            var sumY = 0f
            val isPointerUp = it.actionMasked == MotionEvent.ACTION_POINTER_UP
            for (i in 0 until it.pointerCount) {
                // ignore the action pointer location at action up condition
                if (!isPointerUp || i != it.actionIndex) {
                    sumX += it.getX(i)
                    sumY += it.getY(i)
                }
            }
            val count = if (isPointerUp) {
                it.pointerCount - 1
            } else {
                it.pointerCount
            }
            val focusX: Float = sumX / count
            val focusY: Float = sumY / count
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP -> {
                    downX = focusX
                    downY = focusY
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    offsetX = focusX - downX + originalOffsetX
                    offsetY = focusY - downY + originalOffsetY
                    invalidate()
                }
            }
        }

        return true
        // return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w.toFloat() - bitmap.width) / 2f
        originalOffsetY = (h.toFloat() - bitmap.height) / 2f

        val deltaX = w.toFloat() / bitmap.width
        val deltaY = h.toFloat() / bitmap.height
        if (deltaX > deltaY) {
            bigScale = deltaX
            smallSmall = deltaY
        } else {
            bigScale = deltaY
            smallSmall = deltaX
        }

        curScale = smallSmall
    }

    override fun onDraw(canvas: Canvas?) {
        // canvas?.translate(offsetX, offsetY)
        // canvas?.scale(curScale, curScale, width / 2f, height / 2f)
        // canvas?.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }


}