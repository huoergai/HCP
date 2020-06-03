package com.huoergai.bitmap_drawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-06-03 09:47 AM
 * Des:
 */
class DrawableView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    // private val dw = ColorDrawable(Color.CYAN)
    private val dw = MeshDrawable()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        dw.setBounds(60, 60, width - 60, height - 60)
        canvas?.let {
            dw.draw(it)
        }

    }


}