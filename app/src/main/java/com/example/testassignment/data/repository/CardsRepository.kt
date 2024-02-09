package com.example.testassignment.data.repository

import com.example.testassignment.domain.repository.ICardsRepository
import com.example.testassignment.remote.responsebody.RootCardsResponseBody
import com.example.testassignment.remote.source.cards.ICardsRemoteDataSource
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val cardsRemoteDataSource: ICardsRemoteDataSource
): ICardsRepository {

    override suspend fun getAllCards(): Result<RootCardsResponseBody> =
        cardsRemoteDataSource.getAllCards()

}