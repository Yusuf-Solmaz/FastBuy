package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setupGlide
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemProductBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.HomeFragmentDirections

class ProductAdapter(private val products: ArrayList<Product>, private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding  = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        loadBackgroundColor(holder.binding.root.context) { color ->
            holder.binding.cardView.setBackgroundColor(Color.parseColor(color))
        }

        holder.binding.apply {
            title.text = products[position].title
            starRate.text = products[position].rating.toString()
            discount.text = "-%${products[position].discountPercentage}"
            availabilityStatus.text = products[position].availabilityStatus

            price.text = "${products[position].price}$"

            setupGlide(context,products[position].images[0],productImage,loadingAnimationView)

            cardView.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToDetailFragment(products[position].id.toString())
                it.findNavController().navigate(action)
            }
        }
    }

}
