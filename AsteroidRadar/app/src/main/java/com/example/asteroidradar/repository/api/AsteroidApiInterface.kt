package com.example.asteroidradar.repository.api

import com.example.asteroidradar.repository.database.PictureOfDay
import retrofit2.http.GET

interface AsteroidApiInterface {
    @GET("neo/rest/v1/feed?api_key=OFfN46yZMgjHheMBurLlvhqt7hSplUOQHtxVRfS6")
    suspend fun getAsteroidsFeed(): String

    @GET("planetary/apod?api_key=OFfN46yZMgjHheMBurLlvhqt7hSplUOQHtxVRfS6")
    suspend fun getPicOfTheDay(): PictureOfDay
}