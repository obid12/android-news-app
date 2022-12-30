package com.obidia.testalfagiftnewsapp.utils.result

//sealed class Resources<out T : Any, out U : Any> {
//    data class Success<T : Any>(val data: T) : Resources<T, Nothing>()
//    data class Error<T : Any, U : Any>(val data: T? = null, val response: U) : Resources<T, U>()
//}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
