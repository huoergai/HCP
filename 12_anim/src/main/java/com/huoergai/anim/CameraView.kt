package com.huoergai.anim

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-05-31 10:14 AM
 * Des:
 */
class CameraView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imgWidth = Utils.dp2px(260f)
    private val imgOffset = Utils.dp2px(60f)
    private val bitmap = Utils.getAvatar(resources, R.drawable.avatar_rengwuxian, imgWidth.toInt())
    private val camera = Camera()

    var topFlip: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        // camera.rotateX(bottomFlip)
        // -8*72
        // camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            // it.drawCircle(width / 2f, height / 2f, 0.6f * width / 2, paint)
            // it.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, paint)

            it.save()
            it.translate(imgOffset + bitmap.width / 2f, imgOffset + bitmap.height / 2f)
            it.rotate(-flipRotation)

            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(it)
            camera.restore()

            // it.clipRect(-bitmap.width / 2f, 0f, bitmap.width / 2f, bitmap.height / 2f)
            it.clipRect(
                -bitmap.width.toFloat(),
                0f,
                bitmap.width.toFloat(),
                bitmap.height.toFloat()
            )
            it.rotate(flipRotation)
            it.translate(-(imgOffset + bitmap.width / 2f), -(imgOffset + bitmap.height / 2f))
            it.drawBitmap(bitmap, imgOffset, imgOffset, paint)
            it.restore()

            // ---------------------------------  ----------------------------------

            it.save()
            it.translate(imgOffset + bitmap.width / 2f, imgOffset + bitmap.height / 2f)
            it.rotate(-flipRotation)

            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(it)
            camera.restore()

            // it.clipRect(-bitmap.width / 2f, -bitmap.height / 2f, bitmap.width / 2f, 0f)
            it.clipRect(
                -bitmap.width.toFloat(),
                -bitmap.height.toFloat(),
                bitmap.width.toFloat(),
                0f
            )
            it.rotate(flipRotation)
            it.translate(-(imgOffset + bitmap.width / 2f), -(imgOffset + bitmap.height / 2f))
            it.drawBitmap(bitmap, imgOffset, imgOffset, paint)
            it.restore()
        }

    }
}