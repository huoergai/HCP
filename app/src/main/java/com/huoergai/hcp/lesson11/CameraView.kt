package com.huoergai.hcp.lesson11

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

/**
 * 向上翻起的图片
 */
class CameraView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()
    private var bitmap: Bitmap? = null
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Utils.scaleImage(resources, 0.9f * w, 0.9f * h)

    }

}