package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import com.yusuf.yusuf_mucahit_solmaz_final.data.mapper.toUpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.RootProfile
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.UpdateUserProfileRequest
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.getUserProfile.GetUserProfileUseCase
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.useCase.updateUserProfile.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
): ViewModel() {

    private val _profile = MutableLiveData<ProfileState>()
    val profile: LiveData<ProfileState> = _profile

    private val _updateProfile = MutableLiveData<UpdateProfileState>()
    val updateProfile: LiveData<UpdateProfileState> = _updateProfile

    fun getUserProfile() {
        _profile.value = ProfileState(isLoading = true)
        viewModelScope.launch {
            getUserProfileUseCase.getUserProfile().collect { result ->
                when (result) {
                    is GeneralResult.Error -> {
                        _profile.postValue(
                            ProfileState(
                                error = result.message,
                                isLoading = false,
                                profileResponse = null
                            )
                        )
                    }

                    GeneralResult.Loading -> {
                        _profile.postValue(
                            ProfileState(
                                isLoading = true,
                                error = null,
                                profileResponse = null
                            )
                        )
                    }

                    is GeneralResult.Success -> {
                        _profile.postValue(
                            ProfileState(
                                isLoading = false,
                                error = null,
                                profileResponse = result.data
                            )
                        )
                    }
                }
            }
        }
    }

    fun updateUserProfile(profileRequest: UpdateUserProfileRequest) {
        _updateProfile.value = UpdateProfileState(isLoading = true)
        viewModelScope.launch {
            updateUserProfileUseCase.updateUserProfile(request = profileRequest)
                .collect { result ->
                    when (result) {
                        is GeneralResult.Error -> {
                            _updateProfile.postValue(
                                UpdateProfileState(
                                    error = result.message,
                                    isLoading = false
                                )
                            )
                        }

                        GeneralResult.Loading -> {
                            _updateProfile.postValue(
                                UpdateProfileState(
                                    isLoading = true
                                )
                            )
                        }

                        is GeneralResult.Success -> {
                            _updateProfile.postValue(
                                UpdateProfileState(
                                    isLoading = false,
                                    error = null,
                                    success = true
                                )
                            )
                        }
                    }
                }
        }
    }
}