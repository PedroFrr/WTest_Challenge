package com.pedrofr.wtest.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bumptech.glide.load.HttpException
import com.pedrofr.wtest.data.db.dao.ArticleDao
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.client.ArticleClient
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.util.NUMBER_ARTICLES_PAGE
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleClient: ArticleClient,
    private val articleDao: ArticleDao,
    private val apiMapper: ApiMapper,
    private val pageNumber: Int
) : RemoteMediator<Int, DbArticle>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, DbArticle>): MediatorResult {
        return try {
//            val response = articleClient.fetchArticlesPaginated(pageNumber, NUMBER_ARTICLES_PAGE) //TODO change
//
//            val articles = response.articles.map { apiMapper.mapApiArticleToDb(it) }
//
//            articleDao.insertAllArticles(articles = articles)
//
//            MediatorResult.Success(endOfPaginationReached = response.count < NUMBER_ARTICLES_PAGE)

            //TODO

            MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
}

