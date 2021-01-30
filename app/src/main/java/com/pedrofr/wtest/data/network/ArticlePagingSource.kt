package com.pedrofr.wtest.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.pedrofr.wtest.data.db.dao.ArticleDao
import com.pedrofr.wtest.data.network.client.ArticleClient
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import com.pedrofr.wtest.data.network.response.ArticleResponse
import com.pedrofr.wtest.util.NUMBER_ARTICLES_PAGE
import java.io.IOException

//TODO add dependecy injection?
class ArticlePagingSource(
    private val articleClient: ArticleClient,
    private val articleDao: ArticleDao,
    private val apiMapper: ApiMapper
) :
    PagingSource<Int, ArticleResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        try {

            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = articleClient.fetchArticlesPaginated(nextPageNumber, NUMBER_ARTICLES_PAGE)
            val nextKey = if(response.count < NUMBER_ARTICLES_PAGE) {
                null
            }else{
                nextPageNumber + 1
            }

            val articles = response.articles

            //TODO right now this is a hack. I should be using RemoteMediator but I'm not figuring out a bug
            articleDao.insertAllArticles(articles = articles.map { apiMapper.mapApiArticleToDb(it) })

            return LoadResult.Page(
                data = articles,
                prevKey = null, //Only paging forward //TODO revise
                nextKey = nextKey //TODO revise
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition
    }

}