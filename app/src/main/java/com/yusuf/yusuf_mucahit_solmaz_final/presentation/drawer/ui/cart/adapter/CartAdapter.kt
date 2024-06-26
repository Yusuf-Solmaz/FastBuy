package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setupGlide
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.userCart.Product
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemCartBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.CartFragmentDirections

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.binding.apply {
            title.text = products[position].title
            amount.text = "${context.getString(R.string.amount)}: ${products[position].quantity}"
            price.text = "${products[position].price}$"

            setupGlide(context,products[position].thumbnail,productImage,loadingAnimationView)

            root.setOnClickListener {
                val action = CartFragmentDirections.actionNavCartToDetailFragment(products[position].id.toString())
                root.findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}