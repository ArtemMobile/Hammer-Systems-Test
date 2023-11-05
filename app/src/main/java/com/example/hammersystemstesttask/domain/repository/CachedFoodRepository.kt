package com.example.hammersystemstesttask.domain.repository

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import kotlinx.coroutines.flow.Flow
import com.example.hammersystemstesttask.domain.Result
import com.example.hammersystemstesttask.domain.model.CategoryUiModel
import com.example.hammersystemstesttask.domain.model.FoodUiModel
import javax.security.auth.callback.Callback

interface CachedFoodRepository {
    suspend fun getCachedFoodByCategory(idCategory: Int) : Flow<Result<List<FoodUiModel>>>
    suspend fun getCachedCategories() : Flow<Result<List<CategoryUiModel>>>
    suspend fun cacheFoodResponse(idCategory: Int, foodResponse: FoodResponse, callback: () -> Unit)
    suspend fun cacheCategories(categoriesResponse: CategoriesResponse, callback: () -> Unit)
}