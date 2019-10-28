package com.huoergai.hcp.lesson11

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.R
import com.huoergai.hcp.Utils

/**
 * 向上翻起的图片
 */
class CameraView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()
    private var width_offset = 80.0f
    private val height_offset = 20f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // get camera ready
        camera.rotateX(40f)
        camera.setLocation(0f, 0f, Utils.getZForCamera())

        val bitmap = Utils.getAvatar(resources, R.drawable.avatar_rengwuxian, (0.7 * width).toInt())
        width_offset = (width - bitmap.width) / 2f

        // draw and clip top half
        canvas.save()
        canvas.translate(width_offset + bitmap.width / 2, height_offset + bitmap.height / 2)
        canvas.rotate(-30f)
        canvas.clipRect(
            -bitmap.width.toFloat(),
            -bitmap.height.toFloat(),
            bitmap.width.toFloat(),
            0f
        )
        canvas.rotate(30f)
        canvas.translate(-(width_offset + bitmap.width / 2), -(height_offset + bitmap.height / 2))
        canvas.drawBitmap(bitmap, width_offset, height_offset, paint)
        canvas.restore()

        // draw and clip bottom half
        canvas.save()
        canvas.translate(width_offset + bitmap.width / 2, height_offset + bitmap.height / 2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -bitmap.width.toFloat(),
            0f,
            bitmap.width.toFloat(),
            bitmap.height.toFloat()
        )
        canvas.rotate(30f)
        canvas.translate(-(width_offset + bitmap.width / 2), -(height_offset + bitmap.height / 2))
        canvas.drawBitmap(bitmap, width_offset, height_offset, paint)
        canvas.restore()

    }


}