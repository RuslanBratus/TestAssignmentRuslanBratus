package com.example.testassignment.presentation.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testassignment.domain.Resource
import com.example.testassignment.domain.error.ErrorEntity
import com.example.testassignment.domain.usecase.core.EmptyParams
import com.example.testassignment.domain.usecase.core.NoProgressModel
import com.example.testassignment.domain.usecase.core.ProgressiveUseCase

open class BaseViewModel : ViewModel() {

    private val _noInternetConnectionLiveData = EventLiveData<Resource<Unit>>()
    val noInternetConnectionLiveData: LiveData<Resource<Unit>>
        get() = _noInternetConnectionLiveData

    private val _progressLiveData = EventLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    fun showProgress() {
        _progressLiveData.postValue(true)
    }

    fun hideProgress() {
        _progressLiveData.postValue(false)
    }

    suspend fun <R : Any, PR : NoProgressModel, P : EmptyParams> executeUseCase(
        useCase: ProgressiveUseCase<R, PR, P>,
        params: P,
        showProgress: Boolean = true,
        onProgress: suspend (PR) -> Unit = {},
        onResult: suspend (Resource<R>) -> Unit = {}
    ) {
        useCase.execute(params, onProgress) {
            when (it) {
                is Resource.Error -> {
                    hideProgress()
                    when (it.error) {
                        is ErrorEntity.NetworkConnectionError -> {
                            _noInternetConnectionLiveData.postValue(it)
                            onResult(it)
                        }
                        else -> onResult(it)
                    }
                }
                is Resource.Loading -> if (showProgress) showProgress()
                else -> {
                    hideProgress()
                    onResult(it)
                }
            }
        }
    }
}