package ru.sharipov.podcaster.i_listen.auth

import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor: Interceptor {

    private companion object {
        const val API_KEY = "f8652f7dac814fa5bd5d8e0676e41a00"
        const val API_HEADER = "X-ListenAPI-Key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(API_HEADER, API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}