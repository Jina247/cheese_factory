package com.example.cheesefactory

// Data for overall cheese data
data class CheeseData(
    val image: Int,
    val name: String,
    val shortDescription: String,
    var isLiked: Boolean
)
