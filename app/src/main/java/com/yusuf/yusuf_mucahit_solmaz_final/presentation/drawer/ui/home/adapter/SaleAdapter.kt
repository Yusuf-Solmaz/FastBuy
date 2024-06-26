package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.GlideLoaderUtils
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setUpGlide
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.SaleItemBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.HomeFragmentDirections
import java.text.DecimalFormat

class SaleAdapter(private val context: Context, private val saleProducts: ArrayList<Product>) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {
    class SaleViewHolder(val binding: SaleItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val itemBinding = SaleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
       return saleProducts.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {

        holder.binding.apply {

            holder.binding.root.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToDetailFragment(saleProducts[position].id.toString())
                it.findNavController().navigate(action)
            }

            saleItemTitle.text = saleProducts[position].title
            saleItemPrice.text = ("${saleProducts[position].price}$")

            saleItemOldPrice.paintFlags = saleItemOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            val oldPrice = saleProducts[position].price / (1 - saleProducts[position].discountPercentage / 100)
            val decimalFormat = DecimalFormat("#.##")
            val formattedPrice = decimalFormat.format(oldPrice)
            saleItemOldPrice.text = ("($formattedPrice$)")


            setUpGlide(context,saleProducts[position].images[0],saleImage,loadingAnimationView)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSaleProducts(newProducts: List<Product>) {
        saleProducts.clear()
        saleProducts.addAll(newProducts)
        notifyDataSetChanged()
    }

}