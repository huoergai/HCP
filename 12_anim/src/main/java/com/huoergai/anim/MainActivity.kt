package com.huoergai.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

/**
 * 12.属性动画
 *    ViewPropertyAnimator
 *    ObjectAnimator:
 *    Interpolator
 *    AnimationSet
 *    PropertyValuesHolder
 *    TypeEvaluator: 动画完成度对应的 View 属性实际值
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_main)
        // 1. ViewPropertyAnimator
        // anim01()

        // setContentView(R.layout.activity_main_circle_view)
        // 2.ObjectAnimator
        // circleView()

        // AnimatorSet/PropertyValuesHolder
        setContentView(R.layout.activity_main_camera_view)
        animSet()

        // TypeEvaluator: PointView
        // setContentView(R.layout.activity_main_point_view)
        // pointView()


        // setContentView(R.layout.activity_main_province_view)
        // provinceView()
    }

    /**
     * 属性动画：ViewPropertyAnimator
     *  1.通过不断的改变 view 的属性，使效果看起来是连续的动画。
     *  2.限制性强，只能使用现有动画属性，不能根据自定义的属性做动画。
     *
     */
    private fun anim01() {
        val view = findViewById<View>(R.id.view)

        var aniFlag = true
        val ani: ViewPropertyAnimator = view.animate()
            .setDuration(1000)
        view.setOnClickListener {
            if (aniFlag) {
                ani.scaleX(1.5f)
                    .scaleY(1.5f)
                    .translationY(Utils.dp2px(120f))
            } else {
                ani.scaleX(0.8f)
                    .scaleY(0.8f)
                    .translationY(-Utils.dp2px(120f))
            }
            aniFlag = !aniFlag
            ani.start()

        }
    }

    /**
     * ObjectAnimator
     *  1.可对自定义属性做动画
     *  2.invalidate(): 字面意思是"使无效",使当前 View 的内容绘制区域无效，则下次界面刷新到来的时候，
     *    View 的 onDraw() 会执行，从而更新 view。
     *  3.
     */
    private fun circleView() {
        val cv = findViewById<CircleView>(R.id.circle_view)
        val radiusAnim = ObjectAnimator.ofFloat(cv, "radius", cv.radius, Utils.dp2px(120f))
        var aniFlag = true
        cv.setOnClickListener {
            if (aniFlag) {
                radiusAnim.setDuration(1000)
                    .start()
            } else {
                radiusAnim.reverse()
            }
            aniFlag = !aniFlag
        }

    }

    private fun animSet() {
        val cv = findViewById<CameraView>(R.id.camera_view)
        cv.setOnClickListener {

            // 方式1
            val bottomAnim = ObjectAnimator.ofFloat(cv, "bottomFlip", 0f, 45f)
                .setDuration(1500)
            val topAnim = ObjectAnimator.ofFloat(cv, "topFlip", 0f, -45f)
                .setDuration(1500)
            val flipRotateAnim = ObjectAnimator.ofFloat(cv, "flipRotation", 270f)
                .setDuration(1500)

            val anim = AnimatorSet().apply {
                playSequentially(bottomAnim, topAnim, flipRotateAnim)
            }


            // 方式2: 同一个 View 的多个属性做动画: PropertyValuesHolder
            /*val bottomFlipHol der = PropertyValuesHolder.ofFloat("bottomFlip", 45f)
            val topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45f)
            val flipRotateHolder = PropertyValuesHolder.ofFloat("flipRotation", 270f)

            val anim = ObjectAnimator.ofPropertyValuesHolder(
                cv,
                bottomFlipHolder,
                topFlipHolder,
                flipRotateHolder
            ).setDuration(3000)*/


            var animFlag = true
            cv.setOnClickListener {
                if (animFlag) {
                    anim.start()
                } else {
                    anim.reverse()
                }
                animFlag = !animFlag
            }
        }
    }

    private fun pointView() {
        val pointV = findViewById<PointView>(R.id.point_view)

        val targetPoint = PointF(Utils.dp2px(300f), Utils.dp2px(300f))
        val anim = ObjectAnimator.ofObject(pointV, "point", PointFEvaluator(), targetPoint)
            .setDuration(1000)
        anim.interpolator = AccelerateInterpolator()

        var animFlag = true
        pointV.setOnClickListener {
            if (animFlag) {
                anim.start()
            } else {
                anim.reverse()
            }
            animFlag = !animFlag
        }

    }

    inner class PointFEvaluator : TypeEvaluator<PointF> {
        override fun evaluate(fraction: Float, startValue: PointF?, endValue: PointF?): PointF {
            val tmp = PointF()
            if (startValue != null && endValue != null) {
                tmp.x = startValue.x + fraction * (endValue.x - startValue.x)
                tmp.y = startValue.y + fraction * (endValue.y - startValue.y)
            }
            return tmp
        }

    }

    /**
     * 也可通过 TypeEvaluator
     */
    private fun provinceView() {
        val pv = findViewById<ProvinceView>(R.id.province_view)
        pv.setOnClickListener {
            val anim = ObjectAnimator.ofInt(pv, "provinceNumber", Random.nextInt(12))
            anim.duration = 800
            anim.interpolator = AccelerateInterpolator()
            anim.start()
        }
    }

}
