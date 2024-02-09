package com.example.testassignment.data.repository

import com.example.testassignment.domain.repository.ITransactionsRepository
import com.example.testassignment.remote.responsebody.RootTransactionResponseBody
import com.example.testassignment.remote.source.transactions.ITransactionsRemoteDataSource
import javax.inject.Inject

//Will be much more better to use pagination, but no possible due to backend conditions

class TransactionsRepository @Inject constructor(
    private val transactionsRemoteDataSource: ITransactionsRemoteDataSource
): ITransactionsRepository {

    override suspend fun getAllTransactions(): Result<RootTransactionResponseBody> =
        transactionsRemoteDataSource.getAllTransactions()

}