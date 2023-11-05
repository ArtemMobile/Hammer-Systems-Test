package com.example.hammersystemstesttask.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.hammersystemstesttask.data.model.dto.food.FoodDto
import com.example.hammersystemstesttask.data.model.dto.food.toEntity
import com.example.hammersystemstesttask.data.model.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM food WHERE idCategory == :idCategory")
    fun getFoodByCategory(idCategory: Int): List<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    @Transaction
    suspend fun updateFood(
        idCategory: Int,
        food: List<FoodDto>,
    ) {
        food.forEach { foodDto ->
            insertFood(
                foodDto.toEntity()
            )
        }
    }
}