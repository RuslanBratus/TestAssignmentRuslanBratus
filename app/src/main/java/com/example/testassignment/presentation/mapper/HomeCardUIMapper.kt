package com.example.testassignment.presentation.mapper

import com.example.testassignment.presentation.home.model.CardHolderUI
import com.example.testassignment.presentation.home.model.HomeCardUI
import com.example.testassignment.remote.responsebody.CardHolder
import com.example.testassignment.remote.responsebody.CardResponseBody
import com.example.testassignment.remote.responsebody.RootCardsResponseBody

fun RootCardsResponseBody.toHomeCardsUI() =
    this.cards.map {
        it.toHomeCardUI()
    }


fun CardResponseBody.toHomeCardUI() =
    HomeCardUI(
        id = id,
        cardLast4 = cardLast4,
        cardName = cardName,
        isLocked = isLocked,
        isTerminated = isTerminated,
        spent = spent,
        limit = limit,
        limitType = limitType,
        cardHolder = cardHolder.toCardHolderUI(),
        fundingSource = fundingSource,
        issuedAt = issuedAt

    )

fun CardHolder.toCardHolderUI() =
    CardHolderUI(
        email = email,
        fullName = fullName,
        id = id,
        logoUrl = logoUrl
    )