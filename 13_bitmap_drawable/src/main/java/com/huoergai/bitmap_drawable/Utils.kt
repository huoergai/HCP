package com.huoergai.bitmap_drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

class Utils {
    companion object {

        @JvmStatic
        fun prt(msg: String) {
            println(msg)
        }

        @JvmStatic
        fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )
        }

        @JvmStatic
        fun px2dp(px: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX,
                px,
                Resources.getSystem().displayMetrics
            )
        }

        @JvmStatic
        fun getZForCamera(): Float {
            return -8 * Resources.getSystem().displayMetrics.density
        }

        @JvmStatic
        fun getAvatar(res: Resources, drawableRes: Int, width: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, drawableRes, options)
            options.inDensity = options.outWidth
            options.inTargetDensity = width
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeResource(res, drawableRes, options)
            return bitmap
        }

        @JvmStatic
        fun randomString(len: Int): String {
            return "hello"
        }
    }
}