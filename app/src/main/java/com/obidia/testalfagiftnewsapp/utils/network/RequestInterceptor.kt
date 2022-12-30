package com.obidia.testalfagiftnewsapp.utils.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newReq = chain.request().newBuilder().addHeader(
            "Authorization",
            "044ae03a07274f209732800e024dd153"
        ).build()

        return chain.proceed(newReq)
    }

}