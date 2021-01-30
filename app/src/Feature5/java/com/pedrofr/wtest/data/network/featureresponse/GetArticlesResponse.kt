package com.pedrofr.wtest.data.network.featureresponse

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetArticlesResponse(val items: List<GetArticleResponseItem>)