package com.huoergai.touch_event

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TouchView : View {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        /* e?.let {
             // if (it.actionMasked == MotionEvent.ACTION_UP) {
             if (it.actionMasked == MotionEvent.ACTION_DOWN) {
                 performClick()
             }
         }*/
        return super.onTouchEvent(e)
        // return true
    }

}