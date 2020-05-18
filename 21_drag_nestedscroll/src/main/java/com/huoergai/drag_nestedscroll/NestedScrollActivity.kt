package com.huoergai.drag_nestedscroll

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.drag_nestedscroll.rv.*

class NestedScrollActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private val items = ArrayList<RvItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)

        loadData()
        initView()
    }

    private fun initView() {
        rv = findViewById(R.id.rv)

        val adapter = RvAdapter()
        adapter.setItems(items)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.setItemClickListener(fun(view: View, pos: Int): Unit {
            Toast.makeText(this, "  pos $pos", Toast.LENGTH_SHORT).show()
            // Snackbar.make(view, "position $pos", Snackbar.LENGTH_SHORT).show()
        })
        rv.adapter = adapter
    }

    private fun loadData() {
        items.add(CardItem("Card", R.drawable.ic_launcher_background))
        val stringItems = ArrayList<RvItem>()
        for (i in 1..20) {
            stringItems.add(StringItem("list $i"))
        }
        items.add(ListItem(stringItems))
        for (i in 1..30) {
            items.add(StringItem("item $i"))
        }
    }
}