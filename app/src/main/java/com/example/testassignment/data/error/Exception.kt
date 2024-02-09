package com.example.testassignment.data.error

import com.example.testassignment.remote.dto.ErrorsResponseDTO
import java.io.IOException

object Exceptions {

    class NoResponseContentException : IOException()

    open class NetworkRequestException(val errors: ErrorsResponseDTO) : IOException()

    open class LocalRequestException : IOException()

    class NetworkConnectionException : IOException()

    class BadRequestException(errors: ErrorsResponseDTO) : NetworkRequestException(errors)

    class InternalSererErrorException(errors: ErrorsResponseDTO) : NetworkRequestException(errors)

    class ForbiddenException(errors: ErrorsResponseDTO) : NetworkRequestException(errors)

    class UserNotExistException(errors: ErrorsResponseDTO) : NetworkRequestException(errors)

    class AuthFailedException(errors: ErrorsResponseDTO) : NetworkRequestException(errors)

    class NoLoggedInUserException : LocalRequestException()

    class NoDataException : LocalRequestException()
}