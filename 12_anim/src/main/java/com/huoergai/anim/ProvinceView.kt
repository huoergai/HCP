package com.huoergai.anim

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-06-02 04:46 PM
 * Des:
 */
class ProvinceView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val provinces = listOf<String>(
        "北京",
        "上海",
        "深圳",
        "广州",
        "成都",
        "重庆",
        "天津",
        "南京",
        "苏州",
        "杭州",
        "海口",
        "珠海",
        "佛山",
        "西安"
    )
    var provinceNumber = 0
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.textSize = Utils.dp2px(80f)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            it.drawText(provinces[provinceNumber], width / 2f, height / 2f, paint)
        }

    }

    inner class ProvinceEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
            var result = ""
            if (startValue != null && endValue != null) {
                val startIndex = provinces.indexOf(startValue)
                val endIndex = provinces.indexOf(endValue)
                result = provinces[startIndex + (fraction * (endIndex - startIndex)).toInt()]
            }
            return result
        }

    }

}