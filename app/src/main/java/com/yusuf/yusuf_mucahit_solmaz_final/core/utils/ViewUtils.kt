package com.yusuf.yusuf_mucahit_solmaz_final.core.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.R


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


    fun createDialog(context:Context,message:String, successMessage:String, onSuccess:()->Unit){
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .setNegativeButton(context.getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun setupGlide(context: Context, imageUrl:String, productImage: ImageView, loadingAnimationView: LottieAnimationView?){
        Glide.with(context)
            .load(imageUrl)
            .listener(loadingAnimationView?.let { GlideLoaderUtils().with(it, productImage) })
            .into(productImage)
            .clearOnDetach()
    }
}
