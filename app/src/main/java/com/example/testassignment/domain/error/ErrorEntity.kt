package com.example.testassignment.domain.error

sealed class ErrorEntity(
    val msg: String?,
    val internalMessage: String = "",
    val code: Int = 0
) {
    class NetworkConnectionError(msg: String) : ErrorEntity(msg)

    class WrongAuthenticationTokenError(msg: String) : ErrorEntity(msg)

    class InternalServerError(msg: String) : ErrorEntity(msg)

    class UserNotExistError(msg: String) : ErrorEntity(msg)

    class AuthFailedError(msg: String) : ErrorEntity(msg)

    class NoLoggedInUserError(msg: String) : ErrorEntity(msg)

    class BadRequestError(msg: String) : ErrorEntity(msg)

    class Unknown(msg: String) : ErrorEntity(msg)
}
