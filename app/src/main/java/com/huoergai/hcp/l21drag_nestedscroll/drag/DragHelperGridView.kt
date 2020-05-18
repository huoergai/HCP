package com.huoergai.hcp.l21drag_nestedscroll.drag

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

class DragHelperGridView(context: Context, attr: AttributeSet) : ViewGroup(context, attr) {
    private var rows = 3
    private var columns = 2
    private var childW = 0
    private var childH = 0

    private lateinit var dragHelper: ViewDragHelper

    init {
        dragHelper = ViewDragHelper.create(this, MyDragHelper(this))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rows = 3
            columns = 2
        } else {
            rows = 2
            columns = 3
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        childW = width / columns
        childH = height / rows

        /*
        for (i in 0..childCount) {
            val child = getChildAt(i)
            measureChild(
                child,
                MeasureSpec.makeMeasureSpec(childW, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childH, MeasureSpec.EXACTLY)
            )
        }
        */

        measureChildren(
            MeasureSpec.makeMeasureSpec(childW, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childH, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left: Int
        var top: Int
        for (i in 0 until childCount) {
            left = i % 2 * childW
            top = childH * (i / 2)
            val child = getChildAt(i)
            child.layout(left, top, left + childW, top + childH)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class MyDragHelper(val view: View) : ViewDragHelper.Callback() {
        private var capturedLeft: Int = 0
        private var capturedTop: Int = 0

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                dragHelper.capturedView?.let { it.elevation-- }
            }
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            capturedChild.elevation = elevation + 1
            capturedLeft = capturedChild.left
            capturedTop = capturedChild.top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // settle view back to start position
            dragHelper.settleCapturedViewAt(capturedLeft, capturedTop)
            view.postInvalidateOnAnimation()
        }
    }
}