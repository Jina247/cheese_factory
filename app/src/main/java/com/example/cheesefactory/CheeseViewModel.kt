package com.example.cheesefactory

import android.adservices.ondevicepersonalization.MutableKeyValueStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheeseViewModel: ViewModel() {
    private val _cheeseList = MutableLiveData<MutableList<CheeseData>>()
    val cheeseList: LiveData<MutableList<CheeseData>> = _cheeseList

    private val _favouriteCheeseList = MutableLiveData<MutableList<CheeseData>>()
    val favouriteCheeseList: LiveData<MutableList<CheeseData>> = _favouriteCheeseList

    fun setUpCheese(cheeseImage : Array<Int>, cheeseName: Array<String>,
                    cheeseShortDesc: Array<String>, cheeseDetailDesc: Array<String>) {
        val cheeses: MutableList<CheeseData> = mutableListOf()
        for (i in cheeseName.indices) {
            cheeses.add(CheeseData(cheeseImage[i], cheeseName[i],
                cheeseShortDesc[i], cheeseDetailDesc[i]))
        }
        _cheeseList.value = cheeses
        _favouriteCheeseList.value = mutableListOf()
    }

    fun addToFavourites(cheese: CheeseData, currentFavs: MutableList<CheeseData>) {
        if (!(currentFavs.contains(cheese))) {
            currentFavs.add(cheese)
            _favouriteCheeseList.value = currentFavs
        }
    }

    fun removeFromFavourites(cheese: CheeseData, currentFavs: MutableList<CheeseData>) {
        currentFavs.remove(cheese)
        _favouriteCheeseList.value = currentFavs

    }
}