package com.huoergai.motion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout

/**
 * D&T: 2020-05-21 03:52 PM
 * Des:
 */
class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        // val ratingBar = findViewById<RatingBar>(R.id.rating_bar_film)
        // ratingBar.rating = 4.5f

        val ml = findViewById<MotionLayout>(R.id.ml)


    }
}