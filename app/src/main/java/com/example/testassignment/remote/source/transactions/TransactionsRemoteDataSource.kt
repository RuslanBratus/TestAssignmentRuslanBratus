package com.example.testassignment.remote.source.transactions

import com.example.testassignment.extensions.safeApiCall
import com.example.testassignment.remote.BaseDataSource
import com.example.testassignment.remote.responsebody.RootTransactionResponseBody
import com.example.testassignment.remote.rest.IRestBuilder
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class TransactionsRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder
): BaseDataSource<TransactionsRemoteDataSource.API>(apiBuilder),
    ITransactionsRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java


    override suspend fun getAllTransactions(): Result<RootTransactionResponseBody> = safeApiCall {
        Result.success(api.getAllTransactions().body()!!)
    }

    interface API {

        @GET("cards/transactions")
        suspend fun getAllTransactions(): Response<RootTransactionResponseBody>

    }


}