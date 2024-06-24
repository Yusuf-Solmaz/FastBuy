package com.yusuf.yusuf_mucahit_solmaz_final.core.utils

import android.view.View

object ViewUtils {
    fun View.invisible(){
        visibility = View.INVISIBLE
    }

    fun View.visible(){
        visibility = View.VISIBLE
    }

    fun View.gone(){
        visibility = View.GONE
    }

    fun setVisibility(
        isLoading: Boolean,
        isError: Boolean,
        isSuccess: Boolean,
        loadingView: View,
        errorView: View,
        successView: View
    ) {
        if (isLoading) {

            loadingView.visible()
            errorView.gone()
            successView.gone()
        }
        else if (isError) {

            loadingView.gone()
            errorView.visible()
            successView.gone()
        }
        else if (isSuccess) {
            loadingView.gone()
            errorView.gone()
            successView.visible()
        }
    }
}
