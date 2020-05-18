package com.huoergai.scalable_image_view.prac

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.huoergai.scalable_image_view.Util

/**
 * D&T: 2020-05-10 09:22 AM
 * Des: 接力型/抢夺焦点型
 *      1.首先要在按下时记录起作用的手指 id, 并更新初始值、按下位置等相应数据。
 *      2.当有新的手指按下时, 更新起作用的手指 id, 并更新其它数据, 此时完成接力转换。
 *      3.最后当起作用的手指抬起时,  需要将工作转交给另一个手指; 此时需要判断抬起手指的 index 位置, 以更新起
 *        作用的手指 index/pointerId; 最后更新初始值、偏移量、按下位置等数据。
 * note:除了以上关键知识外, 核心是明白 ACTION_POINTER_XXX 和 actionIndex 的真实含义;
 */
class RelayTouchView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val bitmap: Bitmap = Util.getAvatar(resources, Util.dp2px(300f).toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private var trackedPointerId = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                originalOffsetX = offsetX
                originalOffsetY = offsetY

                trackedPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                originalOffsetX = offsetX
                originalOffsetY = offsetY

                trackedPointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX =
                    event.getX(event.findPointerIndex(trackedPointerId)) - downX + originalOffsetX
                offsetY =
                    event.getY(event.findPointerIndex(trackedPointerId)) - downY + originalOffsetY

                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                if (event.getPointerId(event.actionIndex) == trackedPointerId) {
                    val newPointerIndex = if (event.actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }

                    trackedPointerId = event.getPointerId(newPointerIndex)
                    downX = event.getX(newPointerIndex)
                    downY = event.getY(newPointerIndex)
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
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