package com.example.hammersystemstesttask.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hammersystemstesttask.domain.model.CategoryUiModel

@Entity("category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String
)

fun CategoryEntity.toDomain() = CategoryUiModel(
    id, name
)