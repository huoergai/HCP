package com.huoergai.hcp.l21drag_nestedscroll.drag

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.huoergai.hcp.R
import kotlinx.android.synthetic.main.activity_l21_collect.view.*

/**
 * D&T: 2020-05-17 02:50 PM
 * Des:
 */
class DragToCollectLayout : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private lateinit var collector: LinearLayout

    private val dragListener = CollectDragListener()

    private val longClickListener = MyLongClickListener()

    override fun onFinishInflate() {
        super.onFinishInflate()

        collector = findViewById(R.id.ll_collector)
        val ivCat = findViewById<ImageView>(R.id.iv_cat)
        val ivRobot = findViewById<ImageView>(R.id.iv_robot)

        ivCat.setOnLongClickListener(longClickListener)
        ivRobot.setOnLongClickListener(longClickListener)
        collector.setOnDragListener(dragListener)
    }

    inner class MyLongClickListener : OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            v?.let {
                val clipData = ClipData.newPlainText("name", it.contentDescription)
                return ViewCompat.startDragAndDrop(it, clipData, DragShadowBuilder(it), it, 0)
            }
            return false
        }
    }

    inner class CollectDragListener : OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            event?.let {
                if (it.action == DragEvent.ACTION_DROP) {
                    if (v is LinearLayout) {
                        val tv = TextView(context)
                        tv.textSize = 16f
                        tv.text = event.clipData.getItemAt(0).text
                        v.addView(tv)
                    }
                }
            }
            return true
        }

    }
}