package com.huoergai.testing.section2.wishlist

import androidx.room.Dao
import androidx.room.Insert

/**
 * D&T: 2020-07-04 15:17
 * Des:
 */
@Dao
interface WishlistDao {

    @Insert
    fun save(vararg wishs: Wish)

}