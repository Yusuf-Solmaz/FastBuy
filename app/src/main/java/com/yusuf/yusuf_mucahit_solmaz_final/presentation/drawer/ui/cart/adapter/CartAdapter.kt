package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart.Product
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemCartBinding

class CartAdapter(private val products: ArrayList<Product>,private val context: Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
       val itemBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.apply {
            title.text = products[position].title
            quantity.text = products[position].quantity.toString()
            price.text = products[position].price.toString()

            Glide.with(context)
                .load(products[position].thumbnail)
                .into(productImage)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}