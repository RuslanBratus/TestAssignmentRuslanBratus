package com.example.testassignment.remote.dto

data class ServerErrorResponseDTO(
    val code: String,
    val message: String?,
    val payload: String?
)