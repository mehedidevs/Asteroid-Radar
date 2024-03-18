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

@Dao
interface ImageDao {

    @Query("select * from databaseimage")
    fun getImageOfTheDay(): LiveData<DatabaseImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: DatabaseImage)
}
@Dao
interface AsteroidDao {

    @Query("select * from asteroid_table")
    fun getAsteroid(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( asteroid: List<Asteroid>)

}

@Database(entities = [DatabaseImage::class, Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val imageOfTheDayDao: ImageDao
    abstract val asteroidDao: AsteroidDao
}

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
