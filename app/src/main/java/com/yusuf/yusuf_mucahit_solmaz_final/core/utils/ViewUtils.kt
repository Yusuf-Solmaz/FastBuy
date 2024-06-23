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
}