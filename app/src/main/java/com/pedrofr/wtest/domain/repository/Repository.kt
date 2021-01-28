package com.pedrofr.wtest.domain.repository

import androidx.paging.PagingData
import com.pedrofr.wtest.data.db.entities.DbPostcode
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchPostcodes(): Flow<PagingData<DbPostcode>>

    fun fetchPostcodesByQuery(query: String): Flow<PagingData<DbPostcode>>
}