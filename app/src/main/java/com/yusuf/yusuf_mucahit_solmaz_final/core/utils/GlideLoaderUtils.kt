package com.yusuf.yusuf_mucahit_solmaz_final.core.utils

import android.graphics.drawable.Drawable
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.gone
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.ViewUtils.visible


class GlideLoaderUtils : RequestListener<Drawable> {

    private var animationView: LottieAnimationView? = null
    private var targetView: View? = null

    fun with(animationView: LottieAnimationView, targetView: View): GlideLoaderUtils {
        this.animationView = animationView
        this.targetView = targetView
        return this
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>,
        isFirstResource: Boolean
    ): Boolean {
        animationView?.visible()
        targetView?.gone()
        return false
    }

    override fun onResourceReady(
        resource: Drawable,
        model: Any,
        target: Target<Drawable>?,
        dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        animationView?.gone()
        targetView?.visible()
        return false
    }
}

