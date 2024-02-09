package com.example.testassignment.domain.error

import android.content.Context
import com.example.testassignment.R
import com.example.testassignment.data.error.Exceptions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorHandler @Inject constructor(@ApplicationContext context: Context) : IErrorHandler {

    private val resources by lazy { context.resources }

    override fun getError(tr: Throwable): List<ErrorEntity> {
        return when (tr) {
            is Exceptions.BadRequestException -> tr.errors.errors?.map {
                ErrorEntity.BadRequestError(
                    it.message ?: resources.getString(R.string.error_bad_request)
                )
            } ?: listOf(ErrorEntity.Unknown(resources.getString(R.string.error_unknown)))

            is Exceptions.NetworkConnectionException ->
                listOf(ErrorEntity.NetworkConnectionError(resources.getString(R.string.error_internet_connection)))

            is Exceptions.InternalSererErrorException -> tr.errors.errors?.map {
                ErrorEntity.InternalServerError(
                    it.message ?: resources.getString(R.string.error_internal_server_error)
                )
            } ?: listOf(ErrorEntity.Unknown(resources.getString(R.string.error_unknown)))

            else -> listOf(
                ErrorEntity.Unknown(resources.getString(R.string.error_unknown))
            )
        }
    }
}