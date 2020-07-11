package com.huoergai.testing.section2.wishlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.huoergai.testing.R

/**
 * D&T: 2020-07-01 16:06
 * Des:
 */
class WishDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_item_grid_card)



        val mtv: MaterialTextView = findViewById(R.id.rv_mtv_owner)
        val met: MaterialTextView = findViewById(R.id.rv_mtv_wish)



    }
}