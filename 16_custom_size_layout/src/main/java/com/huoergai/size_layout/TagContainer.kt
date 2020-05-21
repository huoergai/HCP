package com.huoergai.size_layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import kotlin.math.max

/**
 * D&T: 2020-05-20 04:22 PM
 * Des:
 */
class TagContainer : ViewGroup {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val childMargin = Utils.dp2px(2f).toInt()
    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var measuredHeight = MeasureSpec.getSize(heightMeasureSpec)

        var usedHeight = childMargin
        var lineUsedWidth = childMargin
        var maxWidth = 0
        var lineMaxHeight = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            measureChildWithMargins(
                childView,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                usedHeight
            )

            // 换行 withMode != MeasureSpec.UNSPECIFIED
            if (childView.measuredWidth > measuredWidth - lineUsedWidth) {
                lineUsedWidth = 0
                usedHeight += lineMaxHeight + childMargin
                lineMaxHeight = 0
                // remeasure
                measureChildWithMargins(
                    childView,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    usedHeight
                )
            }

            val childBound: Rect
            if (childrenBounds.size > i) {
                childBound = childrenBounds[i]
            } else {
                childBound = Rect()
                childrenBounds.add(childBound)
            }

            childBound.set(
                lineUsedWidth,
                usedHeight,
                lineUsedWidth + childView.measuredWidth,
                usedHeight + childView.measuredHeight
            )

            lineUsedWidth += childView.measuredWidth + childMargin
            maxWidth = max(maxWidth, lineUsedWidth)
            lineMaxHeight = max(lineMaxHeight, childView.measuredHeight)
        }
        usedHeight += lineMaxHeight
        // measuredHeight = min(measuredHeight, usedHeight)
        measuredWidth = maxWidth
        measuredHeight = usedHeight
        Log.d("TagLayout", "measuredWidth=$measuredWidth")
        Log.d("TagLayout", "measuredHeight=$measuredHeight")
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        // return super.generateDefaultLayoutParams()
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val childBound = childrenBounds[i]
            childView.layout(
                childBound.left,
                childBound.top,
                childBound.right,
                childBound.bottom
            )
        }
    }
}