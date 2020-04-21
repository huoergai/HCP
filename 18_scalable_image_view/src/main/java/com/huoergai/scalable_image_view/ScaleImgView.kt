package com.huoergai.scalable_image_view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import kotlin.math.max
import kotlin.math.min

class ScaleImgView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val imgWidth = Util.dp2px(300f)
    private val bitmap = Util.getAvatar(resources, imgWidth.toInt())

    // initial offset
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var deltaOffsetX = 0f
    private var deltaOffsetY = 0f

    private var curScale = 1f
    private var scaleBigFlag = false
    private var bigScale = 1f
    private var smallScale = 1f
    private var overScaleFactor = 1.5f

    // temporary scale factor during animation state
    private var scaleFaction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var scaleAnim: ObjectAnimator
    private var gestureDetector: GestureDetectorCompat
    private var scroller: OverScroller
    private var animRunner: AnimRunnable

    init {
        scaleAnim = ObjectAnimator.ofFloat(this, "scaleFaction", 0f, 1f)
        gestureDetector = GestureDetectorCompat(context, ImgGestureListener())
        // gestureDetector.setOnDoubleTapListener(this)
        // Scroller() will neglect the start velocity,
        // apply only to scroll from one place to another with no specific start speed.
        scroller = OverScroller(context, LinearInterpolator())
        animRunner = AnimRunnable()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // calculate offset
        originalOffsetX = (w.toFloat() - bitmap.width) / 2f
        originalOffsetY = (h.toFloat() - bitmap.height) / 2f

        // calculate scale size
        val scaleX = w.toFloat() / bitmap.width
        val scaleY = h.toFloat() / bitmap.height
        if (scaleX > scaleY) {
            bigScale = scaleX * overScaleFactor
            smallScale = scaleY
        } else {
            bigScale = scaleY * overScaleFactor
            smallScale = scaleX
        }

        deltaOffsetX = (width.toFloat() - bitmap.width * bigScale) / 2f
        deltaOffsetY = (height.toFloat() - bitmap.height * bigScale) / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // curScale = if (scaleFlagBig) {
        //     bigScale
        // } else {
        //     smallScale
        // }
        canvas?.translate(offsetX * scaleFaction, offsetY * scaleFaction)
        curScale = smallScale + (bigScale - smallScale) * scaleFaction
        canvas?.scale(curScale, curScale, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }


    inner class ImgGestureListener : GestureDetector.SimpleOnGestureListener() {
        // +++++++++++++++++++++++++++ setOnDoubleTapListener +++++++++++++++++++++++++++

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            scaleBigFlag = !scaleBigFlag
            if (scaleBigFlag) {
                e?.let {
                    offsetX = (e.x - width / 2f) * (1 - (bigScale / smallScale))
                    offsetY = (e.y - height / 2f) * (1 - (bigScale / smallScale))
                    fixOffset()
                }
                scaleAnim.start()
            } else {
                scaleAnim.reverse()
            }
            return true
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

        // --------------------------- setOnDoubleTapListener ---------------------------

        // +++++++++++++++++++++++++++ OnGestureListener +++++++++++++++++++++++++++
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
            if (scaleBigFlag) {
                offsetX -= distanceX
                offsetY -= distanceY

                // constrain view content in the window to avoid empty space, easy to ignore bitmap's scale
                fixOffset()
                invalidate()
            }
            return true
        }

        private fun fixOffset() {
            // deltaOffsetX < offsetX <-deltaOffsetX
            offsetX = max(offsetX, deltaOffsetX)
            offsetX = min(offsetX, -deltaOffsetX)
            // deltaOffsetY < offsetY <-deltaOffsetY
            offsetY = max(offsetY, deltaOffsetY)
            offsetY = min(offsetY, -deltaOffsetY)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (scaleBigFlag) {
                // add scroll inertial ability
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    deltaOffsetX.toInt(),
                    -deltaOffsetX.toInt(),
                    deltaOffsetY.toInt(),
                    -deltaOffsetY.toInt()
                )
                // add overX overY parameters, the content could scroll off the bound for a specific distance
                // and bounce back to the bounder.
                //   scroller?.fling(
                //       offsetX.toInt(),
                //       offsetY.toInt(),
                //       velocityX.toInt(),
                //       velocityY.toInt(),
                //       deltaOffsetX.toInt(),
                //       -deltaOffsetX.toInt(),
                //       deltaOffsetY.toInt(),
                //       -deltaOffsetY.toInt(),
                //       Util.dp2px(50f).toInt(),
                //       Util.dp2px(50f).toInt()
                //   )

                // post() execute immediately on the main thread, since invalidate() function is very heavy,
                // postOnAnimation() is a better choice. it executes on the next animation time step.
                // ViewCompat.postOnAnimation(this, this)
                postOnAnimation(animRunner)
            }
            return true
        }

        // --------------------------- OnGestureListener ---------------------------
    }

    inner class AnimRunnable : Runnable {
        override fun run() {
            val unfinished = scroller.computeScrollOffset()
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            // without refreshing the view will skip several frames and looks weird
            invalidate()
            if (unfinished) {
                postOnAnimation(this)
            }

        }
    }
}