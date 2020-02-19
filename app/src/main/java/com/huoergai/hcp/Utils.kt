package com.huoergai.hcp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.TypedValue
import kotlin.random.Random

class Utils {
    companion object {
        private const val basic = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz"

        /**
         * 获取指定长度的随机字符串 [0,9] [a,z] [A,Z]
         */
        @JvmStatic
        fun randomString(len: Int): String {
            val ret = StringBuilder()
            for (i in 1..len) {
                val index = Random.nextInt(0, 61)
                ret.append(basic[index])
            }
            return ret.toString()
        }

        /**
         * convert dp to px base on device display density
         */
        fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )
        }

        /**
         * get scaled z of camera
         */
        fun getZForCamera(): Float {
            return -8 * Resources.getSystem().displayMetrics.density
        }

        /**
         * 获取头像
         */
        fun getAvatar(res: Resources, drawableRes: Int, width: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, drawableRes, options)
            options.inDensity = options.outWidth
            options.inTargetDensity = width
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeResource(res, drawableRes, options)
            Log.d("Utils", "is bitmap null:" + (bitmap == null))
            return bitmap
        }

        /**
         * 图片资源伸缩
         */
        fun scaleImage(resources: Resources, reqWidth: Float, reqHeight: Float): Bitmap {
            // get image information
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeResource(resources, R.drawable.shaw, options)
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