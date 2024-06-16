package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryItem
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryResponse
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemCategoryBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.category.CategoryFragmentDirections

class CategoryAdapter(private val categories: ArrayList<RootCategoryItem>):
RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    class CategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            titleCategory.text = categories[position].slug

            categoryConstraintLayout.setOnClickListener {
                val action = CategoryFragmentDirections.actionNavCategoryToNavHome(categories[position].name)
                holder.binding.root.findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<RootCategoryItem>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }
}