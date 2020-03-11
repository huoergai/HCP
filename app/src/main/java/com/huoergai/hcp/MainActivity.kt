package com.huoergai.hcp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.hcp.base.RvAdapter
import com.huoergai.hcp.l10text_transfermation.L10Activity
import com.huoergai.hcp.l11animation.L11Activity
import com.huoergai.hcp.l12bitmap_drawable.L12Activity
import com.huoergai.hcp.l13constraintlayout.L13Activity
import com.huoergai.hcp.l14motionlayout.L14Activity
import com.huoergai.hcp.l21drag_nestedscroll.L21Activity
import com.huoergai.hcp.l29gradle.L29Activity
import com.huoergai.hcp.l36arch.L36Activity
import com.huoergai.hcp.l37plugin.L37Activity
import com.huoergai.hcp.l38hotfix.L38Activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv: RecyclerView = findViewById(R.id.main_rv)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        val datas = listOf(
            L10Activity::class.java,
            L11Activity::class.java,
            L12Activity::class.java,
            L13Activity::class.java,
            L14Activity::class.java,
            L21Activity::class.java,
            L29Activity::class.java,
            L36Activity::class.java,
            L37Activity::class.java,
            L38Activity::class.java
        )
        rv.adapter = RvAdapter(datas)
    }

}
