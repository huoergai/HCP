package com.huoergai.scalable_image_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * D&T: 2020-04-23 12:40 PM
 * Des: 接力型
 */
class MultiTouchView : View {
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

    private var trackedPointerId = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    trackedPointerId = it.getPointerId(0)
                    downX = it.x
                    downY = it.y
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
                MotionEvent.ACTION_MOVE -> {
                    val trackedIndex = it.findPointerIndex(trackedPointerId)
                    offsetX = it.getX(trackedIndex) - downX + originalOffsetX
                    offsetY = it.getY(trackedIndex) - downY + originalOffsetY
                    invalidate()
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    trackedPointerId = it.getPointerId(it.actionIndex)
                    downX = it.getX(it.actionIndex)
                    downY = it.getY(it.actionIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    val pi = it.getPointerId(it.actionIndex)
                    if (pi == trackedPointerId) {
                        val newIndex: Int = if (it.actionIndex == it.pointerCount - 1) {
                            it.pointerCount - 2
                        } else {
                            it.pointerCount - 1
                        }
                        trackedPointerId = it.getPointerId(newIndex)
                        downX = it.getX(newIndex)
                        downY = it.getY(newIndex)
                        originalOffsetX = offsetX
                        originalOffsetY = offsetY
                    }
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