package com.pedrofr.wtest.data.db.dao

import androidx.paging.PagingSource
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

    @Query("SELECT * FROM postcode ORDER BY postalDesignation, postcodeNumber")
    fun fetchPostcodes(): PagingSource<Int, DbPostcode>

    @Query("SELECT * FROM postcode WHERE postalDesignation LIKE  REPLACE(:query, ' ', '%') ORDER BY postalDesignation, postcodeNumber ") //TODO replace WHERE clause
    fun fetchPostcodesByQuery(query: String): PagingSource<Int, DbPostcode>
}