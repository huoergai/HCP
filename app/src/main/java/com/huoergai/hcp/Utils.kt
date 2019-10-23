package com.huoergai.hcp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue

class Utils {
    companion object {
        fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )
        }

        fun getZForCamera(): Float {
            return -4 * Resources.getSystem().displayMetrics.density
        }

        fun scaleImage(resources: Resources, reqWidth: Float, reqHeight: Float): Bitmap {
            // get image information
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeResource(resources, R.drawable.kitty, options)
            Log.d("AvatarView", "img type: " + options.outMimeType)
            Log.d("AvatarView", "img w: " + options.outWidth)
            Log.d("AvatarView", "img h: " + options.outHeight)

            // scale image to the given dimension but keep its original aspect-ratio
            val ow = options.outWidth
            val oh = options.outHeight

            var sampleSize = 1

            if (oh > reqHeight || ow > reqWidth) {
                val halfHeight = oh / 2
                val halfWidth = ow / 2
                while (halfHeight / sampleSize >= reqHeight || halfWidth / sampleSize >= reqWidth) {
                    sampleSize++
                }
            }
            options.inSampleSize = sampleSize
            options.inJustDecodeBounds = false
            val scaledBitmap = BitmapFactory.decodeResource(resources, R.drawable.kitty, options)
            Log.d("AvatarView", "img type: " + options.outMimeType)
            Log.d("AvatarView", "img w: " + options.outWidth)
            Log.d("AvatarView", "img h: " + options.outHeight)
            return scaledBitmap
        }
    }
}