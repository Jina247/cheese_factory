package com.example.cheesefactory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheeseViewModel: ViewModel() {
    private val _cheeseList = MutableLiveData<MutableList<CheeseData>>()
    val cheeseList: LiveData<MutableList<CheeseData>> = _cheeseList

    private val _favouriteCheeseList = MutableLiveData<MutableList<CheeseData>>()
    val favouriteCheeseList: LiveData<MutableList<CheeseData>> = _favouriteCheeseList
    private val _selectedCheese = MutableLiveData<CheeseData>()
    val selectedCheese: LiveData<CheeseData> = _selectedCheese

    fun setUpCheese(cheeseImage : Array<Int>, cheeseName: Array<String>,
                    cheeseShortDesc: Array<String>, cheeseLongDesc: Array<String>,
                    cheeseOrigin: Array<String>, cheeseMilkSource: Array<String>,
                    cheeseTexture: Array<String>, cheeseAge: Array<String>,
                    cheeseFlavour: Array<String>, cheeseFoodPairing: Array<String>, cheeseWinePairing: Array<String>) {
        val cheeses: MutableList<CheeseData> = mutableListOf()
        for (i in cheeseName.indices) {
            cheeses.add(CheeseData(cheeseImage[i],
                cheeseName[i],
                cheeseShortDesc[i],
                false,
                cheeseLongDesc[i],
                cheeseOrigin[i],
                cheeseMilkSource[i],
                cheeseTexture[i],
                cheeseAge[i],
                cheeseFlavour[i],
                cheeseFoodPairing[i],
                cheeseWinePairing[i]))
        }
        _cheeseList.value = cheeses
        _favouriteCheeseList.value = mutableListOf()
    }

    fun addToFavourites(cheese: CheeseData, currentFavs: MutableList<CheeseData>) {
        if (!(currentFavs.contains(cheese))) {
            cheese.isLiked = true
            currentFavs.add(cheese)
            _favouriteCheeseList.value = currentFavs

        }
    }

    fun removeFromFavourites(cheese: CheeseData, currentFavs: MutableList<CheeseData>) {
        currentFavs.remove(cheese)
        cheese.isLiked = false
        _favouriteCheeseList.value = currentFavs
    }

    fun doLike(cheese: CheeseData, currentFavs: MutableList<CheeseData>) {
        if (cheese.isLiked) {
            removeFromFavourites(cheese, currentFavs)
        } else {
            addToFavourites(cheese, currentFavs)
        }
        _cheeseList.value = _cheeseList.value
    }

    fun selectCheese(cheese: CheeseData) {
        _selectedCheese.value = cheese
    }
}