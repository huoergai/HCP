package com.huoergai.scalable_image_view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

class ScaleImageView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imgWidth = Util.dp2px(300f)
    private var bitmap: Bitmap = Util.getAvatar(resources, imgWidth.toInt())

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var deltaOffsetX: Float = 0f
    private var deltaOffsetY: Float = 0f

    private var curScale = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var bigScale = 1f
    private var smallScale = 1f
    private val overScaleFactor = 1.5f
    private var scaleBigFlag = false

    // private val detector = GestureDetectorCompat(context, GestureDetector.SimpleOnGestureListener())
    private var gestureDetector: GestureDetectorCompat

    // private var gestureListener: ImgGestureListener

    private var animRunner: AnimRunnable

    // temporary scale during transmission
    // private var scaleFraction: Float = 0f
    //     set(value) {
    //         field = value
    //         invalidate()
    //     }

    private var scaleAnimator: ObjectAnimator
        get() {
            field.setFloatValues(smallScale, bigScale)
            return field
        }

    // scroller to handle inertial scrolling
    private var scroller: OverScroller

    private var scaleGestureDetector: ScaleGestureDetector

    /**
     * init blocks after invoking constructor
     */
    init {
        // gestureListener = ImgGestureListener()
        gestureDetector = GestureDetectorCompat(context, ImgGestureListener())
        // gestureDetector.setOnDoubleTapListener(this)
        // scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
        scaleAnimator = ObjectAnimator.ofFloat(this, "curScale", 0f, 1f)
        scroller = OverScroller(context, LinearInterpolator())
        animRunner = AnimRunnable()

        scaleGestureDetector = ScaleGestureDetector(context, ImgScaleGestureListener())
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // return super.onTouchEvent(event)
        // val gesture = gestureDetector.onTouchEvent(event)
        // return if (gesture) {
        //     scaleGestureDetector.onTouchEvent(event)
        // } else {
        //     super.onTouchEvent(event)
        // }
        var ret = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            ret = gestureDetector.onTouchEvent(event)
        }
        return ret
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w - bitmap.width) / 2f
        originalOffsetY = (h - bitmap.height) / 2f

        val ws = width.toFloat() / bitmap.width
        val hs = height.toFloat() / bitmap.height
        if (ws > hs) {
            bigScale = ws * overScaleFactor
            smallScale = hs
        } else {
            bigScale = hs * overScaleFactor
            smallScale = ws
        }
        // curScale = smallScale + (bigScale - smallScale) * scaleFraction
        curScale = smallScale

        deltaOffsetX = (bitmap.width * bigScale - width) / 2f
        deltaOffsetY = (bitmap.height * bigScale - height) / 2f

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 1.center
        // canvas.drawBitmap(bitmap, offsetX, offsetY, paint)

        // 2.centerCrop fit width/height
        //  scale = if (big) {
        //      bigScale
        //  } else {
        //      smallScale
        //  }

        //  offsetX *= scaleFraction
        //  offsetY *= scaleFraction
        // canvas.translate(offsetX, offsetY)

        val scaleFraction: Float = (curScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        // Log.d("ScaleImageView", "scale=$scale, scaleBig=$bigScale, scaleSmall=$smallScale")
        canvas.scale(curScale, curScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    inner class ImgGestureListener : GestureDetector.SimpleOnGestureListener() {
        // ++++++++++++++++++++++++++ setOnDoubleTapListener ++++++++++++++++++++++++++
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            scaleBigFlag = !scaleBigFlag
            // invalidate()
            if (scaleBigFlag) {
                e?.let {
                    // offsetX = (it.x - width / 2f) * (bigScale / smallScale) - (it.x - width / 2f)
                    // offsetX = (it.x - width / 2f) * (bigScale / smallScale - 1)
                    // ==> reverse:move back
                    offsetX = (it.x - width / 2f) * (1 - bigScale / smallScale)
                    offsetY = (it.y - height / 2f) * (1 - bigScale / smallScale)
                    fixOffset()
                }
                scaleAnimator.start()
            } else {
                // offsetX = 0f
                // offsetY = 0f
                scaleAnimator.reverse()
            }
            Log.d("ScaleImageView", "onDoubleClick big=$scaleBigFlag")
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }
        // --------------------------- setOnDoubleTapListener ---------------------------

        override fun onShowPress(e: MotionEvent?) {

        }

        override fun onLongPress(e: MotionEvent?) {

        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            // onMove

            // scrollable only when it on scale large condition
            if (scaleBigFlag || (curScale > smallScale)) {
                offsetX -= distanceX
                offsetY -= distanceY

                // constraint the scale range, without empty space
                fixOffset()
                invalidate()
            }
            return true
        }

        private fun fixOffset() {
            offsetX = max(offsetX, -deltaOffsetX)
            offsetX = min(offsetX, deltaOffsetX)
            offsetY = max(offsetY, -deltaOffsetY)
            offsetY = min(offsetY, deltaOffsetY)
        }

        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float
        ): Boolean {
            // 惯性滑动
            if (scaleBigFlag || (curScale > smallScale)) {
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
                ViewCompat.postOnAnimation(this@ScaleImageView, animRunner)
            }
            return false
        }
    }

    inner class AnimRunnable : Runnable {
        override fun run() {
            val unfinished = scroller.computeScrollOffset()
            if (unfinished) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScaleImageView, this)
            }
        }
    }

    inner class ImgScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        private var initScale: Float = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initScale = curScale
            return true
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            detector?.let {
                curScale = initScale * it.scaleFactor
                fixScale()
            }
            return false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {

        }

        private fun fixScale() {
            curScale = max(curScale, smallScale)
            curScale = min(curScale, bigScale)
            if (curScale > smallScale) {
                scaleBigFlag = false
            }
        }

    }
}