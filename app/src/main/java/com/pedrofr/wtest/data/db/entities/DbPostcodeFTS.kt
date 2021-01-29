package com.pedrofr.wtest.data.db.entities

import androidx.room.Entity
import androidx.room.Fts4


@Entity(tableName = "postcodes_fts")
@Fts4(contentEntity = DbPostcode::class)
data class DbPostcodeFTS(
    val id: String,
    val postalDesignation: String,
    val postcodeNumber: String,
    val postcodeExtension: String
)