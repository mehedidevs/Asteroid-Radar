package com.mehedi.asteroidradar.repository

import com.mehedi.asteroidradar.api.AsteroidNetwork
import com.mehedi.asteroidradar.api.Network
import com.mehedi.asteroidradar.api.parseAsteroidsJsonResult
import com.mehedi.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AsteroidRepository(private val database: AsteroidDatabase) {


    val getImageOfTheDay = database.imageOfTheDayDao.getImageOfTheDay()

    suspend fun refreshImageOfTheDay() {

        withContext(Dispatchers.IO) {
            try {
                val data = Network.iotdService.getImageOFTheDay().body()
                data?.let {
                    database.imageOfTheDayDao.insert(it)
                }

                Timber.d("data : $data")


            } catch (e: Exception) {
                Timber.d("error : ${e.message} ")
            }

        }

    }

    val getAsteroid = database.asteroidDao.getAsteroid()

    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            try {
                val data = AsteroidNetwork.asteroidService.getAsteroid()
                if (data.isSuccessful) {
                    data.body()?.let { asteroidResponse ->
                        val jsonObject = JSONObject(asteroidResponse)
                        val asteroidList = parseAsteroidsJsonResult(jsonObject)

                        database.asteroidDao.insertAll(asteroidList)

                    }
                }
            } catch (e: Exception) {
                Timber.d("error ${e.message}")
            }
        }


    }

    suspend fun deleteAsteroidsBeforeToday() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val today = dateFormat.format(Date()) // Get today's date as a string
        database.asteroidDao.deleteAsteroidsBefore(today)
    }


}
