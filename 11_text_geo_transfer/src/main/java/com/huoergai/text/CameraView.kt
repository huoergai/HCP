package com.huoergai.text

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * D&T: 2020-05-31 07:05 PM
 * Des: 沿中部折叠
 */
class CameraView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val imgWidth = Utils.dp2px(250f)
    private val bitmap = Utils.getAvatar(resources, R.drawable.avatar_rengwuxian, imgWidth.toInt())
    private val imgOffset = Utils.dp2px(60f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val camera = Camera()

    init {
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            it.save()

            // 画下半部分
            // 5.移回原位置
            it.translate(imgOffset + bitmap.width / 2f, imgOffset + bitmap.height / 2f)

            // 4.调节 Camera 绕 X 轴旋转
            camera.save()
            camera.rotateX(30f)
            camera.applyToCanvas(it)
            camera.restore()

            // 3.裁切下半部分
            it.clipRect(-bitmap.width / 2f, 0f, bitmap.width / 2f, bitmap.height / 2f)
            // 2.将中心移动到坐标原点，
            it.translate(-(imgOffset + bitmap.width / 2f), -(imgOffset + bitmap.height / 2f))
            // 1.画图
            it.drawBitmap(bitmap, imgOffset, imgOffset, paint)
            it.restore()

            // 画上半部分
            it.save()
            it.translate(imgOffset + bitmap.width / 2f, imgOffset + bitmap.height / 2f)
            it.clipRect(-bitmap.width / 2f, -bitmap.height / 2f, bitmap.width / 2f, 0f)
            it.translate(-(imgOffset + bitmap.width / 2f), -(imgOffset + bitmap.height / 2f))
            it.drawBitmap(bitmap, imgOffset, imgOffset, paint)
            it.restore()

        }
    }

}