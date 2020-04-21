package com.huoergai.touch_event

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.abs

class TouchLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    /**
     * 是否延迟子 View 的按下状态
     * 返回 false 会快些
     */
    override fun shouldDelayChildPressedState(): Boolean {
        // return super.shouldDelayChildPressedState()
        return false
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    private var downX: Float? = null
    private val DIFF: Float = 10f
    private var INTERCEPT_FLAG = false
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { e ->
            if (e.actionMasked == MotionEvent.ACTION_DOWN) {
                downX = e.x
                Log.d("TouchLayout", "new downX = $downX")
                return@let
                // return false
            } else if (e.actionMasked == MotionEvent.ACTION_UP) {
                Log.d("TouchLayout", "downX = $downX, e.x = ${e.x}")
                downX?.let { dx ->
                    return if (abs(e.x - dx) > DIFF) {
                        Log.d("TouchLayout", "return true")
                        INTERCEPT_FLAG = true
                        true
                    } else {
                        INTERCEPT_FLAG = false
                        false
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (INTERCEPT_FLAG) {
            return true
        }
        return super.onTouchEvent(event)
    }
}