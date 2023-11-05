package com.example.hammersystemstesttask.presentation.menu

import com.example.hammersystemstesttask.domain.repository.CachedFoodRepository
import com.example.hammersystemstesttask.domain.repository.NetworkFoodRepository
import com.example.hammersystemstesttask.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val networkFoodRepository: NetworkFoodRepository,
    private val cachedFoodRepository: CachedFoodRepository
) : BaseViewModel<MenuIntent, MenuAction, MenuState>() {

    init {
        dispatchIntent(MenuIntent.LoadCategories)
    }

    private val _categoriesState = MutableStateFlow<MenuState>(MenuState.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private val _cachedCategoriesState = MutableStateFlow<MenuState>(MenuState.Loading)
    val cachedCategoriesState = _cachedCategoriesState.asStateFlow()

    private val _foodState = MutableStateFlow<MenuState>(MenuState.Loading)
    val foodState = _foodState.asStateFlow()

    private val _cachedFoodState = MutableStateFlow<MenuState>(MenuState.Loading)
    val cachedFoodState = _cachedFoodState.asStateFlow()

    protected override fun intentToAction(intent: MenuIntent): MenuAction {
        return when (intent) {
            is MenuIntent.CacheCategories -> MenuAction.CacheCategories(intent.categories)
            is MenuIntent.CacheFood -> MenuAction.CacheFood(intent.idCategory, intent.food)
            MenuIntent.LoadCachedCategories -> MenuAction.GetCachedCategories
            is MenuIntent.LoadCachedFood -> MenuAction.GetCachedFood(intent.idCategory)
            MenuIntent.LoadCategories -> MenuAction.GetAllCategories
            is MenuIntent.LoadFood -> MenuAction.GetFood(intent.idCategory)
        }
    }

    protected override fun handleAction(action: MenuAction) {
        launchOnUI {
            when (action) {
                is MenuAction.CacheCategories -> cachedFoodRepository.cacheCategories(action.categories){
                    dispatchIntent(MenuIntent.LoadCachedCategories)
                }
                is MenuAction.CacheFood -> cachedFoodRepository.cacheFoodResponse(action.idCategory, action.food){
                    dispatchIntent(MenuIntent.LoadCachedFood(action.idCategory))
                }
                MenuAction.GetAllCategories -> networkFoodRepository.getCategories().collect {
                    _categoriesState.value = it.reduce()
                }

                MenuAction.GetCachedCategories -> cachedFoodRepository.getCachedCategories().collect {
                    _cachedCategoriesState.value = it.reduce()
                }

                is MenuAction.GetCachedFood -> cachedFoodRepository.getCachedFoodByCategory(action.idCategory).collect {
                    _cachedFoodState.value = it.reduce()
                }

                is MenuAction.GetFood -> networkFoodRepository.getFoodByCategory(action.idCategory) {
                    dispatchIntent(MenuIntent.LoadCachedFood(action.idCategory))
                }.collect {
                    _foodState.value = it.reduce()
                }

            }
        }
    }
}