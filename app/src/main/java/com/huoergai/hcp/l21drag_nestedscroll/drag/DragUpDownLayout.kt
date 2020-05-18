package com.huoergai.hcp.l21drag_nestedscroll.drag

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.huoergai.hcp.R
import kotlin.math.abs

/**
 * D&T: 2020-05-17 03:56 PM
 * Des:
 */
class DragUpDownLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val viewConfig = ViewConfiguration.get(context)
    private val dragHelper = ViewDragHelper.create(this, DragCallback())

    private lateinit var childView: View

    override fun onFinishInflate() {
        super.onFinishInflate()

        childView = findViewById<View>(R.id.drag_up_down_view)
        ViewCompat.startDragAndDrop(childView, null, DragShadowBuilder(childView), childView, 0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            return dragHelper.shouldInterceptTouchEvent(it)
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            dragHelper.processTouchEvent(it)
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    inner class DragCallback : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {

            return childView == child
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {

            // scroll fast enough
            if (abs(yvel) > viewConfig.scaledMinimumFlingVelocity) {
                // down forward and settle at the bottom
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                } else {
                    dragHelper.settleCapturedViewAt(0, 0)
                }
            } else {
                // scroll slowly
                if (releasedChild.top + releasedChild.bottom < height) {
                    // not cross the middle of the screen
                    dragHelper.settleCapturedViewAt(0, 0)
                } else {
                    dragHelper.settleCapturedViewAt(0, height - releasedChild.height)
                }
            }

            postInvalidateOnAnimation()
        }

    }
}