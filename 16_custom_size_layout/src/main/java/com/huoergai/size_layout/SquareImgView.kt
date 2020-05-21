package com.huoergai.size_layout

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * D&T: 2020-05-18 04:29 PM
 * Des: 方形 ImageView
 */
class SquareImgView : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    //  override fun layout(l: Int, t: Int, r: Int, b: Int) {
    //      val size = min(r - l, b - t)
    //      Log.d("SquareImgView", "size=$size")
    //      Log.d("SquareImgView", "height=${Utils.dp2px(200f)}")
    //      super.layout(l, t, l + size, t + size)
    //      // 直接在这里修改尺寸，可以修改 View 的尺寸，但是修改后的值不能被父 View 获取到，导致父 View 布局错乱。
    //  }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }


}