package com.huoergai.testing.section2.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * D&T: 2020-07-01 15:47
 * Des:
 */
@Entity
data class Wish(
    @PrimaryKey
    val uid: Int = 0,
    var owner: String,
    var wishList: List<String>,
    var count: Int = 0
)