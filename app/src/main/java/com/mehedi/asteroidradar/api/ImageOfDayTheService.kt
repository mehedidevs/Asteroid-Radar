package com.mehedi.asteroidradar.api


import com.mehedi.asteroidradar.Constants.API_KEY
import com.mehedi.asteroidradar.Constants.BASE_URL
import com.mehedi.asteroidradar.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageOfDayTheService {
    @GET("planetary/apod")
    suspend fun getImageOFTheDay(@Query("api_key") apiKey: String = API_KEY): Response<PictureOfDay>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val iotdService: ImageOfDayTheService = retrofit.create(ImageOfDayTheService::class.java)
}
