package com.pedrofr.wtest.data.network.featureresponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentResponse(
    @Json(name = "id") val id: String,
    @Json(name = "articleId") val articleId: String,
    @Json(name = "published-at") val publishedAt: String,
    @Json(name = "name") val name: String,
    @Json(name = "avatar") val avatar: String,
    @Json(name = "body") val body: String
)