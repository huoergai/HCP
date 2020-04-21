package com.huoergai.drag_nestedscroll.rv

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRv : RecyclerView {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    private var tx: Float = 0f
    private var ty: Float = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val llm = (layoutManager as LinearLayoutManager)
        val firstPos = llm.findFirstCompletelyVisibleItemPosition()


        // parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }

}