package com.huoergai.constrain

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.VirtualLayout

/**
 * D&T: 2020-05-29 03:53 PM
 * Des:
 */
class Linear : VirtualLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val cs: ConstraintSet by lazy {
        ConstraintSet().apply {
            isForceId = false
        }
    }

    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)

        cs.clone(container)
        for (i in 1 until mCount) {
            val curr = referencedIds[i]
            val before = referencedIds[i - 1]
            cs.connect(curr, ConstraintSet.START, before, ConstraintSet.START)
            cs.connect(curr, ConstraintSet.TOP, before, ConstraintSet.BOTTOM)
            cs.applyTo(container)
        }

    }
}