package com.huoergai.scalable_image_view.prac

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.huoergai.scalable_image_view.Util

/**
 * D&T: 2020-05-11 09:50 AM
 * Des: 独立型/互不干扰型
 *      1.用 pointerId 跟踪每个手指的 path
 *      2.滑动的时候根据 pointerId 所对应的 path, 并更新其路径
 * note: 核心是单独处理各个手指的焦点, 用 pointerId 对手指滑动的 path 进行绑定跟踪
 */
class SeparateTouchView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = Util.dp2px(4f)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    val temp = Path()
                    val pid = it.getPointerId(it.actionIndex)
                    temp.moveTo(it.getX(it.actionIndex), it.getY(it.actionIndex))
                    paths.append(pid, temp)
                }
                MotionEvent.ACTION_MOVE -> {
                    for (i in 0 until paths.size()) {
                        val tmp = paths[it.getPointerId(i)]
                        tmp.lineTo(it.getX(i), it.getY(i))
                    }
                    invalidate()
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    paths.remove(it.getPointerId(it.actionIndex))
                    invalidate()
                }
            }
        }

        //return super.onTouchEvent(event)
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            for (i in 0 until paths.size()) {
                if (paths[i] != null) {
                    it.drawPath(paths[i], paint)
                }
            }
        }
    }

}