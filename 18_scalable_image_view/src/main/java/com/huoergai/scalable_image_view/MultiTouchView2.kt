package com.huoergai.scalable_image_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View

/**
 * D&T: 2020-05-08 10:17 PM
 * Des: 独立型/互不干扰型
 */
class MultiTouchView2 : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // private val path = Path()

    private val paths = SparseArray<Path>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    // path.moveTo(it.x, it.y)
                    val pId = it.getPointerId(it.actionIndex)
                    val tmpPath = Path()
                    tmpPath.moveTo(it.getX(it.actionIndex), it.getY(it.actionIndex))
                    paths.append(pId, tmpPath)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    // path.lineTo(it.x, it.y)

                    for (i in 0 until it.pointerCount) {
                        val temp = paths.get(it.getPointerId(i))
                        temp.lineTo(it.getX(i), it.getY(i))
                    }
                    invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    // path.reset()
                    paths.remove(it.getPointerId(it.actionIndex))
                    // paths.forEach(())
                    invalidate()
                }
            }
        }

        return true
        // return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Util.dp2px(4f)
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until paths.size()) {
            canvas?.drawPath(paths[i], paint)
        }
    }


}