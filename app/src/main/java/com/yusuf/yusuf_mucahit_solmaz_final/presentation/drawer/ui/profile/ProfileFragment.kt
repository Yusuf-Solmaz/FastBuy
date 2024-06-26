package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.setVisibility
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.UpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remoteconfig.RemoteConfigManager.loadBackgroundColor
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.FragmentProfileBinding
import com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private  var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        viewModel.getUserProfile()

        setupObservers()

    }

    private fun setupUI(){
        loadBackgroundColor(requireContext()) {
            color ->
            view?.setBackgroundColor(Color.parseColor(color))
        }
    }

    private fun setupObservers(){

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
                Toast.makeText(requireContext(), requireContext().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
