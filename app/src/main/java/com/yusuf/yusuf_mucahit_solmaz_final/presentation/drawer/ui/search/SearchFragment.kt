package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentSearchBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.adapter.SearchProductAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var session: UserSessionRepository

    private  var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchProductAdapter: SearchProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchProducts("")

        setupUI()

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

        setupObservers()

    }

    private fun setupUI() {

        loadBackgroundColor(requireContext()) {
            color ->
            view?.setBackgroundColor(Color.parseColor(color))
        }

        searchProductAdapter = SearchProductAdapter(arrayListOf(), requireContext(), session) {
            viewModel.addToCart(it)
        }

        binding.rvSearchProduct.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchProductAdapter
        }
    }

    private fun setupObservers(){
        viewModel.searchAddToCart.observe(viewLifecycleOwner) { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.success != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.searchLayout
            )

            if (state.success != null) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.searchProduct.observe(viewLifecycleOwner) { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.productResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.searchLayout
            )

            if(state.productResponse != null){
                searchProductAdapter.updateSearchProduct(state.productResponse.products)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    }

