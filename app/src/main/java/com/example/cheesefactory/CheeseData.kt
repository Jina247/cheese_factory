package com.example.cheesefactory

// Data for overall cheese data
data class CheeseData(
    val image: Int,
    val name: String,
    val shortDescription: String,
    var isLiked: Boolean,
    val longDescription: String,
    val origin: String,
    val milkSource: String,
    val texture: String,
    val age: String,
    val flavour: String,
    val foodPairing: String,
    val winePairing: String
)
