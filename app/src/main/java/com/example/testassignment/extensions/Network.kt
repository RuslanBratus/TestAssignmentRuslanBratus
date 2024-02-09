package com.example.testassignment.extensions

import com.example.testassignment.data.error.core.BaseExceptionConverter
import com.example.testassignment.data.error.core.IExceptionConverter

suspend fun <T : Any> safeApiCall(
    converter: IExceptionConverter = BaseExceptionConverter(),
    call: suspend () -> Result<T>
): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(converter.getException(e))
    }
}