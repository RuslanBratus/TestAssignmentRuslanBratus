package com.example.testassignment.domain.repository

import androidx.annotation.WorkerThread
import com.example.testassignment.remote.responsebody.RootTransactionResponseBody

@WorkerThread
interface ITransactionsRepository {

    suspend fun getAllTransactions(): Result<RootTransactionResponseBody>

}