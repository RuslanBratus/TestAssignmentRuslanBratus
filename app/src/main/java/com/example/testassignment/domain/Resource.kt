package com.example.testassignment.domain

import com.example.testassignment.domain.error.ErrorEntity


sealed class Resource<out T : Any> {

    object Loading : Resource<Nothing>()

    data class Success<out T : Any>(val data: T) : Resource<T>()

    class Error(val errors: List<ErrorEntity> = emptyList()) : Resource<Nothing>() {
        val error
            get() = errors.firstOrNull()
    }

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            /*is Error -> "Error[exception=$error]"*/
            is Error -> "Error[]"
        }
    }
}