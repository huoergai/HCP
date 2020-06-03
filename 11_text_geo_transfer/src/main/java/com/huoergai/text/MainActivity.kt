package com.huoergai.text

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 文字测量和几何变换（text measurement & geometry transformation）
 *
 * 几何变换：translate rotate scale skew
 * 变换的时候为方便理清思路，使用逆序和操作视图而不是坐标系。
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
