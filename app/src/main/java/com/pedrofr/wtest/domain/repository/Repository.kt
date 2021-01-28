package com.pedrofr.wtest.domain.repository

import com.pedrofr.wtest.data.db.entities.DbPostcode
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchPostcodes(): Flow<List<DbPostcode>>

    fun fetchPostcodesByQuery(query: String): Flow<List<DbPostcode>>
}