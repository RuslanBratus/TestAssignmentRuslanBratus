package com.example.testassignment.data.error.core

import com.example.testassignment.data.error.Exceptions
import javax.inject.Inject

open class BaseExceptionConverter @Inject constructor(): IExceptionConverter {

    override fun getException(e: Exception): Exception = when (e) {
        is Exceptions.NetworkRequestException -> {
            when (e.errors.status) {
                BAD_REQUEST_ERROR_CODE_400 -> Exceptions.BadRequestException(e.errors)
                UNAUTHORIZED_ERROR_CODE_401 -> Exceptions.AuthFailedException(e.errors)
                FORBIDDEN_ERROR_CODE_403 -> Exceptions.ForbiddenException(e.errors)
                INTERNAL_SERVER_ERROR_500 -> Exceptions.InternalSererErrorException(e.errors)
                else -> e
            }
        }
        else -> e
    }

    companion object {

        const val BAD_REQUEST_ERROR_CODE_400 = 400
        const val UNAUTHORIZED_ERROR_CODE_401 = 401
        const val FORBIDDEN_ERROR_CODE_403 = 403
        const val NOT_FOUND_ERROR_CODE_404 = 404
        const val CONFLICT_ERROR_CODE_409 = 409
        const val INTERNAL_SERVER_ERROR_500 = 500
    }
}
