package com.pedrofr.wtest.data.network.mapper

import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.featureresponse.GetArticleResponseItem

//TODO change name of classes representing data
interface ApiMapper {

    //TODO add mapper from API to Model
    fun mapApiArticleToDb(apiArticle: GetArticleResponseItem): DbArticle

}