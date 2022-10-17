package com.example.shoestore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//,val images: List<String> = mutableListOf()
@Parcelize
data class Shoe(var name: String, var size: Float, var company: String, var description: String) : Parcelable