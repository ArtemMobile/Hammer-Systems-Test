package com.example.hammersystemstesttask.data.source.remote

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {

    @GET(ApiConstants.CATEGORIES_ENDPOINT)
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("${ApiConstants.FOOD_ENDPOINT}/{${ApiConstants.ID_CATEGORY_PARAM}}")
    suspend fun getFoodByCategory(@Path("idCategory") idCategory: Int): Response<FoodResponse>
}