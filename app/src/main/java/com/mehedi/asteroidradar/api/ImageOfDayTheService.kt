package com.mehedi.asteroidradar.api


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageOfDayTheService {
    companion object {
        const val API_KEY = "PyxeHF9sEcFlxle718SmfeUmU2pFFUbDa64AObpQ"
    }

    @GET("planetary/apod")
    suspend fun getImageOFTheDay(@Query("api_key") apiKey: String = API_KEY): Response<NetworkImage>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val iotdService: ImageOfDayTheService = retrofit.create(ImageOfDayTheService::class.java)
}
