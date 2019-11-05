package com.huoergai.hcp.lesson13

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class MeshView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val md = MeshDrawable()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        md.setBounds(100, 100, width-100, height-100)
        md.draw(canvas)
    }


}