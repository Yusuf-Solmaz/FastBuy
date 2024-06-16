package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentCategoryBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.adapter.CategoryAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.adapter.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryAdapter(arrayListOf())

        val spacing = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        val includeEdge = true
        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)
        binding.rvCategories.addItemDecoration(GridSpacingItemDecoration(2, spacing, includeEdge))
        binding.rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, Observer { state ->
            state.productResponse?.let {
                adapter.updateCategories(it)
            }
        })
    }
}
