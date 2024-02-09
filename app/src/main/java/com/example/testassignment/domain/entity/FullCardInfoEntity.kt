package com.example.testassignment.domain.entity

import com.example.testassignment.remote.responsebody.CardResponseBody
import com.example.testassignment.remote.responsebody.TransactionResponseBody

data class FullCardInfoEntity(
    val card: CardResponseBody,
    val transactions: List<TransactionResponseBody>?
)