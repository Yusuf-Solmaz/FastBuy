package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import android.graphics.Color
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
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentHomeBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.CarouselAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.ProductAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.SaleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var sessionManager: SessionManager
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var saleAdapter: SaleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBackgroundColor(requireContext()){
                color->
            view.setBackgroundColor(Color.parseColor(color))
        }

        sessionManager = SessionManager.getInstance(requireContext())



        productAdapter = ProductAdapter(arrayListOf(), requireContext())
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }

        carouselAdapter = CarouselAdapter(arrayListOf())
        binding.carouselRecyclerview.apply {
            adapter = carouselAdapter
        }

        saleAdapter = SaleAdapter(arrayListOf())
        binding.saleRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = saleAdapter
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
                    binding.loadingLayout.visible()
                    binding.homeLayout.gone()
                    binding.errorLayout.gone()
                }
                state.error != null -> {
                    binding.error.text = state.error

                    binding.loadingLayout.gone()
                    binding.homeLayout.gone()
                    binding.errorLayout.visible()
                }
                state.productResponse != null -> {

                    binding.loadingLayout.gone()
                    binding.homeLayout.visible()
                    binding.errorLayout.gone()

                    productAdapter.updateProducts(state.productResponse.products)

                    binding.carouselRecyclerview.apply {
                        set3DItem(true)
                        setAlpha(true)
                        setInfinite(true)
                    }
                    carouselAdapter.updateProducts(state.productResponse.products)


                     val randomProducts = getRandomProducts(state.productResponse.products, 5)
                    saleAdapter.updateSaleProducts(randomProducts)
                }
            }
        })
    }

    private fun getRandomProducts(products: List<Product>, count: Int): List<Product> {
        return if (products.size <= count) {
            products
        } else {
            val randomIndices = mutableSetOf<Int>()
            while (randomIndices.size < count) {
                randomIndices.add(Random.nextInt(products.size))
            }
            randomIndices.map { products[it] }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}