package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBackgroundColor(requireContext()){
                color->
            view.setBackgroundColor(Color.parseColor(color))
        }

        adapter = FavoritesAdapter(arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

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
                Log.d("stateelse", "onViewCreated: $state.favoriteProductsResponse")
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
}
