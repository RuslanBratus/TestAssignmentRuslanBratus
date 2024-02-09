package com.example.testassignment.remote.source.cards

import androidx.annotation.WorkerThread
import com.example.testassignment.remote.responsebody.RootCardsResponseBody

@WorkerThread
interface ICardsRemoteDataSource {

    suspend fun getAllCards(): Result<RootCardsResponseBody>

}