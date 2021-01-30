package com.pedrofr.wtest.data.network.featureresponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetArticlesResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "items") val articles: List<ArticleResponse>
)