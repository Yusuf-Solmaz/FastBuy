package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setUpGlide
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.CarouselCategoryItemBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.HomeFragmentDirections

class CarouselAdapter(private val context: Context,val products: ArrayList<Product>): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    class CarouselViewHolder(val binding: CarouselCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemBinding = CarouselCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.binding.apply {
            corouselTitle.text = products[position].title

            setUpGlide(context,products[position].images[0],corouselImage,null)

            holder.binding.root.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToDetailFragment(products[position].id.toString())
                it.findNavController().navigate(action)
            }
        }
    }
}