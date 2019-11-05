package com.huoergai.hcp.lesson12

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

/**
 * 切换时带翻滚切换效果的 Text 显示控件
 */
class RollTextView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val paint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textSize = Utils.dp2px(40f)
        textAlign = Paint.Align.CENTER
    }
    private var content = "北京市"

    fun getContent(): String {
        return content
    }

    fun setContent(text: String) {
        content = text
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(content, width / 2f, height / 2f, paint)
    }
}