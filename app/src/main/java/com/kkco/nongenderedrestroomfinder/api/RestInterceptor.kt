package com.kkco.nongenderedrestroomfinder.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO: implement private auth interceptor
        val request = chain.request().newBuilder().build()
        return chain.proceed(request)
    }
}