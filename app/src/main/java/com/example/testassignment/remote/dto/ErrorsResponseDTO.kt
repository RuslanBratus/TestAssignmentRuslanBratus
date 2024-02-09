package com.example.testassignment.remote.dto

data class ErrorsResponseDTO(
    val status: Int,
    val errors: List<ServerErrorResponseDTO>?
)