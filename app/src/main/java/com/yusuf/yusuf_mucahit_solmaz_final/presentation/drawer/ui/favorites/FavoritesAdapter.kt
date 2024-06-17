package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemFavoriteProductBinding

class FavoritesAdapter(private val favoriteProducts: ArrayList<FavoriteProducts>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private var expandedPosition = -1

    inner class FavoriteViewHolder(val binding: ItemFavoriteProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.expandedIcon.setOnClickListener {
                val position = adapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnClickListener

                // Toggle the expanded state
                expandedPosition = if (expandedPosition == position) -1 else position
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val product = favoriteProducts[position]
        holder.binding.apply {
            title.text = product.title
            starRate.text = product.rating.toString()
            Glide.with(productImage.context).load(product.productImage).into(productImage)

            // Handle expansion logic
            expandedDescription.visibility = if (position == expandedPosition) View.VISIBLE else View.GONE
            viewAllDetailsBtn.visibility = if (position == expandedPosition) View.VISIBLE else View.GONE
            viewAllDetailsBtn.setOnClickListener {
                val action = FavoritesFragmentDirections.actionNavFavoritesToDetailFragment(product.productId.toString())
                it.findNavController().navigate(action)
            }


        }
    }

    override fun getItemCount(): Int = favoriteProducts.size


    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<FavoriteProducts>) {
        favoriteProducts.clear()
        favoriteProducts.addAll(newProducts)
        notifyDataSetChanged()
    }
}
