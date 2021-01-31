package com.pedrofr.wtest.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.wtest.data.db.entities.DbPostcode

/**
 * The Data Access Object for the [DbPostcode] class.
 */
@Dao
interface PostcodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(postcodes: List<DbPostcode>)

    @Query("SELECT * FROM postcode ORDER BY postalDesignation, postcodeNumber")
    fun fetchPostcodes(): PagingSource<Int, DbPostcode>

    //Full text search on Postcode
    //Allows to search in reverse order, full or partial words and with accents
    @Query(
        """
        SELECT *
        FROM postcode 
        JOIN postcodes_fts ON postcode.id = postcodes_fts.id
        WHERE postcodes_fts MATCH '*' || :query || '*'
        """
    )
    fun fetchPostcodesByQuery(query: String): PagingSource<Int, DbPostcode>

    @Query("INSERT INTO postcodes_fts(postcodes_fts) VALUES ('rebuild')")
    fun rebuildDbPostcodesFTS()

    //Fetches one single Article in order to validate if there's data on the database
    @Query("SELECT * FROM postcode LIMIT 1")
    suspend fun fetchData(): DbPostcode

    //Query used to check if Postcode is valid.
    @Query(
        """
        SELECT * 
        FROM postcode 
        WHERE postcodeNumber = :postcodeNumber and postcodeExtension = :postcodeExtension
        LIMIT 1"""
    )
    suspend fun fetchValidPostcode(
        postcodeNumber: String,
        postcodeExtension: String
    ): DbPostcode

}