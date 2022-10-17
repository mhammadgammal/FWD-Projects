package com.example.asteroidradar.repository

import com.example.asteroidradar.DateUtil
import com.example.asteroidradar.repository.api.Network
import com.example.asteroidradar.repository.api.parseAsteroidsJsonResult
import com.example.asteroidradar.repository.database.Asteroid
import com.example.asteroidradar.repository.database.AsteroidRadarDatabase
import com.example.asteroidradar.repository.database.PictureOfDay
import com.example.asteroidradar.toFormattedString
import org.json.JSONObject
import java.util.ArrayList
class AsteroidRepo(private val db: AsteroidRadarDatabase) {
    private var parsedAsteroid = ArrayList<Asteroid>()
    private val currentDate = DateUtil.today()
    suspend fun getRemoteAsteroids(): ArrayList<Asteroid> {
        parsedAsteroid  = parseAsteroidsJsonResult(
            JSONObject(
                Network.retrofitService.getAsteroidsFeed()
            )
        )
        return parsedAsteroid
    }

    suspend fun saveAsteroids() {
        db.asteroidDao.insertAllAsteroids(parsedAsteroid)
    }

    suspend fun getTodayAsteroid(): List<Asteroid>{
        return db.asteroidDao.getTodayAsteroids(currentDate.toFormattedString())
    }

    suspend fun getNextWeekAsteroid(
        startDate: String,
        endDate: String
    ): List<Asteroid>{
        return db.asteroidDao.getWeeksAsteroids(startDate, endDate)
    }

    suspend fun getAllAsteroids(): List<Asteroid>{
        return db.asteroidDao.getAllAsteroids()
    }

    suspend fun refreshPicOfToday(): PictureOfDay {
        return Network.retrofitService.getPicOfTheDay()
    }

    suspend fun deletePreviousAsteroids(){
        db.asteroidDao.deleteAsteroids(currentDate.time)
    }

}