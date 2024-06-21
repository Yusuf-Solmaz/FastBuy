package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart

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
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentCartBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.adapter.CartAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBackgroundColor(requireContext()){
                color->
            view.setBackgroundColor(Color.parseColor(color))
        }

        viewModel.getUserCart()

         cartAdapter = CartAdapter(arrayListOf(), requireContext())
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        viewModel.cart.observe(viewLifecycleOwner, Observer { state ->
            try {
                when {
                    state.isLoading -> {
                        binding.loadingAnimation.visibility = View.VISIBLE
                        binding.errorMessage.visibility = View.GONE
                        binding.rvCart.visibility = View.GONE

                    }
                    state.error != null -> {
                        binding.loadingAnimation.visibility = View.GONE
                        binding.errorMessage.visibility = View.VISIBLE
                        binding.errorMessage.text = state.error
                        binding.rvCart.visibility = View.GONE
                    }
                    state.cartResponse != null -> {
                        binding.loadingAnimation.visibility = View.GONE
                        binding.errorMessage.visibility = View.GONE
                        binding.rvCart.visibility = View.VISIBLE
                        try {
                            val carts = state.cartResponse.carts
                            if (carts.isNotEmpty()) {
                                binding.errorEmptyText.visibility = View.GONE
                                cartAdapter.updateProducts(carts[0].products)
                                binding.totalPrice.text = "Total: ${carts[0].discountedTotal}$"

                                binding.discountedPrice.paintFlags = binding.discountedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                                binding.discountedPrice.text = "${carts[0].total}$"
                            } else {
                               binding.errorEmptyText.visibility = View.VISIBLE
                                Log.e("CartFragment", "Carts list is  empty.")
                            }
                        } catch (e: Exception) {
                            Log.e("CartFragment", "Error updating cart adapter: ${e.message}")
                        }

                        binding.payButton.setOnClickListener {
                            showConfirmationDialog()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("CartFragment", "Error observing cart state: ${e.message}")
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserCart()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Do you want to proceed with the transaction?")
            .setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(requireContext(), "Transaction successful", Toast.LENGTH_SHORT).show()
                val action = CartFragmentDirections.actionNavCartToNavHome()
                findNavController().navigate(action)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
