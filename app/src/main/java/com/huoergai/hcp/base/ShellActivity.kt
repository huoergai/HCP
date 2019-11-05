package com.huoergai.hcp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.huoergai.hcp.R

class ShellActivity : BaseActivity() {
    companion object {
        private lateinit var content: Fragment
        fun start(context: Context, fragment: Fragment) {
            content = fragment
            context.startActivity(Intent(context, ShellActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shell)

        supportFragmentManager.beginTransaction()
            .replace(R.id.shell_container, content)
            .addToBackStack(null)
            .commit()
    }
}