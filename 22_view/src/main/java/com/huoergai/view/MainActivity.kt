package com.huoergai.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val tv = findViewById<TextView>(R.id.view_tv)

        tv.setOnClickListener {
            // 1. 设置为 warp_content 会崩溃,添加了 requestLayout() 不会崩溃。
            // it.requestLayout()
            // 当在子线程更新 UI，tv width height 设置为固定值时不会崩溃。
            thread {

                // sleep(2000)
                tv.text = "${Thread.currentThread().name} : ${SystemClock.uptimeMillis()}"
            }
        }

    }
}
