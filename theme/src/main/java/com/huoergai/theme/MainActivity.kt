package com.huoergai.theme

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton

/**
 * D&T: 2020-06-13 08:50 PM
 * Des: 夜间模式
 *
 * 1. 需设置 theme 为支持 DayNight 的 support 或者 MaterialDesign 主题
 * 2. 通过 AppCompatDelegate.setDefaultNightMode() 设置主题模式, 注意更改主题 当前 activity 会重绘.
 * 3. 避免使用硬编码方式设置颜色;
 * 4. 使用系统自带颜色 ?android:attr/ 或者 ?attr/
 * 5. 新建 values-night 目录, 为自定义颜色添加不同模式下的颜色
 * 6. 如果要手动自定义某一页面模式切换下的颜色;
 *        a. 首先在 activity 下设置  android:configChanges="uiMode"
 *        b. 从 configuration 中获取模式, 再根据具体值自定义相应的操作
 * 7. 通过设置 view.isForceDarkAllowed, 可避免 View 受模式切换影响
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<MaterialButton>(R.id.btn)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            btn.isForceDarkAllowed = false
        }

        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                // it's night mode now
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                // it's light mode
            }
        }

        var switchFlag = true
        btn.setOnClickListener {
            if (switchFlag) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchFlag = false
            } else {
                switchFlag = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }
}
