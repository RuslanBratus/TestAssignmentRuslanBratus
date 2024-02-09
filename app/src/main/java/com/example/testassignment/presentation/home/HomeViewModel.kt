package com.example.testassignment.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.testassignment.domain.repository.ICardsRepository
import com.example.testassignment.domain.repository.ITransactionsRepository
import com.example.testassignment.presentation.core.BaseViewModel
import com.example.testassignment.presentation.home.model.HomeCardUI
import com.example.testassignment.presentation.home.model.TransactionUI
import com.example.testassignment.presentation.mapper.toHomeCardsUI
import com.example.testassignment.presentation.mapper.toTransactionUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardsRepository: ICardsRepository,
    private val transactionsRepository: ITransactionsRepository
) : BaseViewModel() {

    private val _cardsState = MutableSharedFlow<List<HomeCardUI>>()
    val cardsState = _cardsState.asSharedFlow()
    private val _transactionsState = MutableSharedFlow<List<TransactionUI>>()
    val transactionsState = _transactionsState.asSharedFlow()

    private val _errorState = MutableSharedFlow<Throwable>()
    val errorState = _errorState.asSharedFlow()


    init {
        viewModelScope.launch {
            getAllCards()
            getAllTransactions()
        }
    }

    private suspend fun getAllCards() {
        val result = cardsRepository.getAllCards()

        result.onSuccess {
            _cardsState.emit(it.toHomeCardsUI())
        }

        result.onFailure {
            _errorState.emit(it)
        }
    }

    private suspend fun getAllTransactions() {
        val result = transactionsRepository.getAllTransactions()

        result.onSuccess { rootTransaction ->
            rootTransaction.transactions.forEach {
                Log.e("WatchingSomeStuff", "transaction = $it")
                Log.e("WatchingSomeStuff", "transaction.card = ${it.card}")
            }
            _transactionsState.emit(
                rootTransaction.transactions.take(3).map {
                    it.toTransactionUI()
                }
            )
        }

        result.onFailure {
            _errorState.emit(it)
        }
    }
}