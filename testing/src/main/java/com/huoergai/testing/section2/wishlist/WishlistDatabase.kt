package com.huoergai.testing.section2.wishlist

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * D&T: 2020-07-04 15:18
 * Des:
 */

@Database(entities = arrayOf(Wish::class), version = 1)
abstract class WishlistDatabase : RoomDatabase() {

    abstract fun wishlistDao(): WishlistDao

}