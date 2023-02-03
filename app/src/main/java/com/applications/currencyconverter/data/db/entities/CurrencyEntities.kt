package com.applications.currencyconverter.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CurrencyLive")
class CurrencyEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "key")
    val key: String,
    @ColumnInfo(name = "value")
    val value: Double,

    )