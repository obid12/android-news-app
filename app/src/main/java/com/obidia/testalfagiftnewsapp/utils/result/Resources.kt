package com.obidia.testalfagiftnewsapp.utils.result

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
    object Loading: ResponseResult<Nothing>()
}

inline infix fun <T> ResponseResult<T>.loading(predicate: () -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Loading) {
        predicate.invoke()
    }
    return this
}

inline infix fun <T> ResponseResult<T>.success(predicate: (data: T) -> Unit): ResponseResult<T> {
    if (this is ResponseResult.Success && this.data != null) {
        predicate.invoke(this.data)
    }
    return this
}

inline infix fun <T> ResponseResult<T>.error(predicate: (data: Throwable) -> Unit) {
    if (this is ResponseResult.Error) {
        predicate.invoke(this.throwable)
    }
}
