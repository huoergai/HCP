package com.huoergai.md

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huoergai.md.extension.applyExitMaterialTransform

/**
 * Material Design Practice
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val demos = listOf(
            FeatureDemo(R.string.cat_bottomappbar_title, R.drawable.ic_bottomappbar),
            FeatureDemo(R.string.cat_bottom_nav_title, R.drawable.ic_bottomnavigation),
            FeatureDemo(R.string.cat_bottomsheet_title, R.drawable.ic_bottomsheet),
            FeatureDemo(R.string.cat_toc_buttons, R.drawable.ic_button)
        )

        val rv = findViewById<RecyclerView>(R.id.rv)
        val gm = GridLayoutManager(this, 2)
        rv.layoutManager = gm
        val rvAdapter = RvAdapter(demos)
        rv.addItemDecoration(
            GridDividerDecoration(
                resources.getDimensionPixelSize(R.dimen.cat_toc_grid_divider_size),
                resources.getColor(R.color.cat_toc_grid_divider_color),
                2
            )
        )
        rvAdapter.onItemClick = { itemView, data ->
            val intent = Intent(this, ShellActivity::class.java)
            intent.putExtra("fragment_data", data)
            val option =
                ActivityOptions.makeSceneTransitionAnimation(this, itemView, getString(data.title))

            // startActivity(intent)
            startActivity(intent, option.toBundle())
        }
        rv.adapter = rvAdapter
    }
}
