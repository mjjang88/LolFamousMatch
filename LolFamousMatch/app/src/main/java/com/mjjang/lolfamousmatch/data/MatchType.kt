package com.mjjang.lolfamousmatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matchtype")
data class MatchType(
    @PrimaryKey val name: String,
    val category: String,
    val select_count: Int?
) {
}