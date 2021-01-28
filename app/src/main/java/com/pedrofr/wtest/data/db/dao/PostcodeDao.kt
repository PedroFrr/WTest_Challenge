package com.pedrofr.wtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.wtest.data.db.entities.DbPostcode
import kotlinx.coroutines.flow.Flow

@Dao
interface PostcodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(postcodes: List<DbPostcode>)

    @Query("SELECT * FROM postcode ORDER BY postalDesignation, postcodeNumber LIMIT 50")
    fun fetchPostcodes(): Flow<List<DbPostcode>>

    @Query("SELECT * FROM postcode WHERE postalDesignation LIKE '%' || :query ORDER BY postalDesignation, postcodeNumber LIMIT 50") //TODO replace WHERE clause
    fun fetchPostcodesByQuery(query: String): Flow<List<DbPostcode>>
}