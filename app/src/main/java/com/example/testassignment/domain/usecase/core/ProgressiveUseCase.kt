package com.example.testassignment.domain.usecase.core

import com.example.testassignment.domain.Resource

abstract class ProgressiveUseCase<out R : Any, out PR : NoProgressModel, in P : EmptyParams> {

    abstract suspend fun run(params: P, onProgress: suspend (PR) -> Unit = {}): Resource<R>

    suspend fun execute(
        params: P,
        onProgress: suspend (PR) -> Unit = {},
        onResult: suspend (Resource<R>) -> Unit
    ) {
        onResult(Resource.Loading)
        onResult(run(params, onProgress))
    }
}