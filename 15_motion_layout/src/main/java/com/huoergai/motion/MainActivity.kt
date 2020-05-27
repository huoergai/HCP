package com.huoergai.motion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * MotionLayout 解析
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * View 动画 API 1
         * 属性动画 API 11
         * 1.不会改变布局参数
         *
         * 过渡动画 API 18
         * 1.两个场景之间的过渡, 开始场景 --> 结束场景; 创建动画, 播放动画
         * 2.会改变布局参数
         * 3.不支持过渡反馈,不能相应事件
         */

        // TransitionManager.go(Scene.getSceneForLayout())

    }

    fun onClick(view: View) {
        val destination = "com.huoergai.motion.${(view as Button).text}Activity"
        Log.d("Motion", destination)
        startActivity(Intent(this, Class.forName(destination)))
    }

}
