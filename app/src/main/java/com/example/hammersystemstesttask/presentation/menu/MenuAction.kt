package com.example.hammersystemstesttask.presentation.menu

import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import com.example.hammersystemstesttask.presentation.common.ViewAction

sealed class MenuAction : ViewAction{
    data class GetFood(val idCategory: Int): MenuAction()
    class GetCachedFood(val idCategory: Int): MenuAction()
    class CacheFood(val idCategory: Int, val food: FoodResponse): MenuAction()
    data object GetAllCategories: MenuAction()
    data object GetCachedCategories: MenuAction()
    class CacheCategories(val categories: CategoriesResponse): MenuAction()
}