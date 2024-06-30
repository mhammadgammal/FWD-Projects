package com.example.asteroid_radar.repository.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL = "https://api.nasa.gov/"
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()!!
object Network {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: AsteroidApiInterface by lazy {
        retrofit.create(AsteroidApiInterface::class.java)
    }
}