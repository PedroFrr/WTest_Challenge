package com.pedrofr.wtest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pedrofr.wtest.core.Failure
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.core.Success
import com.pedrofr.wtest.data.db.dao.PostcodeDao
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.data.network.client.ArticleClient
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    private val articleClient: ArticleClient,
    private val postcodeDao: PostcodeDao,
) : Repository {
    override fun fetchPostcodes(): Flow<PagingData<DbPostcode>> {
        return Pager(
            PagingConfig(
                pageSize = 40,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = { postcodeDao.fetchPostcodes() }
        ).flow
    }


    override fun fetchPostcodesByQuery(query: String): Flow<PagingData<DbPostcode>> {
        return Pager(
            PagingConfig(
                pageSize = 40,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = { postcodeDao.fetchPostcodesByQuery(query) }
        ).flow
    }

    override suspend fun fetchArticles(): Result<List<DbArticle>> {
        val results = articleClient.fetchArticles()

        return if(results is Success){
            val articles = results.data.articles.map {
                apiMapper.mapApiArticleToDb(it)
            }

            Success(articles)

        }else {
            Failure((results as Failure).error) //TODO refactor
        }

    }
    //TODO add calls. save first on db ...

}

