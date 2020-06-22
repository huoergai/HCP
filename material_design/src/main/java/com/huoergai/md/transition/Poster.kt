package com.huoergai.md.transition

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * D&T: 2020-06-21 17:48
 * Des:
 */

@Entity
data class Poster(
    @PrimaryKey val id: Long,
    val name: String,
    val release: String,
    val playTime: String,
    val description: String,
    val plot: String,
    val poster: String
)