package com.mehedi.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "picture_of_the_day_title")
data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    @PrimaryKey
    val title: String,
    val url: String
)