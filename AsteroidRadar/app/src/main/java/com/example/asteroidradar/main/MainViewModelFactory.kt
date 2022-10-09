package com.example.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.repository.database.AsteroidRadarDatabase

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(val application: Application): ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application) as T
        }
        throw java.lang.IllegalArgumentException("Invalid View Model")
    }
}