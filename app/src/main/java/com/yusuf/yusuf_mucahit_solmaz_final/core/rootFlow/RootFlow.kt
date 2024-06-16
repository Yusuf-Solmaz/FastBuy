package com.yusuf.yusuf_mucahit_solmaz_final.core.rootFlow

import com.yusuf.yusuf_mucahit_solmaz_final.core.AppResult.GeneralResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> rootFlow(call: suspend () -> Response<T>?): Flow<GeneralResult<T>> = flow {
    emit(GeneralResult.Loading)
    try {
        val c = call()
        c?.let {
            if (c.isSuccessful) {
                it.body()?.let {
                    emit(GeneralResult.Success(c.body()))
                }
            } else {
                emit(GeneralResult.Error(c.errorBody()?.toString() ?: "Something went wrong"))
            }
        }
    }
    catch (e:Exception){
        emit(GeneralResult.Error(e.message ?: "Something went wrong"))
    }
}.flowOn(Dispatchers.IO)