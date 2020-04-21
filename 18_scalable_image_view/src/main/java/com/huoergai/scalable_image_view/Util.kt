package com.huoergai.scalable_image_view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

class Util {
    companion object {
        fun dp2px(dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().displayMetrics
            )
        }

        fun getAvatar(res: Resources, width: Int): Bitmap {
            val ops: BitmapFactory.Options = BitmapFactory.Options()
            ops.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, R.drawable.charles, ops)
            ops.inJustDecodeBounds = false
            ops.inDensity = ops.outWidth
            ops.inTargetDensity = width
            return BitmapFactory.decodeResource(res, R.drawable.charles, ops)
        }
    }

}