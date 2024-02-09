package com.example.testassignment.domain.error

interface IErrorHandler {

    fun getError(tr: Throwable): List<ErrorEntity>
}