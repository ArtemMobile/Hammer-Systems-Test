package com.example.hammersystemstesttask.presentation.menu

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import com.example.hammersystemstesttask.presentation.common.ViewIntent

sealed class MenuIntent : ViewIntent{
    data class LoadFood(val idCategory: Int): MenuIntent()
    class LoadCachedFood(val idCategory: Int): MenuIntent()
    class CacheFood(val idCategory: Int, val food: FoodResponse): MenuIntent()
    data object LoadCategories: MenuIntent()
    data object LoadCachedCategories: MenuIntent()
    class CacheCategories(val categories: CategoriesResponse): MenuIntent()
}