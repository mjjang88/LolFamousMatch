package com.mjjang.lolfamousmatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matchs")
data class Match(
    @PrimaryKey val id: Int,
    val name: String,
    val year: Int,
    val tag: String,
    val link: String,
    val recommend: Int
) {
    override fun toString(): String {
        return name
    }
}