package com.example.hammersystemstesttask.domain.repository

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import kotlinx.coroutines.flow.Flow
import com.example.hammersystemstesttask.domain.Result

interface NetworkFoodRepository {
    suspend fun getFoodByCategory(idCategory: Int, callBack: () -> Unit) : Flow<Result<FoodResponse>>
    suspend fun getCategories() : Flow<Result<CategoriesResponse>>
}