package com.example.asteroidradar.main

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.asteroidradar.R
import com.example.asteroidradar.repository.database.Asteroid
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class MainFragmentDirections private constructor() {
  private data class NavToDetailFragemnt(
    public val selectedAsteroid: Asteroid
  ) : NavDirections {
    public override val actionId: Int = R.id.nav_to_detail_fragemnt

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(Asteroid::class.java)) {
          result.putParcelable("selectedAsteroid", this.selectedAsteroid as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(Asteroid::class.java)) {
          result.putSerializable("selectedAsteroid", this.selectedAsteroid as Serializable)
        } else {
          throw UnsupportedOperationException(Asteroid::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
      }
  }

  public companion object {
    public fun navToDetailFragemnt(selectedAsteroid: Asteroid): NavDirections =
        NavToDetailFragemnt(selectedAsteroid)
  }
}
