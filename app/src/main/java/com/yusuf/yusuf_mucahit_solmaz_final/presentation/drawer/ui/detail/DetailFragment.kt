package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.SessionManager
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import com.yusuf.yusuf_mucahit_solmaz_final.data.local.model.FavoriteProducts
import com.yusuf.yusuf_mucahit_solmaz_final.data.mapper.toAddCartRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.mapper.toFavoriteProduct
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Product
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentDetailBinding
import com.yusuf.yusuf_mucahit_solmaz_final.di.AppModule.addToCart
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.adapter.CommentsAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.HomeFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment() : Fragment() {

    @Inject
    lateinit var session: UserSessionRepository

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var commentAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBackgroundColor(requireContext()){
                color->
            view.setBackgroundColor(Color.parseColor(color))
        }

        commentAdapter = CommentsAdapter(arrayListOf())
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }


        val id = args.id


        viewModel.getProductById(id)


        viewModel.productDetail.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.scrollView2.visibility = View.GONE
                    binding.addToCart.visibility = View.GONE
                }
                state.error != null -> {
                    binding.error.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView2.visibility = View.GONE
                    binding.addToCart.visibility = View.GONE
                }
                state.productResponse != null -> {
                    binding.error.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.scrollView2.visibility = View.VISIBLE
                    binding.addToCart.visibility = View.VISIBLE

                    binding.apply {
                        title.text = state.productResponse.title
                        productPrice.text = ("${state.productResponse.price}$")
                        discount.text = "(-%${state.productResponse.discountPercentage})"
                        rating.text = state.productResponse.rating.toString()

                        Glide.with(requireContext())
                            .load(state.productResponse.images[0])
                            .into(productImage)
                            .clearOnDetach()

                        description.text = state.productResponse.description
                        stock.text = "In Stock: ${state.productResponse.stock}"
                        stock.setTextColor(if (state.productResponse.availabilityStatus == "Low Stock") {
                            resources.getColorStateList(R.color.statusRed)
                        } else {
                            resources.getColorStateList(R.color.statusGreen)
                        })

                        commentAdapter.updateComments(state.productResponse.reviews)

                        textView2.paintFlags = textView2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                        val oldPrice = state.productResponse.price / (1 - state.productResponse.discountPercentage / 100)
                        val decimalFormat = DecimalFormat("#.##")
                        val formattedPrice = decimalFormat.format(oldPrice)
                        textView2.text = ("$formattedPrice$")


                        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->

                            binding.checkBox.setOnCheckedChangeListener(null)
                            binding.checkBox.isChecked = isFavorite

                            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                                val favoriteProduct = state.productResponse.toFavoriteProduct()
                                if (isChecked) {
                                    viewModel.addOrRemoveFavorite(favoriteProduct)
                                } else {
                                    viewModel.addOrRemoveFavorite(favoriteProduct)
                                }
                            }
                        }

                        viewModel.addToCartState.observe(viewLifecycleOwner) { state ->
                            when {
                                state.isLoading -> {

                                }
                                state.error != null -> {

                                }
                                state.success != null -> {
                                    Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }
                    binding.addToCart.setOnClickListener {
                        showAddToCartDialog(product = state.productResponse)
                    }
                }
            }
        }
    }

    private fun showAddToCartDialog(product: Product) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_to_cart, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val etQuantity = dialogView.findViewById<EditText>(R.id.et_quantity)
        val btnAddToCart = dialogView.findViewById<Button>(R.id.btn_add_to_cart)

        btnAddToCart.setOnClickListener {
            val quantity = etQuantity.text.toString().toIntOrNull()

            if (quantity != null ) {
                if (quantity.toInt() > product.stock){
                    Toast.makeText(requireContext(), "Quantity must be less than or equal to stock", Toast.LENGTH_SHORT).show()

                }
                else if (quantity > 0) {
                    val cartProduct = product.toAddCartRequest(session.getUserId(), quantity.toString())
                    Log.d("cartProduct", "showAddToCartDialog: $cartProduct")
                    viewModel.addToCart(cartProduct)
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please enter a valid quantity", Toast.LENGTH_SHORT).show()
                }
            }


        }

        dialog.show()
    }
}


