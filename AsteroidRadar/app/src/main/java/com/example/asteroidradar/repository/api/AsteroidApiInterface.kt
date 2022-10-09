package com.example.asteroidradar.repository.api

import com.example.asteroidradar.repository.database.Asteroid
import com.example.asteroidradar.repository.database.PictureOfDay
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApiInterface {
    @GET("neo/rest/v1/feed?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    suspend fun getAsteroidsFeed(): String

    @GET("planetary/apod?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    suspend fun getPicOfTheDay(): PictureOfDay
}