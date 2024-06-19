package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cart.observe(viewLifecycleOwner, Observer {
            state->
            when {
                state.isLoading -> {
                    Log.d("CartFragment", "Loading")
                }

                state.error != null -> {
                    Log.d("CartFragment", state.error.toString())
                }

                state.cartResponse != null -> {
                    Log.d("CartFragment", "Success")
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserCart()
    }
}
