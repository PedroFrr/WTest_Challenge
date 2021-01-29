package com.pedrofr.wtest.data.network.client

import com.pedrofr.wtest.data.network.response.GetArticlesResponse
import retrofit2.http.GET

interface ArticleService {

    @GET("articles")
    suspend fun fetchArticles(): GetArticlesResponse

}