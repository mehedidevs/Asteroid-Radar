package com.mehedi.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mehedi.asteroidradar.database.getDatabase
import com.mehedi.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)


    init {
        viewModelScope.launch {
            asteroidRepository.apply {
                refreshImageOfTheDay()
                refreshAsteroid()
            }

        }
    }

    fun imageOfTheDay() = asteroidRepository.getImageOfTheDay

    fun getSteroid() = asteroidRepository.getAsteroid


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}