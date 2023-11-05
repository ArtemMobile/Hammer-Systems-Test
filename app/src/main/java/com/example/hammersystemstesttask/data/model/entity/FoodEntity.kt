package com.example.hammersystemstesttask.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hammersystemstesttask.domain.model.FoodUiModel

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val description: String,
    val idCategory: Int,
    val name: String,
    val photo: String,
    val startPrice: Int,
)

fun FoodEntity.toDomain() = FoodUiModel(
    id = id,
    name = name,
    description = description,
    startPrice = startPrice,
    photo = photo,
    idCategory = idCategory
)

