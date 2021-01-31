package com.pedrofr.wtest.data.network.featureclient

import com.pedrofr.wtest.core.Failure
import com.pedrofr.wtest.core.Success
import com.pedrofr.wtest.data.network.mapper.ApiMapper
import javax.inject.Inject

class ArticleClient @Inject constructor(
    private val articleService: ArticleService
) {

    suspend fun fetchArticles() =
        try {
            val response = articleService.fetchArticles()
            Success(response)
        } catch (error: Throwable) {
            Failure(error)
        }

    suspend fun fetchArticlesPaginated(pageNumber: Int, loadSize: Int) = articleService.fetchArticles(page = pageNumber, loadSize = loadSize)

    suspend fun fetchCommentsPaginated(articleId: String, pageNumber: Int, loadSize: Int) = articleService.fetchComments(articleId = articleId, page = pageNumber, loadSize = loadSize)

}

