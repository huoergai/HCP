package com.huoergai.constrain

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Group
import androidx.constraintlayout.widget.Placeholder
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private var animToggle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1.多个控件整个相对于父控件居中
        // setContentView(R.layout.activity_main_sample_01)

        // 2.控件（或多个控件作为整体）相对于另一个控件居中
        // setContentView(R.layout.activity_main_sample_02)

        // 3.constrain weight: 类似 LinearLayout 中的 weight
        // setContentView(R.layout.activity_main_sample_03)

        // 4.baseline: 排除尺寸、padding 等因素影响，使之始终与参考控件的基线 baseline 对齐。
        // setContentView(R.layout.activity_main_sample_04)

        // 5.circle position 角度定位:根据参考点、直径和弧度定位
        // setContentView(R.layout.activity_main_sample_05)
        // circlePosition()

        // 6.constrained width/height: 当 width/height 为 0dp 时，如果设置了左右约束于参考控件，
        // 则其宽高自动约束于参考控件； 当 width/height 为 wrap_content 时则需要设置 constraint_width 为 true.
        // setContentView(R.layout.activity_main_sample_06)

        // 7.bias Horizontal_bias/Vertical_bias 水平/垂直偏移
        // setContentView(R.layout.activity_main_sample_07)

        // 8. goneMargin
        // setContentView(R.layout.activity_main_sample_08)

        // 9.dimensionRatio: 自定义控件的宽高比
        // setContentView(R.layout.activity_main_sample_09)

        // 10.GuideLine：为其它 View 布局提供导航线
        // setContentView(R.layout.activity_main_sample_10)

        // 11.Group
        // setContentView(R.layout.activity_main_sample_11)
        // group()

        // 12.layer
        // setContentView(R.layout.activity_main_sample_12)
        // btnLayer()

        // 13.barrier
        // setContentView(R.layout.activity_main_sample_13)

        // 14.CircularReveal：在 xml 文件中直接应用动画，无需在 activity 中进行设置
        // setContentView(R.layout.activity_main_sample_14)

        // 15.PlaceHolder
        // setContentView(R.layout.activity_main_sample_15)

        // 16.constraintSet
        // setContentView(R.layout.activity_main_sample_16)

        // 17.Linear
        // setContentView(R.layout.activity_main_sample_17)

        // 18.Flow
        // setContentView(R.layout.activity_main_sample_18)

        // 19.ConstraintSetX 在两个布局间切换(不重新创建对象)
        setContentView(R.layout.activity_main_sample_19_start)

        /*
         * 1.当控件上下（或者左右）constraint 到其它控件的上下（或者左右）时，宽或者高设置为 0dp，
         *   则会填充到其参考控件的尺寸（区别与 match_parent）。
         * 2.使用特殊混淆工具时，如果布局中使用了 Group/Layer/Barrier 等辅助控件，当中引用到其它 id 时，可能会造成异常。
         *
         */


    }

    /**
     * No.5
     */
    private fun circlePosition() {
        val ivSun = findViewById<ImageView>(R.id.iv_sun)
        val ivEarth = findViewById<ImageView>(R.id.iv_earth)
        val ivMoon = findViewById<ImageView>(R.id.iv_moon)


        val sunRotateAnim = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 20000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
        }

        val earthRotateAnim = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 5000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
        }

        val earthAnim = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 10000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val param = ivEarth.layoutParams as ConstraintLayout.LayoutParams
                // param.circleAngle = 45 + it.animatedFraction * 360
                param.circleAngle += 0.5f
                ivEarth.requestLayout()
            }
        }

        val moonAnim = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000L
            repeatCount = INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val param = ivMoon.layoutParams as ConstraintLayout.LayoutParams
                // param.circleAngle = 300 + it.animatedFraction * 360
                param.circleAngle += 0.5f
                ivMoon.requestLayout()
            }
        }

        ivSun.setOnClickListener {
            if (animToggle) {
                ivSun.animation = sunRotateAnim
                ivEarth.animation = earthRotateAnim

                sunRotateAnim.start()
                earthRotateAnim.start()

                earthAnim.start()
                moonAnim.start()

                animToggle = !animToggle
            } else {
                sunRotateAnim.cancel()
                earthRotateAnim.cancel()

                earthAnim.cancel()
                moonAnim.cancel()

                animToggle = !animToggle
            }
        }
    }

    private fun group() {
        val group = findViewById<Group>(R.id.group)
        val btnGroup = findViewById<MaterialButton>(R.id.btn_group)
        btnGroup.setOnClickListener {
            group.visibility = if (group.isVisible) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun btnLayer() {
        val layer = findViewById<Layer>(R.id.layer)
        val btn = findViewById<MaterialButton>(R.id.btn_click)
        var toggle = true
        btn.setOnClickListener {
            if (toggle) {
                layer.translationX = 100f
                layer.translationY = 100f
                layer.rotation = 45f
            } else {
                layer.translationX = 0f
                layer.translationY = 0f
                layer.rotation = 0f
            }
            toggle = !toggle
        }
    }

    /**
     * 15.placeHolder
     */
    fun onClick15(view: View) {
        TransitionManager.beginDelayedTransition(view.parent as ViewGroup)
        val ph = findViewById<Placeholder>(R.id.placeholder)
        ph.setContentId(view.id)
    }

    fun onClick16(view: View) {
        val cl = view as ConstraintLayout
        val cs = ConstraintSet().apply {
            isForceId = false
            clone(cl)
            connect(
                R.id.iv_twitter,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
        }
        cs.applyTo(cl)
    }

    private var switchFlag = true
    fun onClick19(view: View) {
        val cl = view as ConstraintLayout
        TransitionManager.beginDelayedTransition(cl)

        val cs = ConstraintSet().apply {
            isForceId = false
            if (switchFlag) {
                clone(this@MainActivity, R.layout.activity_main_sample_19_end)
            } else {
                clone(this@MainActivity, R.layout.activity_main_sample_19_start)
            }
            switchFlag = !switchFlag
        }
        cs.applyTo(cl)
    }
}
