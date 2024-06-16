package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.category.RootCategoryItem
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemProductBinding

class ProductAdapter(private val products: ArrayList<Product>,private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding  = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            title.text = products[position].title
            starRate.text = products[position].rating.toString()
            discount.text = "-%${products[position].discountPercentage}"
            availabilityStatus.text = products[position].availabilityStatus

            Glide.with(context)
                .load(products[position].images[0])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(productImage)
        }
    }

    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }


}
