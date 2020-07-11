package com.huoergai.testing

import android.app.Application
import androidx.room.Room
import com.huoergai.testing.section2.wishlist.WishlistDatabase

/**
 * D&T: 2020-07-04 21:24
 * Des:
 */

class TestingApp : Application() {
    private lateinit var testDB: WishlistDatabase
    override fun onCreate() {
        super.onCreate()
        setupDB()
    }

    private fun setupDB() {
        testDB = Room.databaseBuilder(this, WishlistDatabase::class.java, "wishes.db")
            .build()
    }
}