package com.huoergai.jni

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        init {
            System.loadLibrary("nativeTools")
        }
    }

    private external fun stringFromJNI(): String

    private external fun getUser(): User
    private external fun printUser(foo: User)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)
        tv.text = stringFromJNI()
        val foo = getUser()
        Log.d("Java log: ", foo.toString())
        printUser(foo)
    }

}
