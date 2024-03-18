package com.mehedi.asteroidradar.api

import androidx.annotation.Keep
import com.mehedi.asteroidradar.database.DatabaseImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Keep
data class NetworkImage(
    @Json(name = "date")
    val date: String,
    @Json(name = "explanation")
    val explanation: String,
    @Json(name = "hdurl")
    val hdurl: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String
)

fun NetworkImage.asDatabaseImage(): DatabaseImage {

    return DatabaseImage(
        url = this.url,
        hdUrl = this.hdurl,
        title = this.title,
        serviceVersion = this.serviceVersion,
        mediaType = this.mediaType,
        explanation = this.explanation,
        date = this.date,

    )
}


/*
fun NetworkVideoContainer.asDomainModel(): List<Video> {
    return videos.map {
        Video(
                title = it.title,
                description = it.description,
                url = it.url,
                updated = it.updated,
                thumbnail = it.thumbnail)
    }
}
 */