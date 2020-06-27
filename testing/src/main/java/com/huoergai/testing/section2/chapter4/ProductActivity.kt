package com.huoergai.testing.section2.chapter4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.huoergai.testing.R

/**
 * D&T: 2020-06-27 07:14
 * Des:
 */
class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val bab = findViewById<BottomAppBar>(R.id.product_bab)
        bab.setOnMenuItemClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            true
        }
    }


}