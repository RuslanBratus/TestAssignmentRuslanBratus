package com.example.testassignment.remote.source.transactions

import androidx.annotation.WorkerThread
import com.example.testassignment.remote.responsebody.RootTransactionResponseBody

@WorkerThread
interface ITransactionsRemoteDataSource {

    suspend fun getAllTransactions(): Result<RootTransactionResponseBody>

}