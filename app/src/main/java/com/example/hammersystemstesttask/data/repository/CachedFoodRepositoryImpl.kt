package com.example.hammersystemstesttask.data.repository

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import com.example.hammersystemstesttask.data.model.entity.toDomain
import com.example.hammersystemstesttask.data.source.local.FoodDatabase
import com.example.hammersystemstesttask.domain.Result
import com.example.hammersystemstesttask.domain.model.CategoryUiModel
import com.example.hammersystemstesttask.domain.model.FoodUiModel
import com.example.hammersystemstesttask.domain.repository.CachedFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CachedFoodRepositoryImpl @Inject constructor(private val database: FoodDatabase) :
    CachedFoodRepository {
    override suspend fun getCachedFoodByCategory(idCategory: Int): Flow<Result<List<FoodUiModel>>> =
        flow<Result<List<FoodUiModel>>> {
            val cachedFood = database.foodDao().getFoodByCategory(idCategory).map { it.toDomain() }
            emit(Result.Success(cachedFood))
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.Error(it.message.toString()))
        }

    override suspend fun getCachedCategories(): Flow<Result<List<CategoryUiModel>>> =
        flow<Result<List<CategoryUiModel>>> {
            val cachedCategories = database.categoryDao().getCategories().map { it.toDomain() }
            emit(Result.Success(cachedCategories))
        }.flowOn(Dispatchers.IO).catch {
            emit(Result.Error(it.message.toString()))
        }


    override suspend fun cacheFoodResponse(
        idCategory: Int,
        foodResponse: FoodResponse,
        callback: () -> Unit
    ) {
        database.foodDao().updateFood(idCategory, foodResponse)
        callback()
    }

    override suspend fun cacheCategories(
        categoriesResponse: CategoriesResponse,
        callback: () -> Unit
    ) {
        database.categoryDao().updateCategories(categoriesResponse)
        callback()
    }
}