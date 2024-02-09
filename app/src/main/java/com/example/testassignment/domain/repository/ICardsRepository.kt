package com.example.testassignment.domain.repository

import androidx.annotation.WorkerThread
import com.example.testassignment.remote.responsebody.RootCardsResponseBody

@WorkerThread
interface ICardsRepository {

    suspend fun getAllCards(): Result<RootCardsResponseBody>

}