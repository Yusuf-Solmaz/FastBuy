package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentFavoritesBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites.adapter.FavoritesAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private  var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    private fun setupUI(){
        loadBackgroundColor(requireContext()){
            color->
            view?.setBackgroundColor(Color.parseColor(color))
        }

        adapter = FavoritesAdapter(requireContext(),arrayListOf()){
                product ->
            viewModel.removeFavorite(product){
                    error->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers(){
        viewModel.favoriteProducts.observe(viewLifecycleOwner) { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.favoriteProductsResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.favoritesLayout
            )

            if (state.favoriteProductsResponse != null) {
                adapter.updateProducts(state.favoriteProductsResponse)
            }
            if (state.favoriteProductsResponse.isNullOrEmpty()){

                binding.emptyListText.visible()
            }
            else{
                binding.emptyListText.gone()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
