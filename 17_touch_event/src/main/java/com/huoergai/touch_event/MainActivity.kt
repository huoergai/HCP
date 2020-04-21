package com.huoergai.touch_event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tl -> {
                Snackbar.make(view, "TouchLayout ...", Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                Snackbar.make(view, "TouchView ...", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
