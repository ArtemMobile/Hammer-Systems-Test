package com.example.hammersystemstesttask.presentation.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hammersystemstesttask.R
import com.example.hammersystemstesttask.data.model.dto.category.CategoriesResponse
import com.example.hammersystemstesttask.data.model.dto.food.FoodResponse
import com.example.hammersystemstesttask.databinding.CategoryChipBinding
import com.example.hammersystemstesttask.databinding.FragmentMenuBinding
import com.example.hammersystemstesttask.domain.model.CategoryUiModel
import com.example.hammersystemstesttask.domain.model.FoodUiModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    private val menuAdapter by lazy {
        MenuAdapter(requireContext(), listOf())
    }

    private val menuViewModel by lazy {
        ViewModelProvider(this)[MenuViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAllFood()
        observeCachedFood()
        observeCategories()
        observeCachedCategories()
        applyMenuRecycler()
        applyNewsRecyclerView()
    }

    private fun applyNewsRecyclerView() {
        val list = listOf(R.drawable.banner_1, R.drawable.banner_2)
        binding.recyclerViewNews.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = NewsAdapter(list)
        }
    }

    private fun applyMenuRecycler() {
        binding.recyclerViewMenu.apply {
            adapter = menuAdapter
        }
    }

    private fun observeCachedFood() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.cachedFoodState.collect {
                    when (it) {
                        is MenuState.Exception -> {
                            hideShimmer()
                            Log.d("EXCEPTION", it.error)
                        }

                        MenuState.Loading -> {
                            showShimemr()
                        }

                        is MenuState.Success -> {
                            hideShimmer()
                            menuAdapter.updateList(it.data as List<FoodUiModel>)
                        }
                    }
                }
            }
        }
    }

    private fun observeCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.categoriesState.collect {
                    when (it) {
                        is MenuState.Exception -> {
//                            applyErrorSnackBar()
                            menuViewModel.dispatchIntent(MenuIntent.LoadCachedCategories)
                        }

                        MenuState.Loading -> {
                            showShimemr()
                        }

                        is MenuState.Success -> {
                            menuViewModel.dispatchIntent(
                                MenuIntent.CacheCategories(it.data as CategoriesResponse)
                            )
                           // menuViewModel.dispatchIntent(MenuIntent.LoadCachedCategories)
                        }
                    }
                }
            }
        }
    }

    private fun observeCachedCategories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.cachedCategoriesState.collect {
                    when (it) {
                        is MenuState.Exception -> {
                            Log.d("EXCEPTION", it.error)
                        }

                        MenuState.Loading -> {
                            showShimemr()
                        }

                        is MenuState.Success -> {
                            fillCategories(it.data as List<CategoryUiModel>)
                            menuViewModel.dispatchIntent(MenuIntent.LoadFood(binding.categoryGroup.checkedChipId))
                        }
                    }
                }
            }
        }
    }

    private fun observeAllFood() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                menuViewModel.foodState.collect {
                    when (it) {
                        is MenuState.Exception -> {
                            hideShimmer()
                            Log.d("EXCEPTION", it.error)
                        }

                        MenuState.Loading -> {
                            showShimemr()
                        }

                        is MenuState.Success -> {
                            val categoryId = binding.categoryGroup.checkedChipId
                            menuViewModel.dispatchIntent(
                                MenuIntent.CacheFood(
                                    categoryId,
                                    it.data as FoodResponse
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun hideShimmer() {
        binding.shimmerLayout.isVisible = false
    }

    private fun applyErrorSnackBar() {
        val snackBarView = Snackbar.make(
            requireView(),
            "Отсутстует подключение к интернету",
            Snackbar.LENGTH_INDEFINITE
        )
        snackBarView.setAction(R.string.try_again) {
            menuViewModel.dispatchIntent(MenuIntent.LoadCategories)
            snackBarView.dismiss()
        }
        snackBarView.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
        snackBarView.show()
    }

    private fun showShimemr() {
        binding.shimmerLayout.isVisible = true
    }

    private fun fillCategories(categories: List<CategoryUiModel>) {
        if (binding.categoryGroup.isEmpty()) {
            categories.forEachIndexed { _, category ->
                val chip =
                    CategoryChipBinding.inflate(layoutInflater).rootChip.apply {
                        text = category.name
                    }
                binding.categoryGroup.apply {
                    addView(chip.apply {
                        id = category.id
                    })
                    check(this.getChildAt(0).id)

                    setOnCheckedStateChangeListener { _, _ ->
                        menuViewModel.dispatchIntent(MenuIntent.LoadFood(checkedChipId))
                    }
                }
            }
        }
    }
}