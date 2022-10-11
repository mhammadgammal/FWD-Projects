package com.example.asteroidradar.repository.api

import com.example.asteroidradar.repository.database.PictureOfDay
import retrofit2.http.GET

interface AsteroidApiInterface {
    @GET("neo/rest/v1/feed?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    suspend fun getAsteroidsFeed(): String

    @GET("planetary/apod?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    suspend fun getPicOfTheDay(): PictureOfDay
}