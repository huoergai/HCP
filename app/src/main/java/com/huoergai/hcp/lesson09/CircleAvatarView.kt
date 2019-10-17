package com.huoergai.hcp.lesson09

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.huoergai.hcp.R
import com.huoergai.hcp.Utils


class CircleAvatarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    private var nw: Int = 0
    private var nh: Int = 0

    constructor(context: Context) : this(context, null)

    private fun scaleImage(resources: Resources, reqWidth: Int, reqHeight: Int): Bitmap {
        // get image information
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeResource(resources, R.drawable.kitty, options)
        Log.d("CircleAvatarView", "img type: " + options.outMimeType)
        Log.d("CircleAvatarView", "img w: " + options.outWidth)
        Log.d("CircleAvatarView", "img h: " + options.outHeight)

        // scale image to the given dimension but keep its original aspect-ratio
        val ow = options.outWidth
        val oh = options.outHeight

        var sampleSize = 1

        if (oh > reqHeight || ow > reqWidth) {
            val halfHeight = oh / 2
            val halfWidth = ow / 2
            while (halfHeight / sampleSize >= reqHeight && halfWidth / sampleSize >= reqWidth) {
                sampleSize *= 2
            }
        }
        options.inSampleSize = sampleSize
        options.inJustDecodeBounds = false
        val scaledBitmap = BitmapFactory.decodeResource(resources, R.drawable.kitty, options)
        Log.d("CircleAvatarView", "img type: " + options.outMimeType)
        Log.d("CircleAvatarView", "img w: " + options.outWidth)
        Log.d("CircleAvatarView", "img h: " + options.outHeight)
        return scaledBitmap
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        nw = w
        nh = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val saveLayer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint)

        val bitmap = scaleImage(resources, width, height)
        canvas.drawBitmap(bitmap, 0f, 120f, paint)

        paint.xfermode = xFermode
        paint.color = Color.BLUE
        canvas.drawCircle(nw / 2f, nh / 2f, Utils.dp2px(150f), paint)
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(4f)
        canvas.drawCircle(nw / 2f, nh / 2f, Utils.dp2px(150f), paint)
    }
}