package com.refactorme.demo.data.net

sealed class NetResult<out R> {

    data class Success<out T>(val data: T) : NetResult<T>()
    data class Error(val exception: Exception) : NetResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}