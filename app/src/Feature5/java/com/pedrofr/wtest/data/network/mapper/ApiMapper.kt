package com.pedrofr.wtest.data.network.mapper

import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.featureresponse.GetArticleResponseItem

interface ApiMapper {

    fun mapApiArticleToDb(apiArticle: GetArticleResponseItem): DbArticle

}