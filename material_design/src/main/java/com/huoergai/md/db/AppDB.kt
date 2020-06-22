package com.huoergai.md.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huoergai.md.transition.Poster
import com.huoergai.md.transition.PosterDao

/**
 * D&T: 2020-06-21 18:02
 * Des:
 */
@Database(entities = [Poster::class], version = 1, exportSchema = true)
abstract class AppDB : RoomDatabase() {

    abstract fun posterDao(): PosterDao



}