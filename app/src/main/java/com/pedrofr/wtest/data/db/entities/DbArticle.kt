package com.pedrofr.wtest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "article")
data class DbArticle(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val summary: String,
    val body: String,
    val hero: String,
    val publishedAt: String,
    val avatar: String = "" //TODO see if I should isolate this into the Feature5 flavour
    )