package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentHomeBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.CarouselAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.ProductAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.SaleAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.viewmodel.HomeViewModel
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

    private val allProducts = ArrayList<Product>()
    private val carouselProducts = ArrayList<Product>()
    private val saleProducts = ArrayList<Product>()

    private var isLoading = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager.getInstance(requireContext())

        setupUI()

        val categoryName = args.category

        if (categoryName == "default") {
            homeViewModel.getProduct()
        } else {
            categoryName?.let {

                homeViewModel.getProductsByCategory(it)
            }
        }

        setupObservers()
    }

    private fun setupUI() {

        loadBackgroundColor(requireContext()) {
            color ->
            view?.setBackgroundColor(Color.parseColor(color))
        }

        productAdapter = ProductAdapter(allProducts, requireContext())
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && !isLastPage && (visibleItemCount + firstVisibleItemPosition >= totalItemCount) && firstVisibleItemPosition >= 0) {
                        val categoryName = args.category
                        if (categoryName == "default") {
                            homeViewModel.getProduct()
                        }
                    }
                }
            })
        }

        carouselAdapter = CarouselAdapter(requireContext(),carouselProducts)
        binding.carouselRecyclerview.apply {
            adapter = carouselAdapter
        }

        saleAdapter = SaleAdapter(requireContext(),saleProducts)
        binding.saleRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = saleAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        homeViewModel.products.observe(viewLifecycleOwner, Observer { state ->

            isLastPage = state.productResponse?.products?.isEmpty() == true

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.productResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.homeLayout
            )

            if (state.productResponse != null) {
                val newProducts = state.productResponse.products
                allProducts.addAll(newProducts)
                carouselProducts.addAll(newProducts)
                saleProducts.addAll(newProducts)

                productAdapter.notifyDataSetChanged()
                carouselAdapter.notifyDataSetChanged()
                saleAdapter.notifyDataSetChanged()

                binding.carouselRecyclerview.apply {
                    set3DItem(true)
                    setAlpha(true)
                    setInfinite(true)
                }

                val randomProducts = getRandomProducts(state.productResponse.products)
                saleAdapter.updateSaleProducts(randomProducts)
            }

            if (state.productResponse?.products?.isEmpty() == true) {
                binding.noDataTextView.visible()
                binding.homeLayout.gone()
            } else {
                binding.noDataTextView.gone()
            }


            if (isLastPage && state.productResponse?.products?.isEmpty() == true) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.no_more_products), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRandomProducts(products: List<Product>, count: Int = 5): List<Product> {
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