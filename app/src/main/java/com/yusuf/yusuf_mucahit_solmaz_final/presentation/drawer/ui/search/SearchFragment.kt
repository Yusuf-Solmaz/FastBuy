package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentSearchBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.adapter.SearchProductAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchProductAdapter: SearchProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchProducts("")

        searchProductAdapter = SearchProductAdapter(arrayListOf(), requireContext())
        binding.rvSearchProduct.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchProductAdapter
        }



        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchProducts(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchProducts(it) }
                return true
            }
        })

        viewModel.searchProduct.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> {
                    // Handle loading state if needed
                }

                state.error != null -> {
                    // Handle error state if needed
                }

                state.productResponse != null -> {
                    searchProductAdapter.updateSearchProduct(state.productResponse.products)
                }
            }
        }
    }
}
