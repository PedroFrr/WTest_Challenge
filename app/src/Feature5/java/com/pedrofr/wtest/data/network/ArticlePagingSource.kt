package com.pedrofr.wtest.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.pedrofr.wtest.data.db.dao.ArticleDao
import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.featureclient.ArticleClient
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.util.NUMBER_ARTICLES_PAGE
import java.io.IOException

class ArticlePagingSource(
    private val articleClient: ArticleClient,
    private val articleDao: ArticleDao,
    private val apiMapper: ApiMapper
) :
    PagingSource<Int, DbArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DbArticle> {
        try {

            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = articleClient.fetchArticlesPaginated(nextPageNumber, NUMBER_ARTICLES_PAGE)
            val nextKey = if(response.isEmpty()) {
                null
            }else{
                nextPageNumber + 1
            }

            val articles = response.map { apiMapper.mapApiArticleToDb(it) }

            articleDao.insertAllArticles(articles = articles)

            return LoadResult.Page(
                data = articles,
                prevKey = null, //Only paging forward
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DbArticle>): Int? {
        return state.anchorPosition
    }

}