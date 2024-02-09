package com.example.testassignment.presentation.home.model

data class HomeCardUI(
    val id: String,
    val cardLast4: String,
    val cardName: String,
    val isLocked: Boolean,
    val isTerminated: Boolean,
    val spent: Long,
    val limit: Long,
    val limitType: String,
    val cardHolder: CardHolderUI,
    val fundingSource: String,
    val issuedAt: String,
)

data class CardHolderUI(
    val id: String,
    val fullName: String,
    val email: String,
    val logoUrl: String,
)