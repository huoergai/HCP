package com.huoergai.hcp.l21drag_nestedscroll.drag

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup

/**
 *
 */
class DragListenerGridView(context: Context, attr: AttributeSet) : ViewGroup(context, attr) {
    private var rows = 3
    private var colums = 2
    private var configuration: ViewConfiguration = ViewConfiguration.get(context)

    private lateinit var draggedView: View
    private val orderedChildren = mutableListOf<View>()
    private val dragListener = MyDragListener()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rows = 3
            colums = 2
        } else {
            rows = 2
            colums = 3
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)

        val childW = specWidth / colums
        val childH = specHeight / rows
        measureChildren(
            MeasureSpec.makeMeasureSpec(childW, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childH, MeasureSpec.EXACTLY)
        )
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left: Int
        var top: Int
        val childW = width / colums
        val childH = height / rows
        for (i in 0 until childCount) {
            left = i % 2 * childW
            top = i / 2 * childH
            val child = getChildAt(i)
            child.layout(0, 0, childW, childH)
            child.translationX = left.toFloat()
            child.translationY = top.toFloat()
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
            orderedChildren.add(child)
            child.setOnDragListener(dragListener)
        }
    }

    inner class MyDragListener : OnDragListener {
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

        private fun doSort(targetView: View) {
            var draggedIndex = -1
            var targetIndex = -1
            for (i in 0 until childCount) {
                val child = orderedChildren[i]
                if (targetView == child) {
                    targetIndex = i
                } else if (draggedView == child) {
                    draggedIndex = i
                }
            }

            orderedChildren.removeAt(draggedIndex)
            orderedChildren.add(targetIndex, draggedView)

            var childLeft: Float
            var childTop: Float
            val childWidth = width / colums
            val childHeight = height / rows
            for (i in 0 until childCount) {
                childLeft = (i % 2 * childWidth).toFloat()
                childTop = (i / 2 * childHeight).toFloat()
                orderedChildren[i].animate()
                    .translationX(childLeft)
                    .translationY(childTop).duration = 150
            }
        }
    }
}