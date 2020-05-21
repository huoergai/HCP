package com.huoergai.size_layout

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicInteger

/**
 * 自定义尺寸和布局、手写 TagLayout
 */
class MainActivity : AppCompatActivity() {

    private val count = AtomicInteger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *  1.继承已有的 View, 简单改写它们的尺寸： 重写 onMeasure()
         *    a. 用 getMeasuredWidth() 和 getMeasuredHeight() 获取到测量出的尺寸。
         *    b. 计算出最终需要的尺寸
         *    c. 使用 setMeasuredDimension(width,height) 把结果保存
         *
         *  2.对自定义 View 进行完全的自定义尺寸计算： 重写 onMeasure()
         *    a.计算出自己的尺寸
         *    b.用 resolveSize() 或者 resolveSizeAndSate() 修正结果
         *    c.使用 setMeasuredDimension(with,height) 保存结果
         *
         *  3.自定义 Layout： 重写 onMeasure() 和 onLayout()
         *    a.在 onMeasure 中要适配控件不同的宽高属性设置时, 需要测量子 View 及其 padding 和 margin
         *    b.使用 measureChildWithMargins() 而不是 measureChild()
         *    c.关于异常 "cannot be cast to android.view.ViewGroup$MarginLayoutParams",
         *      需要重写 ViewGroup 的 generateLayoutParams() 和 generateDefaultLayoutParams() 方法.
         *
         */

        // val tagLayout: TagLayout = findViewById(R.id.tag_layout)
        val tagLayout: TagContainer = findViewById(R.id.tag_layout)
        tagLayout.setOnClickListener {
            print("hello")
            Log.d("hello", "")
            val child: ColorfulTextView = ColorfulTextView(this)
            child.text = "hello ${count.getAndIncrement()}"
            child.setOnClickListener {
                Toast.makeText(
                    this,
                    " ${child.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            tagLayout.addView(child)
        }
    }
}
