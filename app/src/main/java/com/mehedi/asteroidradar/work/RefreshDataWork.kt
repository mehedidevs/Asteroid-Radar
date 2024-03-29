package com.mehedi.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mehedi.asteroidradar.database.getDatabase
import com.mehedi.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshImageOfTheDay()
            repository.refreshAsteroid()
            repository.deleteAsteroidsBeforeToday()

            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
