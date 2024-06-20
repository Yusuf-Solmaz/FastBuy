package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.searchProduct.SearchProductUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase
): ViewModel() {

    private val _searchProduct = MutableLiveData<SearchState>()
    val searchProduct: LiveData<SearchState> = _searchProduct

    fun searchProducts(query: String) {
        _searchProduct.value = SearchState(isLoading = true)
        viewModelScope.launch {
            searchProductUseCase.searchProduct(query).collect { result ->

                when (result) {
                    is GeneralResult.Error -> {
                        _searchProduct.postValue(SearchState(
                            error = result.message,
                            isLoading = false,
                            productResponse = null))
                }
                    is GeneralResult.Loading -> {
                        _searchProduct.postValue(SearchState(
                            isLoading = true,
                            error = null,
                            productResponse = null))
                    }
                    is GeneralResult.Success -> {
                        _searchProduct.postValue(SearchState(
                            isLoading = false,
                            error = null,
                            productResponse = result.data))
            }
        }
    }
}
        }
}