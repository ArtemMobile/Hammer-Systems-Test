package com.example.hammersystemstesttask.data.model.dto.food


import com.example.hammersystemstesttask.data.model.dto.category.CategoryDto
import com.example.hammersystemstesttask.data.model.entity.CategoryEntity
import com.example.hammersystemstesttask.data.model.entity.FoodEntity
import com.google.gson.annotations.SerializedName

data class FoodDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("idCategory")
    val idCategory: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("startPrice")
    val startPrice: Int
)

fun FoodDto.toEntity() = FoodEntity(
    id = id,
    name = name,
    description = description,
    idCategory = idCategory,
    photo = photo,
    startPrice = startPrice
)