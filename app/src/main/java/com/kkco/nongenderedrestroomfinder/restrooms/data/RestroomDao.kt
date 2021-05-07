package com.kkco.nongenderedrestroomfinder.restrooms.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Restroom class.
 */
@Dao
interface RestroomDao {

    @Query("SELECT * FROM restrooms ORDER BY distance ASC")
    fun getRestrooms(): LiveData<List<Restroom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restrooms: List<Restroom>)
}