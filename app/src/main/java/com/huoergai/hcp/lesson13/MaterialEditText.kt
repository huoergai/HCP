package com.huoergai.hcp.lesson13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import com.huoergai.hcp.Utils

class MaterialEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val text_size = Utils.dp2px(16f)
    private val text_magin = Utils.dp2px(8f)
    private val text = "holiday"

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(s)) {
                    setPadding(0, 0, 0, 0)
                    return
                }
                setPadding(0, (text_size + text_magin).toInt(), 0, 0)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(text, 0f, text.length.toFloat(), paint)
    }
}