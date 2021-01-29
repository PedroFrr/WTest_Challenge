package com.pedrofr.wtest.data.network.client

import com.pedrofr.wtest.core.Failure
import com.pedrofr.wtest.core.Success
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

}

