package com.huoergai.scalable_image_view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

/**
 * D&T: 2020-04-29 08:59 AM
 * Des:
 */
class ImgView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imgWidth = Util.dp2px(300f)
    private var bitmap: Bitmap

    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private val overScaleFactor = 1.5f
    private var curScale: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var bigScaleFlag = false

    /*private var scaleFaction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    */
    private var scaleAnimator: ObjectAnimator

    private var scroller: OverScroller
    private var scrollRunner: ScrollRunner
    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var deltaOffsetX = 0f
    private var deltaOffsetY = 0f

    private var gestureDetector: GestureDetectorCompat
    private val imgGestureListener = ImgGestureListener()

    init {
        bitmap = Util.getAvatar(resources, imgWidth.toInt())
        gestureDetector = GestureDetectorCompat(context, imgGestureListener)
        gestureDetector.setOnDoubleTapListener(imgGestureListener)

        // scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFaction", 0f, 1f)
        scaleAnimator = ObjectAnimator.ofFloat(this, "curScale", 0f, 1f)

        scroller = OverScroller(context, LinearInterpolator())
        scrollRunner = ScrollRunner()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val ws = w.toFloat() / bitmap.width
        val hs = h.toFloat() / bitmap.height
        if (ws > hs) {
            bigScale = ws * overScaleFactor
            smallScale = hs
        } else {
            bigScale = hs * overScaleFactor
            smallScale = ws
        }
        curScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
        deltaOffsetX = (bigScale * bitmap.width - width) / 2f
        deltaOffsetY = (bigScale * bitmap.height - height) / 2f

        originalOffsetX = (w.toFloat() - bitmap.width) / 2f
        originalOffsetY = (h.toFloat() - bitmap.height) / 2f

        // println("ImgView smallScale = $smallScale")
        // println("ImgView bigScale = $bigScale")
        // println("ImgView offsetX = $offsetX")
        // println("ImgView offsetY = $offsetY")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            // val scaleF: Float = (curScale - smallScale) / (bigScale - smallScale)
            // println("ImgView scaleF=$scaleF")
            canvas.translate(offsetX, offsetY)
            // curScale = smallScale + (bigScale - smallScale) * scaleFaction
            canvas.scale(curScale, curScale, width / 2f, height / 2f)
            canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val gesture = gestureDetector.onTouchEvent(event)
        return if (gesture) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class ImgGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            // only return true can it trigger the event
            e?.let {
                // make sure the touch point is within the image
                val inImg: Boolean =
                    e.x < (width + bitmap.width * curScale) / 2f && e.x > (width - bitmap.width * curScale) / 2f && e.y > (height - bitmap.height * curScale) / 2f && e.y < (height + bitmap.height * curScale) / 2f
                if (inImg) {
                    return true
                }
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            bigScaleFlag = !bigScaleFlag
            e?.let {
                if (bigScaleFlag) {
                    //  offsetX = -(curScale - 1) * (e.x - width / 2f)
                    //  offsetY = -(curScale - 1) * (e.y - height / 2f)
                    // fixOffset()
                    scaleAnimator.start()
                } else {
                    // offsetX = 0f
                    // offsetY = 0f
                    scaleAnimator.reverse()
                }
            }
            // invalidate()
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            // add inertial scrolling
            if (bigScaleFlag) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    -deltaOffsetX.toInt(),
                    deltaOffsetX.toInt(),
                    -deltaOffsetY.toInt(),
                    deltaOffsetY.toInt()
                )
                ViewCompat.postOnAnimation(this@ImgView, scrollRunner)
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (bigScaleFlag) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        private fun fixOffset() {
            offsetX = max(offsetX, -deltaOffsetX)
            offsetX = min(offsetX, deltaOffsetX)
            offsetY = max(offsetY, -deltaOffsetY)
            offsetY = min(offsetY, deltaOffsetY)
        }
    }

    inner class ScrollRunner : Runnable {
        override fun run() {
            val unfinished = scroller.computeScrollOffset()
            if (unfinished) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ImgView, this)
            }
        }

    }

}