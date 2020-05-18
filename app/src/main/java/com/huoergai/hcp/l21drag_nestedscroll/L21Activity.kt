package com.huoergai.hcp.l21drag_nestedscroll

import android.os.Bundle
import com.huoergai.hcp.R
import com.huoergai.hcp.base.BaseActivity

/**
 * drag & nestedScroll
 */
class L21Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_l21_nested_scroll_view)

        /*
         * OnDragListener
         *  1.在 api 11 引入
         *  2.跨进程拖动和传递数据
         *  3.拖起和放下操作,重点在获取数据
         *
         * ViewDragHelper
         *  1.拖拽移动 View 本身,
         *  2.要拖拽 ViewGroup 中的子 View
         *
         */

    }
}