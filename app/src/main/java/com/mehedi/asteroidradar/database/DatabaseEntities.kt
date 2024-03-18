package com.mehedi.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mehedi.asteroidradar.domain.Image

@Entity
data class DatabaseImage(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val url: String,
    val hdUrl: String,
    val title: String,
    val serviceVersion: String,
    val mediaType: String,
    val explanation: String,
    val date: String
    )

fun DatabaseImage.asDomainModel(): Image {
    return Image(
        url = this.url,
        title = this.title,
        hdUrl = this.hdUrl,
        serviceVersion = this.serviceVersion,
        mediaType = this.mediaType,
        explanation = this.explanation,
        date = this.date,

        )

}
