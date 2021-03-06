package com.huoergai.testing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huoergai.testing.section2.wishlist.ui.WishListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick.setOnClickListener {
            Toast.makeText(this, "hello..", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, SecondaryActivity::class.java))
            // startActivity(Intent(this, ProductActivity::class.java))
            // startActivity(Intent(this, CocktailGameActivity::class.java))
            startActivity(Intent(this, WishListActivity::class.java))
        }
    }

    fun sayHello() {
        Log.d("MainActivity", "hello there...")
        println("MainActivity hello there...")
    }

}