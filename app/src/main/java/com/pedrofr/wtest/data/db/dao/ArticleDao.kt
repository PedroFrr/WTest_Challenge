package com.pedrofr.wtest.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.pedrofr.wtest.data.db.entities.DbArticle

/**
 * The Data Access Object for the [DbArticle] class.
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<DbArticle>)

    @Query("SELECT * FROM article")
    fun fetchArticlesPaginated(): PagingSource<Int, DbArticle>

    @Query("DELETE FROM article")
    suspend fun clearAll()

    @Query("SELECT * FROM article WHERE id = :articleId")
    suspend fun fetchArticle(articleId: String): DbArticle

    @Query("DELETE FROM article")
    suspend fun clearArticles()

}