package com.pedrofr.wtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pedrofr.wtest.data.db.entities.DbArticle

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<DbArticle>)

    @Query("SELECT * FROM article ORDER BY publishedAt")
    fun fetchArticles(): List<DbArticle>

    @Query("SELECT * FROM article WHERE id = :articleId")
    suspend fun fetchArticle(articleId: String): DbArticle

}