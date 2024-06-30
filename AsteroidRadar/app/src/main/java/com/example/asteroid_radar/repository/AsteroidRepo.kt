package com.example.asteroid_radar.repository

import com.example.asteroid_radar.DateUtil
import com.example.asteroid_radar.repository.api.Network
import com.example.asteroid_radar.repository.api.parseAsteroidsJsonResult
import com.example.asteroid_radar.repository.database.Asteroid
import com.example.asteroid_radar.repository.database.AsteroidRadarDatabase
import com.example.asteroid_radar.repository.database.PictureOfDay
import com.example.asteroid_radar.toFormattedString
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