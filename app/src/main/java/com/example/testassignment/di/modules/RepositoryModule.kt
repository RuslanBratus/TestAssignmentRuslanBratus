package com.example.testassignment.di.modules

import com.example.testassignment.data.repository.CardsRepository
import com.example.testassignment.data.repository.TransactionsRepository
import com.example.testassignment.domain.repository.ICardsRepository
import com.example.testassignment.domain.repository.ITransactionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindCardsRepository(repository: CardsRepository): ICardsRepository

    @Binds
    fun bindTransactionsRepository(repository: TransactionsRepository): ITransactionsRepository

}