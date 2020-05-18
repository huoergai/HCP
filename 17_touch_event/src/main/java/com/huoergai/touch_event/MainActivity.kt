package com.huoergai.touch_event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * 1.触摸事件的消费顺序是：从上层子 View 往下到根 View
         * 2.一旦在中间被消费(在 OnTouchEvent 的 ACTION_DOWN 事件中返回了 true), 则不再往下传递,
         *   该同一组或同一序列的后续事件也不再向下传递。
         * 3.getAction() 与 getActionMasked(): 后者有对多点触控的支持,除了能获取到事件类型，还能获取手指 index 等信息
         * 4.ViewGroup onInterceptTouchEvent() 负责是否拦截子 View 的事件, 拦截后则其 onTouchEvent() 负责处理该事件
         * 5.如果 ViewGroup 拦截了事件, 需要在拦截时记录下按下的数据,以供后面消费事件使用
         *
         * 触摸事件传递流程简化
         * View.dispatchTouchEvent():
         * public boolean dispatchTouchEvent(){
         *      return onTouchEvent();
         * }
         *
         * ViewGroup.dispatchTouchEvent()
         * public boolean ViewGroup.dispatchTouchEvent(){
         *      boolean result;
         *      if(interceptTouchEvent()){
         *          result = onTouchEvent();
         *      }else{
         *          result = 子 View 的 dispatchTouchEvent()
         *      }
         *      return result
         * }
         *
         */



    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tl -> {
                Snackbar.make(view, "TouchLayout ...", Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                Snackbar.make(view, "TouchView ...", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
