package com.pedrofr.wtest.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.pedrofr.wtest.data.network.featureclient.ArticleClient
import com.pedrofr.wtest.data.network.featureresponse.CommentResponse
import com.pedrofr.wtest.featureutil.NUMBER_COMMENTS_PAGE
import java.io.IOException

class CommentPagingSource(
    private val articleClient: ArticleClient,
    private val articleId: String
) :
    PagingSource<Int, CommentResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentResponse> {
        try {

            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = articleClient.fetchCommentsPaginated(articleId, nextPageNumber, NUMBER_COMMENTS_PAGE)
            val nextKey = if(response.isEmpty()) {
                null
            }else{
                nextPageNumber + 1
            }

            return LoadResult.Page(
                data = response,
                prevKey = null, //Only paging forward
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommentResponse>): Int? {
        return state.anchorPosition
    }

}