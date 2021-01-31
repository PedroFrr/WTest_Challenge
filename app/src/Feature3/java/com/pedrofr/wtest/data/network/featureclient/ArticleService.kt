package com.pedrofr.wtest.data.network.featureclient

import com.pedrofr.wtest.data.network.featureresponse.GetArticlesResponse
import com.pedrofr.wtest.util.NUMBER_ARTICLES_PAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {

    @GET("articles")
    suspend fun fetchArticles(
        @Query("page") page: Int? = null, //TODO see if this makes sense
        @Query("limit") loadSize: Int = NUMBER_ARTICLES_PAGE, //default number of items to retrieve per page
    ): GetArticlesResponse

}