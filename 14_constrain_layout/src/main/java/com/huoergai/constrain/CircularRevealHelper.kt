package com.huoergai.constrain

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

/**
 * D&T: 2020-05-29 10:24 AM
 * Des:
 */
class CircularRevealHelper : ConstraintHelper {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)

        for (rId in referencedIds) {
            container?.let {
                val view = it.getViewById(rId)
                val radius = hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
                ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, radius)
                    .setDuration(1500)
                    .start()
            }
        }
    }
}