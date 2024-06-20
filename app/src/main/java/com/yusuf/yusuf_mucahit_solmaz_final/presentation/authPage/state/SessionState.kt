package com.yusuf.yusuf_mucahit_solmaz_final.presentation.authPage.state

import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.model.CurrentUser

data class SessionState(
    val user: CurrentUser? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)