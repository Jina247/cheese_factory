package com.example.cheesefactory

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
