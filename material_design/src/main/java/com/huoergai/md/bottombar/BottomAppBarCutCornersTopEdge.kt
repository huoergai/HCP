package com.huoergai.md.bottombar

import android.annotation.SuppressLint
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * D&T: 2020-06-15 10:48 PM
 * Des: a bottomAppBar top edge that works with a diamond shaped floatingActionBar
 */
class BottomAppBarCutCornersTopEdge(
    var fabMargin: Float, roundedCornerRadius: Float, var cradleVerticalOffset: Float
) : BottomAppBarTopEdgeTreatment(fabMargin, roundedCornerRadius, cradleVerticalOffset) {

    @SuppressLint("RestrictedApi")
    override fun getEdgePath(
        length: Float, center: Float, interpolation: Float, shapePath: ShapePath
    ) {
        if (fabDiameter == 0f) {
            shapePath.lineTo(length, 0f)
            return
        }

        val diamondSize: Float = fabDiameter / 2f
        val middle = center + horizontalOffset
        val verticalOffsetRatio = cradleVerticalOffset / diamondSize
        if (verticalOffsetRatio >= 1.0f) {
            shapePath.lineTo(length, 0f)
            return
        }

        shapePath.lineTo(middle - (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(middle, (diamondSize - cradleVerticalOffset + fabMargin) * interpolation)
        shapePath.lineTo(middle + (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(length, 0f)
    }


}