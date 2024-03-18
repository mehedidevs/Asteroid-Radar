package com.mehedi.asteroidradar.api


import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface AsteroidService {

    companion object {
        const val API_KEY = "PyxeHF9sEcFlxle718SmfeUmU2pFFUbDa64AObpQ"
    }

    //start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
    @GET("feed")
    suspend fun getAsteroid(
        @Query("start_date") startDate: String = "2024-03-18",
        @Query("end_date") endDate: String = "2024-03-23",
        @Query("api_key") apiKey: String = API_KEY
    ): Response<String>
}


object AsteroidNetwork {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/neo/rest/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val asteroidService: AsteroidService = retrofit.create(AsteroidService::class.java)
}
