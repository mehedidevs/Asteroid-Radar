package com.mehedi.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.mehedi.asteroidradar.Asteroid
import com.mehedi.asteroidradar.AsteroidFilter
import com.mehedi.asteroidradar.api.AsteroidNetwork
import com.mehedi.asteroidradar.api.Network
import com.mehedi.asteroidradar.api.parseAsteroidsJsonResult
import com.mehedi.asteroidradar.database.AsteroidDatabase
import com.mehedi.asteroidradar.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(private val database: AsteroidDatabase) {


    val getImageOfTheDay = database.imageOfTheDayDao.getImageOfTheDay()

    suspend fun refreshImageOfTheDay() {

        withContext(Dispatchers.IO) {
            try {
                val data = Network.iotdService.getImageOFTheDay().body()
                data?.let {
                    database.imageOfTheDayDao.insert(it)
                }
            } catch (e: Exception) {
                Timber.d("${e.message}")
            }

        }

    }

    fun getAsteroid(filter: AsteroidFilter): LiveData<List<Asteroid>> {

      return  when (filter) {
            AsteroidFilter.SHOW_SAVED ->
                database.asteroidDao.getAllSavedAsteroid()

            AsteroidFilter.SHOW_WEEK ->
                database.asteroidDao.getAsteroidsForNext7Days()

            AsteroidFilter.SHOW_TODAY ->
                database.asteroidDao.getAsteroidsFromToday()
        }




    }

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
                Timber.d("${e.message}")
            }
        }


    }

    suspend fun deleteAsteroidsBeforeToday() {
        database.asteroidDao.deleteAsteroidsBefore(today())
    }


}
