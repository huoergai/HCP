package com.huoergai.md

import android.app.Application
import androidx.room.Room
import com.huoergai.md.db.AppDB

/**
 * D&T: 2020-06-21 18:01
 * Des:
 */
class MDApp : Application() {

    companion object {
        private lateinit var appDB: AppDB
        fun getDB(): AppDB {
            return appDB
        }
    }

    override fun onCreate() {
        super.onCreate()
        appDB = Room.databaseBuilder(this, AppDB::class.java, "MD.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


}