package com.example.testassignment.remote.responsebody

import com.google.gson.annotations.SerializedName

data class RootCardsResponseBody(
    @SerializedName("cards") val cards: List<CardResponseBody>,
)

data class CardResponseBody(
    @SerializedName("id") val id: String,
    @SerializedName("cardLast4") val cardLast4: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("isLocked") val isLocked: Boolean,
    @SerializedName("isTerminated") val isTerminated: Boolean,
    @SerializedName("spent") val spent: Long,
    @SerializedName("limit") val limit: Long,
    @SerializedName("limitType") val limitType: String,
    @SerializedName("cardHolder") val cardHolder: CardHolder,
    @SerializedName("fundingSource") val fundingSource: String,
    @SerializedName("issuedAt") val issuedAt: String,
)

data class CardHolder(
    @SerializedName("id") val id: String,
    @SerializedName("fullName") val fullName: String,
    @SerializedName("email") val email: String,
    @SerializedName("logoUrl") val logoUrl: String,
)