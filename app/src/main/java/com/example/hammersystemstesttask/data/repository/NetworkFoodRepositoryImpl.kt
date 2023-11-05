package com.example.hammersystemstesttask.data.repository

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import com.example.hammersystemstesttask.data.source.remote.FoodApiService
import com.example.hammersystemstesttask.domain.repository.NetworkFoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.hammersystemstesttask.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkFoodRepositoryImpl @Inject constructor(private val apiService: FoodApiService) :
    NetworkFoodRepository {
    override suspend fun getFoodByCategory(idCategory: Int, callback :() -> Unit): Flow<Result<FoodResponse>> = flow {
        val response = apiService.getFoodByCategory(idCategory)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Result.Success(it))
            }
        } else {
            callback()
            emit(Result.Error(response.errorBody()?.string().toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        callback()
        emit(Result.Error(it.message.toString()))
    }



    override suspend fun getCategories(): Flow<Result<CategoriesResponse>> = flow {
        val response = apiService.getCategories()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Result.Success(it))
            }
        } else {
            emit(Result.Error(response.errorBody()?.string().toString()))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Result.Error(it.message.toString()))
    }

}