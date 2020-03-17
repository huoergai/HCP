package com.huoergai.hcp.l12bitmap_drawable

import android.animation.ObjectAnimator
import android.animation.PointFEvaluator
import android.graphics.PointF
import android.os.Bundle
import com.huoergai.hcp.R
import com.huoergai.hcp.base.BaseActivity
import kotlin.random.Random

/**
 * 属性动画和硬件加速
 */
class L12Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_l12)

        val rtv: RollTextView = findViewById(R.id.l12_rtv)
        val pv: PointView = findViewById(R.id.l12_pv)
        val cv: CircleView = findViewById(R.id.l12_cv)
        val dv: DynamicView = findViewById(R.id.l12_dv)

        rtv.setOnClickListener {
            ObjectAnimator.ofObject(
                rtv,
                "content",
                TextEvaluator(),
                TextEvaluator.provinces[Random.nextInt(34)]
            ).apply {
                duration = 1500
            }.start()
        }

        pv.setOnClickListener { v ->
            ObjectAnimator.ofObject(
                v,
                "point",
                PointFEvaluator(),
                PointF(
                    Random.nextDouble(300.00).toFloat() + 20,
                    Random.nextDouble(300.00).toFloat() + 20
                )
            ).apply { duration = 1000 }.start()

        }

        cv.setOnClickListener { v ->
            ObjectAnimator.ofFloat(
                v,
                "radius",
                Random.nextDouble(100.00).toFloat() + 20
            ).apply { duration = 800 }.start()
        }


    }
}