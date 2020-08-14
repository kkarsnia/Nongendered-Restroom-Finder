package com.kkco.nongenderedrestroomfinder.api

import android.util.Log
import com.kkco.nongenderedrestroomfinder.data.Result
import retrofit2.Response
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("BaseDataSource", "body: $body")
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Timber.e(message)
        Log.d("BaseDataSource", "Network call failed because: $message")
        return Result.error("Network call has failed for a following reason: $message")
    }
}