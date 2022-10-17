package com.example.shoestore.screens.shoe

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestore.model.Shoe

class ShoeListViewModel: ViewModel() {
    var shoe = Shoe("", 0.0F, "", "")
    private val shoesList = mutableListOf<Shoe?>()
    private val _shoeList = MutableLiveData<List<Shoe?>>()
    val shoeList: LiveData<List<Shoe?>>
        get() = _shoeList

    fun addNewShoe() {
        shoesList.add(shoe)
        shoesList.forEach{addedShoe ->
            Log.i(TAG, "addNewShoe: $addedShoe")
        }
        shoe = Shoe("", 0.0F, "", "")
        _shoeList.value = shoesList
    }

}