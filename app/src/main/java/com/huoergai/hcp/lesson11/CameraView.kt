package com.huoergai.hcp.lesson11

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
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
    private val img_offset = 80.0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // get camera ready
        camera.rotateX(30f)
        camera.setLocation(0f, 0f, Utils.getZForCamera())

        // draw img
        val bitmap = Utils.scaleImage(resources, 0.9f * width, 0.9f * height)
        // canvas.clipRect(0f, 0f, width.toFloat(), height / 2f)
        canvas.drawBitmap(bitmap, img_offset, img_offset, paint)

    }


}