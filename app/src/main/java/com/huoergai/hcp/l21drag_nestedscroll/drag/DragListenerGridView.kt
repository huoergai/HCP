package com.huoergai.hcp.l21drag_nestedscroll.drag

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

class DragListenerGridView(context: Context, attr: AttributeSet) : ViewGroup(context, attr) {
    private var ROW = 3
    private var COLUMN = 2
    private var childW = 0
    private var childH = 0
    private var configuration: ViewConfiguration = ViewConfiguration.get(context)

    private lateinit var draggedView: View
    private val orderdChildren = ArrayList<View>()
    private val dragListener = MyDragListener()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ROW = 3
            COLUMN = 2
        } else {
            ROW = 2
            COLUMN = 3
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        childW = width / COLUMN
        childH = height / ROW
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
            top = i / 2 * childH
            getChildAt(i).layout(left, top, left + childW, top + childH)
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.setOnLongClickListener { v ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(null, DragShadowBuilder(v), v, 0)
                } else {
                    v.startDrag(null, DragShadowBuilder(v), v, 0)
                }
                draggedView = v
                false
            }
            orderdChildren.add(child)
            child.setOnDragListener(dragListener)
        }
    }

    private class MyDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if (event.localState == v) {
                        v.visibility = View.INVISIBLE
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    if (event.localState != v) {
                        // sort
                        doSort(v)
                    }
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    if (event.localState == v) {
                        v.visibility = View.VISIBLE
                    }
                }
            }

            return true
        }

        private fun doSort(v: View) {


        }

    }
}