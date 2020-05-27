package com.huoergai.hcp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.hcp.base.CubeActivity
import com.huoergai.hcp.base.RvAdapter
import com.huoergai.hcp.l10text_transfermation.L10Activity
import com.huoergai.hcp.l11animation.L11Activity
import com.huoergai.hcp.l12bitmap_drawable.L12Activity
import com.huoergai.hcp.l13constraintlayout.L13Activity
import com.huoergai.hcp.l21drag_nestedscroll.L21Activity
import com.huoergai.hcp.lesson36.L36Activity
import com.huoergai.hcp.lesson37.L37Activity
import com.huoergai.hcp.lesson38.L38Activity
import com.huoergai.hcp.lesson40.L40Activity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        // coroutineTest()
    }

    private fun initView() {
        val rv: RecyclerView = findViewById(R.id.main_rv)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        val datas = listOf(
            CubeActivity::class.java,
            L10Activity::class.java,
            L11Activity::class.java,
            L12Activity::class.java,
            L13Activity::class.java,
            L21Activity::class.java,
            L36Activity::class.java,
            L37Activity::class.java,
            L38Activity::class.java,
            L40Activity::class.java
        )
        rv.adapter = RvAdapter(datas)
    }

    private fun coroutineTest() {
        GlobalScope.launch(Dispatchers.Main) {
            ioFun1()
            uiFun1()
            ioFun2()
            uiFun2()
        }

    }

    private suspend fun ioFun1() {
        withContext(Dispatchers.IO) {
            delay(200)
            println("ioFun1:${Thread.currentThread().name} ")
        }
    }

    private fun uiFun1() {
        println("uiFun1:${Thread.currentThread().name} ")
    }

    private suspend fun ioFun2() {
        withContext(Dispatchers.IO) {
            delay(200)
            println("ioFun2:${Thread.currentThread().name} ")
        }
    }

    private fun uiFun2() {
        println("uiFun2:${Thread.currentThread().name} ")
    }
}
