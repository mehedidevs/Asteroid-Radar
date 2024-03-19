package com.mehedi.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehedi.asteroidradar.Asteroid
import com.mehedi.asteroidradar.PictureOfDay
import com.mehedi.asteroidradar.today


@Dao
interface ImageDao {
    @Query("select * from picture_of_the_day_title ")
    fun getImageOfTheDay(): LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: PictureOfDay)
}


@Dao
interface AsteroidDao {

    @Query("select * from asteroid_table")
    fun getAsteroid(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroid: List<Asteroid>)

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :today ORDER BY closeApproachDate ASC")
    fun getAsteroidsFromToday(today: String = today()): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate < :today")
    suspend fun deleteAsteroidsBefore(today: String)


}

@Database(entities = [PictureOfDay::class, Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val imageOfTheDayDao: ImageDao
    abstract val asteroidDao: AsteroidDao
}

@Volatile
private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_db"
            ).build()
        }
    }
    return INSTANCE
}
