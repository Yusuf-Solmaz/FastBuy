package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentCategoryBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.adapter.CategoryAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.adapter.GridSpacingItemDecoration
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()
    private  var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
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

        adapter = CategoryAdapter(arrayListOf())

        val spacing = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        val includeEdge = true
        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)
        binding.rvCategories.addItemDecoration(GridSpacingItemDecoration(2, spacing, includeEdge))
        binding.rvCategories.adapter = adapter
    }
    private fun setupObservers(){
        viewModel.categories.observe(viewLifecycleOwner, Observer { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.productResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.categoryLayout
            )

            if (state.productResponse != null){
                state.productResponse.let {
                    adapter.updateCategories(it)
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
