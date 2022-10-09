package com.example.asteroidradar.repository.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Entity(tableName = "asteroid_table")
@Parcelize
data class Asteroid(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val is_potentially_hazardous_asteroid: Boolean
) : Parcelable

//fun List<Asteroid>.asDatabaseModel(): Array<Asteroid> {
//    return map{ asteroid ->
//        Asteroid(
//            asteroid.id,
//            asteroid.name,
//            asteroid.closeApproachDate,
//            asteroid.absoluteMagnitude, asteroid.estimatedDiameter,
//            asteroid.relativeVelocity, asteroid.distanceFromEarth,
//            asteroid.is_potentially_hazardous_asteroid
//        )
//    }.toTypedArray()
//}