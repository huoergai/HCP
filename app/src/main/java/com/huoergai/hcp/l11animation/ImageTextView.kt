package com.huoergai.hcp.l11animation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.huoergai.hcp.Utils

class ImageTextView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val textBounds = Rect()
    private var staticLayout: StaticLayout? = null
    private val text =
        "The Eve of St. Agnes St. Agnes’ Eve—Ah, bitter chill it was! The owl, for all his feathers, was a-cold;" +
                "The hare limp’d trembling through the frozen grass," +
                "And silent was the flock in woolly fold: Numb were the Beadsman’s fingers, while he told" +
                "His rosary, and while his frosted breath, Like pious incense from a censer old, Seem’d taking flight for heaven, without a death," +
                "Past the sweet Virgin’s picture, while his prayer he saith." +
                "His prayer he saith, this patient, holy man;" +
                "Then takes his lamp, and riseth from his knees," +
                "And back returneth, meagre, barefoot, wan," +
                "Along the chapel aisle by slow degrees:" +
                "The sculptur’d dead, on each side, seem to freeze," +
                "Emprison’d in black, purgatorial rails: Knights, ladies, praying in dumb orat’ries, He passeth by; and his weak spirit fails"

    constructor(context: Context) : this(context, null)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, textPaint, w).build()
        } else {
            StaticLayout(
                text,
                textPaint,
                w,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                0f,
                false
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val bitmap = Utils.scaleImage(resources, Utils.dp2px(30f), Utils.dp2px(30f))
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        paint.textSize = Utils.dp2px(14f)

        paint.getTextBounds(text, 0, text.length, textBounds)

        var start = 0
        var yOffset = paint.fontSpacing
        var xOffset = 0f
        var count = 0
        var currentIndex = 0
        var avaliableWidth = 0f
        while (start < text.length) {
            avaliableWidth = if (yOffset < bitmap.height + paint.fontSpacing) {
                xOffset = bitmap.width.toFloat()
                (width - bitmap.width).toFloat()
            } else {
                xOffset = 0f
                width.toFloat()
            }

            count = paint.breakText(
                text, start, text.length, true, avaliableWidth, null
            )
            canvas.drawText(text, start, start + count, xOffset, yOffset, paint)
            start += count
            currentIndex += count
            yOffset += paint.fontSpacing
        }

    }
}