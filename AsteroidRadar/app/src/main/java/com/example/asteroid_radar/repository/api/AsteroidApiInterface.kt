package com.example.asteroid_radar.repository.api

import com.example.asteroid_radar.repository.database.PictureOfDay
import retrofit2.http.GET

interface AsteroidApiInterface {
    @GET("neo/rest/v1/feed?api_key=OFfN46yZMgjHheMBurLlvhqt7hSplUOQHtxVRfS6")
    suspend fun getAsteroidsFeed(): String

    @GET("planetary/apod?api_key=OFfN46yZMgjHheMBurLlvhqt7hSplUOQHtxVRfS6")
    suspend fun getPicOfTheDay(): PictureOfDay
}