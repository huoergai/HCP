package com.huoergai.testing.section2.chapter4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.huoergai.testing.R
import com.huoergai.testing.SecondaryActivity

/**
 * D&T: 2020-06-27 07:31
 * Des:
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<MaterialButton>(R.id.btn_login).setOnClickListener {
            startActivity(Intent(this, SecondaryActivity::class.java))
        }
    }
}