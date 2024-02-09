package com.example.testassignment.remote.source.cards

import com.example.testassignment.extensions.safeApiCall
import com.example.testassignment.remote.BaseDataSource
import com.example.testassignment.remote.responsebody.RootCardsResponseBody
import com.example.testassignment.remote.rest.IRestBuilder
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class CardsRemoteDataSource @Inject constructor(
    apiBuilder: IRestBuilder
): BaseDataSource<CardsRemoteDataSource.API>(apiBuilder),
    ICardsRemoteDataSource {

    override val apiInterface: Class<API>
        get() = API::class.java

    override suspend fun getAllCards(): Result<RootCardsResponseBody> = safeApiCall {
        Result.success(api.getAllCards().body()!!)
    }

    interface API {

        @GET("cards")
        suspend fun getAllCards(): Response<RootCardsResponseBody>

    }

}