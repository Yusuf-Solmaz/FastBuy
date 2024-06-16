package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getProductsCategory.GetProductsCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductsCategoryUseCase: GetProductsCategoryUseCase
): ViewModel() {

    private val _categories = MutableLiveData<CategoryState>()
    val categories: LiveData<CategoryState> = _categories

    init {
        getProductsCategory()
    }

    private fun getProductsCategory() {
        _categories.value = CategoryState(isLoading = true)
        viewModelScope.launch {
            getProductsCategoryUseCase.getProductsCategory().collect { result ->
                when (result) {
                    is GeneralResult.Error -> {
                        _categories.postValue(
                            CategoryState(
                                error = result.message,
                                isLoading = false,
                                productResponse = null
                            )
                        )
                    }

                    GeneralResult.Loading -> {
                        _categories.postValue(
                            CategoryState(
                                isLoading = true,
                                error = null,
                                productResponse = null
                            )
                        )
                    }

                    is GeneralResult.Success -> {
                        _categories.postValue(
                            CategoryState(
                                isLoading = false,
                                error = null,
                                productResponse = result.data
                            )
                        )
                    }
                }
            }
        }
    }
}