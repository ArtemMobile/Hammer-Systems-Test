package com.example.hammersystemstesttask.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.example.hammersystemstesttask.data.model.dto.category.CategoryDto
import com.example.hammersystemstesttask.data.model.dto.category.toEntity
import com.example.hammersystemstesttask.data.model.dto.food.FoodDto
import com.example.hammersystemstesttask.data.model.dto.food.toEntity
import com.example.hammersystemstesttask.data.model.entity.CategoryEntity
import com.example.hammersystemstesttask.data.model.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Query("DELETE FROM category")
    suspend fun clearCategories()

    @Transaction
    suspend fun updateCategories(
        categories: List<CategoryDto>,
    ) {
        clearCategories()

        categories.forEach { categoryDto ->
            insertCategory(
                categoryDto.toEntity()
            )
        }
    }
}