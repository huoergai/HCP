package com.huoergai.scalable_image_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import kotlin.math.abs

/**
 * D&T: 2020-05-08 11:11 AM
 * Des:
 */
class RawViewPager : ViewGroup {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val velocityTracker = VelocityTracker.obtain()
    private val  scroller = OverScroller(context)
    private val viewConfig = ViewConfiguration.get(context)
    private val maxVelocity = viewConfig.scaledMaximumFlingVelocity.toFloat()
    private val minVelocity = viewConfig.scaledMinimumFlingVelocity.toFloat()

    private var scrolling = false
    private var downScrollX = 0

    private var downX = 0f
    private var downY = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)

        var result = false
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downScrollX = scrollX
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (!scrolling) {
                    val dx = downX - ev.x
                    if (abs(dx) > viewConfig.scaledPagingTouchSlop) {
                        scrolling = true
                        result = true
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
        }
        // return super.onInterceptTouchEvent(ev)
        return result
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX
            }
            MotionEvent.ACTION_MOVE -> {
                var dx: Float = downX - event.x + downScrollX
                if (dx > width) {
                    dx = width.toFloat()
                } else if (dx < 0f) {
                    dx = 0f
                }
                scrollTo(dx.toInt(), 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity)
                var targetPage: Int = 0
                if (abs(velocityTracker.xVelocity) < minVelocity) {
                    if (scrollX > width / 2) {
                        targetPage = 1
                    }
                } else if (velocityTracker.xVelocity < 0) {
                    targetPage = 1
                }
                Log.d("RawPager", "xVelocity=${velocityTracker.xVelocity}")
                Log.d("RawPager", "scrollX=$scrollX")
                val scrollDistance: Int = if (targetPage == 1) {
                    width - scrollX
                } else {
                    -scrollX
                }
                scroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }

        }

        // return super.onTouchEvent(event)
        return true
    }

    /**
     * 在 onDraw 中被调用
     */
    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidateOnAnimation()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, widthMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childL = 0
        var childR = width
        val childT = 0
        val childB = height
        for (i in 0 until childCount) {
            val tmp = getChildAt(i)
            tmp.layout(childL, childT, childR, childB)
            childL += width
            childR += width
        }
    }

}