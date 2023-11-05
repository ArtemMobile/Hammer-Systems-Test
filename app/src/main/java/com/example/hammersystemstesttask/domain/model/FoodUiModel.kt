package com.example.hammersystemstesttask.domain.model

data class FoodUiModel(
    val id: Int,
    val description: String,
    val idCategory: Int,
    val name: String,
    val photo: String,
    val startPrice: Int,
)