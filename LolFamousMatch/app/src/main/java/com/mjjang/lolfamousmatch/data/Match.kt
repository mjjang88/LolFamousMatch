package com.mjjang.lolfamousmatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matchs")
data class Match(
    @PrimaryKey val id: String,
    val name: String,
    val sub_name : String?,
    val year: Int?,
    val tag: String?,
    val link_full: String?,
    val link_highlight: String?,
    val recommend: Int?
) {
    override fun toString(): String {
        return name
    }

    fun getYearToString(): String {
        return "${year}년 경기"
    }
}