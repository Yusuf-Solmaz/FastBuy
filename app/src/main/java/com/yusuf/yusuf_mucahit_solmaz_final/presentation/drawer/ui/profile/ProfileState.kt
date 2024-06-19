package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.profile

import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.profile.RootProfile

data class ProfileState(
    val isLoading: Boolean = false,
    val profileResponse: RootProfile? = null,
    val error: String? = null

)