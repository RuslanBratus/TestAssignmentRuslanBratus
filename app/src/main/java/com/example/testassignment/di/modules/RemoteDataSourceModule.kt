package com.example.testassignment.di.modules

import com.example.testassignment.remote.source.cards.CardsRemoteDataSource
import com.example.testassignment.remote.source.cards.ICardsRemoteDataSource
import com.example.testassignment.remote.source.transactions.ITransactionsRemoteDataSource
import com.example.testassignment.remote.source.transactions.TransactionsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RemoteDataSourceModule {

    @Binds
    fun bindCardsRemoteDataSource(source: CardsRemoteDataSource): ICardsRemoteDataSource

    @Binds
    fun bindTransactionsRemoteDataSource(source: TransactionsRemoteDataSource): ITransactionsRemoteDataSource

}