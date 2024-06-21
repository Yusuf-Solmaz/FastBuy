package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemSearchProductBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.HomeFragmentDirections
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.search.SearchFragmentDirections

class SearchProductAdapter (private  val products: ArrayList<Product>, private val context: Context)
    : RecyclerView.Adapter<SearchProductAdapter.SearchProductViewHolder>() {

        class SearchProductViewHolder(val binding: ItemSearchProductBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        val itemBinding = ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchProductViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {

        loadBackgroundColor(holder.binding.root.context){
                color->
            holder.binding.cardView.setBackgroundColor(Color.parseColor(color))
        }

        holder.binding.apply {
            title.text = products[position].title
            starRate.text = products[position].rating.toString()
            discount.text = "-%${products[position].discountPercentage}"
            availabilityStatus.text = products[position].availabilityStatus

            Glide.with(context)
                .load(products[position].images[0])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(productImage)

            cardView.setOnClickListener {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(products[position].id.toString())
                it.findNavController().navigate(action)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchProduct(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}