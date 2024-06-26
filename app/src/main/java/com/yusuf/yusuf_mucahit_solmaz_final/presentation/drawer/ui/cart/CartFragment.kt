package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
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
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.createDialog
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentCartBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.adapter.CartAdapter
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserCart()

        setupUI()
        setupObservers()

    }

    private fun setupUI() {

        loadBackgroundColor(requireContext()){
            color->
            view?.setBackgroundColor(Color.parseColor(color))
        }

        cartAdapter = CartAdapter(arrayListOf(), requireContext())
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.cart.observe(viewLifecycleOwner, Observer { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.cartResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.cartLayout
            )

            if (state.cartResponse != null){
                totalPrice = state.cartResponse.total

                try {
                    val carts = state.cartResponse.carts
                    if (carts.isNotEmpty()) {
                        binding.errorEmptyText.visibility = View.GONE
                        cartAdapter.updateProducts(carts[0].products)
                        binding.totalPrice.text = "${requireContext().getString(R.string.total)}: ${carts[0].discountedTotal}$"

                        binding.discountedPrice.paintFlags = binding.discountedPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        binding.discountedPrice.text = "${carts[0].total}$"
                    } else {
                        binding.errorEmptyText.visibility = View.VISIBLE

                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.update_cart_error), Toast.LENGTH_SHORT).show()
                }


                binding.payButton.setOnClickListener(::showConfirmationDialog)

                binding.icDelete.setOnClickListener(::showDeleteCartConfirmationDialog)
            }

        })

        viewModel.deleteCart.observe(viewLifecycleOwner, Observer { state ->
            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.success,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.cartLayout
            )

        })
    }

    private fun showDeleteCartConfirmationDialog(view: View?) {
        createDialog(requireContext(), requireContext().getString(R.string.delete_cart_confirmation),requireContext().getString(R.string.delete_cart_confirmation_message)){
            val action = CartFragmentDirections.actionNavCartToNavHome()
            findNavController().navigate(action)
        }
    }

    private fun showConfirmationDialog(view: View?) {
        if (totalPrice >0){
            createDialog(requireContext(), requireContext().getString(R.string.pay_confirmation),requireContext().getString(R.string.pay_confirmation_message)){
                val action = CartFragmentDirections.actionNavCartToNavHome()
                findNavController().navigate(action)
            }
        }
        else{
            Toast.makeText(requireContext(), requireContext().getString(R.string.empty_cart_error), Toast.LENGTH_SHORT).show()
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.getUserCart()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
