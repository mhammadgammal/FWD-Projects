package com.example.asteroidradar.detail

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.example.asteroidradar.repository.database.Asteroid
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class DetailFragmentArgs(
  public val selectedAsteroid: Asteroid
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
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

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(Asteroid::class.java)) {
      result.set("selectedAsteroid", this.selectedAsteroid as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(Asteroid::class.java)) {
      result.set("selectedAsteroid", this.selectedAsteroid as Serializable)
    } else {
      throw UnsupportedOperationException(Asteroid::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): DetailFragmentArgs {
      bundle.setClassLoader(DetailFragmentArgs::class.java.classLoader)
      val __selectedAsteroid : Asteroid?
      if (bundle.containsKey("selectedAsteroid")) {
        if (Parcelable::class.java.isAssignableFrom(Asteroid::class.java) ||
            Serializable::class.java.isAssignableFrom(Asteroid::class.java)) {
          __selectedAsteroid = bundle.get("selectedAsteroid") as Asteroid?
        } else {
          throw UnsupportedOperationException(Asteroid::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__selectedAsteroid == null) {
          throw IllegalArgumentException("Argument \"selectedAsteroid\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"selectedAsteroid\" is missing and does not have an android:defaultValue")
      }
      return DetailFragmentArgs(__selectedAsteroid)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): DetailFragmentArgs {
      val __selectedAsteroid : Asteroid?
      if (savedStateHandle.contains("selectedAsteroid")) {
        if (Parcelable::class.java.isAssignableFrom(Asteroid::class.java) ||
            Serializable::class.java.isAssignableFrom(Asteroid::class.java)) {
          __selectedAsteroid = savedStateHandle.get<Asteroid?>("selectedAsteroid")
        } else {
          throw UnsupportedOperationException(Asteroid::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__selectedAsteroid == null) {
          throw IllegalArgumentException("Argument \"selectedAsteroid\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"selectedAsteroid\" is missing and does not have an android:defaultValue")
      }
      return DetailFragmentArgs(__selectedAsteroid)
    }
  }
}
