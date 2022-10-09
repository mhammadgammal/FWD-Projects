package com.example.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.DateUtil
import com.example.asteroidradar.repository.AsteroidRepo
import com.example.asteroidradar.repository.api.Network
import com.example.asteroidradar.repository.api.parseAsteroidsJsonResult
import com.example.asteroidradar.repository.database.Asteroid
import com.example.asteroidradar.repository.database.AsteroidRadarDatabase.Companion.getInstance
import com.example.asteroidradar.repository.database.PictureOfDay
import com.example.asteroidradar.toFormattedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(
    application: Application
) : ViewModel() {
    private val _weekAsteroids = MutableLiveData<List<Asteroid>>()
    val weekAsteroid: LiveData<List<Asteroid>>
        get() = _weekAsteroids
    private val _allAsteroids = MutableLiveData<List<Asteroid>>()
    val allAsteroid: LiveData<List<Asteroid>>
        get() = _allAsteroids
    private val _navToDetailFragment = MutableLiveData<Asteroid>()
    val navToDetailFragment: LiveData<Asteroid>
        get() = _navToDetailFragment
    private val db = getInstance(application)
    private val repo = AsteroidRepo(db)
    private val _pic = MutableLiveData<PictureOfDay>()
    val pic: LiveData<PictureOfDay>
        get() = _pic
    private val picType = MutableLiveData<String>()
    private val _todayAsteroids = MutableLiveData<List<Asteroid>>()
    val todayAsteroids: LiveData<List<Asteroid>>
        get() = _todayAsteroids
    private val currentDate = DateUtil.today()

    init {
        refreshPic()
        refreshAsteroids()
        todayAsteroids()
        weekAsteroid()
        allAsteroids()
    }

    private val tag = "MainViewModel"

    private fun refreshPic() {
        Log.i(tag, "refresh: In refresh method")
        var remotePic: PictureOfDay
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                remotePic = repo.refreshPicOfToday()
            }
            withContext(Dispatchers.Main) {
                _pic.value = remotePic
                picType.value = remotePic.media_type
            }
        }
    }

    private fun refreshAsteroids() {
        Log.i(tag, "refreshAsteroids: In refreshAsteroids Method")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.getRemoteAsteroids()
                repo.saveAsteroids()
            }
        }
    }


    private fun todayAsteroids() {
        Log.i(tag, "getAsteroids: $currentDate")
        viewModelScope.launch {

            var asteroids: List<Asteroid>

            withContext(Dispatchers.IO) {
                asteroids = repo.getTodayAsteroid()
            }

            _todayAsteroids.value = asteroids

            Log.i(tag, "getAsteroids: $allAsteroid")
        }
    }

    private fun weekAsteroid() {
        var weekAsteroid: List<Asteroid>
        val nextWeek = DateUtil.getNextWeek(
            7, DateUtil.today()
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                weekAsteroid = repo.getNextWeekAsteroid(
                    currentDate.toFormattedString(), nextWeek.toFormattedString()
                )
            }
            _weekAsteroids.value = weekAsteroid
        }
    }

    private fun allAsteroids() {
        var allAsteroids: List<Asteroid>
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allAsteroids = repo.getAllAsteroids()
            }
            _allAsteroids.value = allAsteroids
        }
    }

    fun navToDetailFragment(asteroid: Asteroid) {
        _navToDetailFragment.value = asteroid
    }
}