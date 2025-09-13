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
    private val _filteredList =  MutableLiveData<MutableList<CheeseData>>()
    val filteredList: LiveData<MutableList<CheeseData>> = _filteredList
    private val selectedMilk = mutableSetOf<String>()
    private val selectedTexture = mutableSetOf<String>()
    private val selectedFlavour = mutableSetOf<String>()
    private val selectedAged = mutableSetOf<String>()

    fun setUpCheese(cheeseImage : Array<Int>, cheeseName: Array<String>, cheeseFullName: Array<String>,
                    cheeseShortDesc: Array<String>, cheeseLongDesc: Array<String>,
                    cheeseOrigin: Array<String>, cheeseMilkSource: Array<String>,
                    cheeseTexture: Array<String>, cheeseAge: Array<String>,
                    cheeseFlavour: Array<String>, cheeseFoodPairing: Array<String>, cheeseWinePairing: Array<String>) {
        val cheeses: MutableList<CheeseData> = mutableListOf()
        for (i in cheeseName.indices) {
            cheeses.add(CheeseData(cheeseImage[i],
                cheeseName[i],
                cheeseFullName[i],
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

    fun addToFavourites(cheese: CheeseData, currentFav: MutableList<CheeseData>) {
        if (!(currentFav.contains(cheese))) {
            cheese.isLiked = true
            currentFav.add(cheese)
            _favouriteCheeseList.value = currentFav

        }
    }

    fun removeFromFavourites(cheese: CheeseData, currentFav: MutableList<CheeseData>) {
        currentFav.remove(cheese)
        cheese.isLiked = false
        _favouriteCheeseList.value = currentFav
    }

    fun doLike(cheese: CheeseData, currentFav: MutableList<CheeseData>) {
        if (cheese.isLiked) {
            removeFromFavourites(cheese, currentFav)
        } else {
            addToFavourites(cheese, currentFav)
        }
        _cheeseList.value = _cheeseList.value
    }

    fun selectCheese(cheese: CheeseData) {
        _selectedCheese.value = cheese
    }

    fun setFilters(
        milk: Set<String>,
        texture: Set<String>,
        flavour: Set<String>,
        age: Set<String>
    ) {
        selectedMilk.clear(); selectedMilk.addAll(milk)
        selectedTexture.clear(); selectedTexture.addAll(texture)
        selectedFlavour.clear(); selectedFlavour.addAll(flavour)
        selectedAged.clear(); selectedAged.addAll(age)
        applyFilters()
    }

    private fun applyFilters() {
        val allCheeses = _cheeseList.value ?: return
        var result = allCheeses

        if (selectedMilk.isNotEmpty()) {
            result = result.filter { it.milkSource in selectedMilk }.toMutableList()
        }

        if (selectedTexture.isNotEmpty()) {
            result = result.filter { it.texture in selectedTexture }.toMutableList()
        }

        if (selectedFlavour.isNotEmpty()) {
            result = result.filter { it.flavour in selectedFlavour }.toMutableList()
        }

        if (selectedAged.isNotEmpty()) {
            result = result.filter { it.age in selectedAged }.toMutableList()
        }
        _filteredList.value = result
    }
}