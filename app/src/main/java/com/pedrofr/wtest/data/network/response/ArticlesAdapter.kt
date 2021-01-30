package com.pedrofr.wtest.data.network.response

import com.pedrofr.wtest.data.network.featureresponse.GetArticleResponseItem
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ArticlesAdapter {

    @ToJson
    fun arrayListToJson(list: ArrayList<GetArticleResponseItem>) : List<GetArticleResponseItem> = list

    @FromJson
    fun arrayListFromJson(list: List<GetArticleResponseItem>) : ArrayList<GetArticleResponseItem> = ArrayList(list)

}