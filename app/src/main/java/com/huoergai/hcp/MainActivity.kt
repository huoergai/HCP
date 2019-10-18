package com.huoergai.hcp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* val btnLucky = findViewById<Button>(R.id.main_btn_lucky)
         btnLucky.setOnClickListener {
             startActivity(Intent(this@MainActivity, TestActivity::class.java))
         }*/

    }

}
