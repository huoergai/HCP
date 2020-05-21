package com.huoergai.size_layout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewConfiguration
import android.view.ViewGroup
import kotlin.math.max

/**
 * D&T: 2020-05-19 03:53 PM
 * Des:
 */
class TagLayout : ViewGroup {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val childMargin = Utils.dp2px(2f).toInt()

    private var screenWidth = Utils.getScreenWidth()
    private var screenHeight = Utils.getScreenHeight()
    private val viewConfig = ViewConfiguration.get(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)

        var childWidth = 0
        var childHeight = 0
        var usedLineWidth = 0
        var maxLineHeight = 0
        // Log.d("TagLayout", "screenWidth=$screenWidth screenHeight=$screenHeight")
        var count = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            /*   if (childWidth < measureWidth) {
                   childWidth += child.measuredWidth
                   if (childWidth > screenWidth) {
                       childWidth = screenWidth
                   }
               }*/
            // Log.d("TagLayout", "child width=${child.measuredWidth} child height=${child.measuredHeight}")
            // 换行
            if (child.measuredWidth >= measureWidth - usedLineWidth) {
                usedLineWidth = 0
                childHeight += maxLineHeight + childMargin
                maxLineHeight = 0
                Log.d("TagLayout", "measure line=${++count}")
            }
            usedLineWidth += child.measuredWidth + childMargin
            maxLineHeight = max(maxLineHeight, child.measuredHeight)

            /*if (childHeight > screenHeight) {
                break
            }*/
        }

       /* if (childHeight < maxLineHeight) {
            childHeight = maxLineHeight
        }*/

        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        Log.d("TagLayout", "width=$childWidth height=$childHeight")
        // Log.d("TagLayout", "widthMeasure=${MeasureSpec.getSize(widthMeasureSpec)} heightMeasure=${MeasureSpec.getSize(heightMeasureSpec)}")
        // setMeasuredDimension(childWidth, childHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var usedLineWidth = 0
        var usedLineHeight = 0
        var maxLineHeight = 0
        var count = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            // 换行
            if (width - usedLineWidth < child.measuredWidth) {
                usedLineWidth = 0
                usedLineHeight += maxLineHeight + childMargin
                maxLineHeight = 0
                Log.d("TagLayout", "layout line=${++count}")
            }

            child.layout(
                usedLineWidth,
                usedLineHeight,
                usedLineWidth + child.measuredWidth,
                usedLineHeight + child.measuredHeight
            )
            usedLineWidth += child.measuredWidth + childMargin
            maxLineHeight = max(maxLineHeight, child.measuredHeight)

            if (usedLineHeight > screenHeight) {
                break
            }
        }
    }

}