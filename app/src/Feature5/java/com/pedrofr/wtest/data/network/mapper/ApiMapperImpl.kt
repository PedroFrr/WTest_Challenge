package com.pedrofr.wtest.data.network.mapper

import com.pedrofr.wtest.data.db.entities.DbArticle
import com.pedrofr.wtest.data.network.featureresponse.GetArticleResponseItem
import javax.inject.Inject

class ApiMapperImpl @Inject constructor() : ApiMapper {
    override fun mapApiArticleToDb(apiArticle: GetArticleResponseItem): DbArticle = DbArticle(
        id = apiArticle.id,
        title = apiArticle.title,
        author = apiArticle.author,
        summary = apiArticle.summary ?: "",
        body = apiArticle.body,
        hero = apiArticle.hero ?: "",
        publishedAt = apiArticle.publishedAt,
        avatar = apiArticle.avatar
    )

}
