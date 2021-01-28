package com.pedrofr.wtest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "postcode")
data class DbPostcode(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val postalDesignation: String,
    val postcodeNumber: String,
    val postcodeExtension: String
)
