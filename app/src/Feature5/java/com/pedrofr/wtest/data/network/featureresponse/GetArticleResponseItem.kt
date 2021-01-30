package com.pedrofr.wtest.data.network.featureresponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetArticleResponseItem(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "body") val body: String,
    @Json(name = "hero") val hero: String?,
    @Json(name = "published-at") val publishedAt: String,
    @Json(name = "summary") val summary: String?,
    @Json(name = "avatar") val avatar: String
)