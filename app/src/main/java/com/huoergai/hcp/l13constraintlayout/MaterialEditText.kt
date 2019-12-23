package com.huoergai.hcp.l13constraintlayout

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.EditText
import com.huoergai.hcp.Utils

class MaterialEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val text_size = Utils.dp2px(16f)
    private val text_magin = Utils.dp2px(8f)
    private val text = "holiday"

}