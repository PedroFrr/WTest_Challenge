package com.pedrofr.wtest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "poste_code")
data class DbPostcode(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val districtCode: String,
    val countyCode: String,
    val localCode: String,
    val name: String
    //TODO add more
)
