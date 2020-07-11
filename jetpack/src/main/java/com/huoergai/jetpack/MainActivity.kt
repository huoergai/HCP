package com.huoergai.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

/**
 * LiveData & ViewModel
 */
class MainActivity : AppCompatActivity() {

    // private val liveUser = MutableLiveData<Int>(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testLiveData()
        testViewModel()

    }

    private fun testViewModel() {
        val model: MyViewModel = ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(MyViewModel::class.java)
        model.getUsers().observe(this, Observer<List<String>> {
            // update UI
        })
    }

    private fun testLiveData() {
        val liveUser = MutableLiveData<Int>(0)
        liveUser.observe(this, Observer<Int> { t ->
            t?.let {
                println("user changed: $it")
            }
        })
    }

    class MyViewModel : ViewModel() {
        private val users: MutableLiveData<List<String>> by lazy { MutableLiveData<List<String>>().also { loadData() } }

        fun getUsers(): LiveData<List<String>> {
            return users
        }

        private fun loadData() {

        }

    }

}