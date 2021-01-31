package com.pedrofr.wtest.domain.repository

import androidx.paging.PagingData
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.db.entities.DbPostcode
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchPostcodes(): Flow<PagingData<DbPostcode>>

    fun fetchPostcodesByQuery(query: String): Flow<PagingData<DbPostcode>>

    suspend fun fetchArticles(): Result<List<DbArticle>>

    suspend fun fetchArticle(articleId: String): DbArticle

    fun fetchArticlesPaginated(): Flow<PagingData<DbArticle>>

    suspend fun fetchData(): DbPostcode?

    suspend fun fetchValidPostcode(postcodeNumber: String, postcodeExtension: String): DbPostcode?

}