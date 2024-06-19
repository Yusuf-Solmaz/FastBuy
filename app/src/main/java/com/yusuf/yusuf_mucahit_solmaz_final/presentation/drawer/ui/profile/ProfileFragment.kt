package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.UpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserProfile()

        viewModel.profile.observe(viewLifecycleOwner) { state ->
            when {
                state.isLoading -> {

                }

                state.error != null -> {

                }

                state.profileResponse != null -> {

                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.name.text = Editable.Factory.getInstance().newEditable(state.profileResponse.firstName)
                    binding.lastName.text = Editable.Factory.getInstance().newEditable(state.profileResponse.lastName)
                    binding.email.text = Editable.Factory.getInstance().newEditable(state.profileResponse.email)
                    binding.phoneNumber.text = Editable.Factory.getInstance().newEditable(state.profileResponse.phone)
                    binding.password.text = Editable.Factory.getInstance().newEditable(state.profileResponse.password)

                    binding.updateBtn.setOnClickListener {
                        val request = UpdateUserProfileRequest(
                            name = binding.name.text.toString(),
                            lastName = binding.lastName.text.toString(),
                            email = binding.email.text.toString(),
                            phone = binding.phoneNumber.text.toString(),
                            password = binding.password.text.toString()
                        )
                        viewModel.updateUserProfile(request)
                    }
                }
            }
        }

        viewModel.updateProfile.observe(viewLifecycleOwner, Observer { state ->
            when {
                state.isLoading -> {

                }
                state.error != null -> {

                }
                state.success != null -> {
                    Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
                    val action =  ProfileFragmentDirections.actionNavProfileToNavHome()
                    findNavController().navigate(action)
                }

            }
        })
    }
}