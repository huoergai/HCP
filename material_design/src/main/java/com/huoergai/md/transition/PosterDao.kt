package com.huoergai.md.transition

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * D&T: 2020-06-21 18:05
 * Des:
 */
@Dao
interface PosterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosters(posters: List<Poster>)

    @Query("SELECT * FROM Poster WHERE id = :id_")
    fun getPoster(id_: Long): Poster

    @Query("SELECT * FROM Poster")
    fun getPosters(): List<Poster>
}