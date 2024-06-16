package com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult


sealed class GeneralResult<out R> {
    data class Success<out R>(val data: R?) : GeneralResult<R>()
    data class Error(val message:String) : GeneralResult<Nothing>()
    data object Loading : GeneralResult<Nothing>()
}