package com.huoergai.md

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huoergai.md.fragment.IonBackPressed

/**
 * D&T: 2020-06-15 05:15 PM
 * Des:
 */
class ShellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shell)

        val fragmentData: FeatureDemo = intent.getSerializableExtra("fragment_data") as FeatureDemo

        fragmentData.fragment?.let {
            val b = Bundle()
            b.putString("title", getString(fragmentData.title))
            it.arguments = b
            supportFragmentManager.beginTransaction().replace(R.id.fl_container, it)
                .commit()
        }
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.fl_container)
        if (f is IonBackPressed) {
            val ret = f.onBackPressed()
            if (ret) {
                return
            }
        }
        super.onBackPressed()
    }
}