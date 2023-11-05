package com.example.hammersystemstesttask.data.model.dto.category


import com.example.hammersystemstesttask.data.model.entity.CategoryEntity
import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)


fun List<CategoryDto>.toEntity() = map { it.toEntity() }
fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = name,
)