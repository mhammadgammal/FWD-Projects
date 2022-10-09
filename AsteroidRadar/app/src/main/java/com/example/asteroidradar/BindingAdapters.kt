package com.example.asteroidradar

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("setPic")
fun downloadPic(imageView: ImageView, url: String?) {
    url?.let {
        val picUri = url.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(picUri)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = R.string.potentially_hazardous_asteroid_image.toString()
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = R.string.not_hazardous_asteroid_image.toString()
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
