package com.pedrofr.wtest.data.db.dao

import androidx.room.*
import com.pedrofr.wtest.data.db.entities.DbArticle

@Dao
interface ArticleDao {

    @Transaction
    suspend fun updateArticles(articles: List<DbArticle>) {
        clearArticles()
        insertAllArticles(articles)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<DbArticle>)

    @Query("SELECT * FROM article ORDER BY publishedAt")
    fun fetchArticles(): List<DbArticle>

    @Query("SELECT * FROM article WHERE id = :articleId")
    suspend fun fetchArticle(articleId: String): DbArticle

    @Query("DELETE FROM article")
    suspend fun clearArticles()


}