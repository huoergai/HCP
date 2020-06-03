package com.huoergai.bitmap_drawable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *  Bitmap Drawable MaterialEditText
 *  1.Bitmap 与 Drawable 的区别
 *  2.自定义 Drawable:
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val met = findViewById<MaterialEditText>(R.id.met)
        met.setOnClickListener {
            met.userLabel = !met.userLabel
        }
    }
}
