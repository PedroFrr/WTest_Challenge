package com.pedrofr.wtest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pedrofr.wtest.core.Failure
import com.pedrofr.wtest.core.Result
import com.pedrofr.wtest.core.Success
import com.pedrofr.wtest.data.db.dao.ArticleDao
import com.pedrofr.wtest.data.db.dao.PostcodeDao
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.db.entities.DbPostcode
import com.pedrofr.wtest.data.network.ArticlePagingSource
import com.pedrofr.wtest.data.network.CommentPagingSource
import com.pedrofr.wtest.data.network.featureclient.ArticleClient
import com.pedrofr.wtest.data.network.featureresponse.CommentResponse
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.domain.repository.Repository
import com.pedrofr.wtest.featureutil.NUMBER_COMMENTS_PAGE
import com.pedrofr.wtest.util.NUMBER_ARTICLES_PAGE
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
    private val articleDao: ArticleDao
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

    override suspend fun fetchArticle(articleId: String): DbArticle =
        articleDao.fetchArticle(articleId)

    override fun fetchArticlesPaginated(): Flow<PagingData<DbArticle>> {

        return Pager(
            config = PagingConfig(
                pageSize = NUMBER_ARTICLES_PAGE,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),

            pagingSourceFactory = { ArticlePagingSource(articleClient, articleDao, apiMapper) }
        ).flow
    }

    override fun fetchCommentsPaginated(articleId: String): Flow<PagingData<CommentResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NUMBER_COMMENTS_PAGE,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),

            pagingSourceFactory = { CommentPagingSource(articleClient, articleId) }
        ).flow
    }

    override suspend fun fetchData(): DbPostcode = postcodeDao.fetchData()

    override suspend fun fetchValidPostcode(postcodeNumber: String, postcodeExtension: String): DbPostcode =
        postcodeDao.fetchValidPostcode(postcodeNumber, postcodeExtension)

}


