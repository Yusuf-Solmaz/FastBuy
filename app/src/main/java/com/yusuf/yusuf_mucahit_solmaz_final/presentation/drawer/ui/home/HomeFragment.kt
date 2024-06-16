package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentHomeBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(arrayListOf(), requireContext())
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }

        val categoryName = args.category
        Log.d("categoryName", "onViewCreated: $categoryName")

        if (categoryName == "default") {
            homeViewModel.getProduct()
        } else {
            categoryName?.let {
                Log.d("getProductsByCategory", "Category: $it")
                homeViewModel.getProductsByCategory(it) }
        }

        homeViewModel.products.observe(viewLifecycleOwner, Observer { state ->
            when {
                state.isLoading -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    binding.rvProducts.visibility = View.GONE
                }
                state.error != null -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = state.error
                    binding.rvProducts.visibility = View.GONE
                }
                state.productResponse != null -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.rvProducts.visibility = View.VISIBLE
                    productAdapter.updateProducts(state.productResponse.products)
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}