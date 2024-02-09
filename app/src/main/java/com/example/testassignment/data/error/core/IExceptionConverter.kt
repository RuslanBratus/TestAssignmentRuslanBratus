package com.example.testassignment.data.error.core

interface IExceptionConverter {
    fun getException(e: Exception): Exception
}
