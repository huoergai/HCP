package com.huoergai.hcp.preference

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huoergai.hcp.R

class SetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        supportFragmentManager.beginTransaction().replace(R.id.set_fl, SetFragment()).commit()
    }


}