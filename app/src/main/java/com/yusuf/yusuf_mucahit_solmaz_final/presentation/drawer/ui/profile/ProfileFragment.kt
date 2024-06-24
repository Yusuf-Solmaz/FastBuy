package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile

import android.graphics.Color
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
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.UpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.updateUI
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

        loadBackgroundColor(requireContext()) { color ->
            view.setBackgroundColor(Color.parseColor(color))
        }

        viewModel.getUserProfile()



        viewModel.profile.observe(viewLifecycleOwner) { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.profileResponse != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.profileLayout
            )

            if (state.profileResponse != null) {
                binding.name.text = Editable.Factory.getInstance().newEditable(state.profileResponse.firstName)
                binding.lastName.text = Editable.Factory.getInstance().newEditable(state.profileResponse.lastName)
                binding.email.text = Editable.Factory.getInstance().newEditable(state.profileResponse.email)
                binding.phoneNumber.text = Editable.Factory.getInstance().newEditable(state.profileResponse.phone)
                binding.password.text = Editable.Factory.getInstance().newEditable(state.profileResponse.password)

                binding.updateBtn.setOnClickListener(::updateUserInformation)
            }
        }

        viewModel.updateProfile.observe(viewLifecycleOwner) { state ->

            setVisibility(
                isLoading = state.isLoading,
                isError = state.error != null,
                isSuccess = state.success != null,
                loadingView = binding.profileLoadingErrorComponent.loadingLayout,
                errorView = binding.profileLoadingErrorComponent.errorLayout,
                successView = binding.profileLayout
            )

            if (state.success != null) {
                Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
                val action = ProfileFragmentDirections.actionNavProfileToNavHome()
                findNavController().navigate(action)
            }
        }
    }

    private fun updateUserInformation(view: View){

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
