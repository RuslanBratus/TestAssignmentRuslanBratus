package com.example.testassignment.presentation.carddetails

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.viewModelScope
import com.example.testassignment.domain.repository.ICardsRepository
import com.example.testassignment.domain.repository.ITransactionsRepository
import com.example.testassignment.presentation.carddetails.adapter.CardDetailsTransactionsAdapter
import com.example.testassignment.presentation.core.BaseViewModel
import com.example.testassignment.presentation.home.model.CardUI
import com.example.testassignment.presentation.mapper.toCardUI
import com.example.testassignment.presentation.mapper.toTransactionUI
import com.example.testassignment.remote.responsebody.TransactionResponseBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val cardsRepository: ICardsRepository,
    private val transactionsRepository: ITransactionsRepository
): BaseViewModel() {

    private val _transactionsState = MutableSharedFlow<CardDetailsFragment.CardDetailsState>()
    val transactionsState = _transactionsState.asSharedFlow()
    private val _cardState = MutableSharedFlow<CardUI>()
    val cardState = _cardState.asSharedFlow()

    fun getCardInfo(cardId: String?) {
        viewModelScope.launch {
            if (cardId.isNullOrEmpty()) {
                _transactionsState.emit(CardDetailsFragment.CardDetailsState.CardNotFound)
            }
            else {
                val cards = cardsRepository.getAllCards()
                cards.onSuccess { cardsBody ->
                    val card = cardsBody.cards.find { it.id == cardId }
                    if (card == null) {
                        _transactionsState.emit(CardDetailsFragment.CardDetailsState.CardNotFound)
                        return@launch
                    }
                    _cardState.emit(card.toCardUI())

                    val allTransactions = transactionsRepository.getAllTransactions()
                    allTransactions.onSuccess { transactionsBody ->
                        val cardsTransactions = transactionsBody.transactions.filter {
                            it.card?.id == cardId
                        }
                        if (cardsTransactions.isEmpty()) {
                            _transactionsState.emit(CardDetailsFragment.CardDetailsState.NoTransactionsFound)
                            return@launch
                        }

                        val finalTransactions = convertTransactionsByTypes(cardsTransactions)
                        _transactionsState.emit(
                            CardDetailsFragment.CardDetailsState.CardTransactions(finalTransactions)
                        )
                    }
                    allTransactions.onFailure {
                        _transactionsState.emit(
                            CardDetailsFragment.CardDetailsState.NoTransactionsFound
                        )
                    }
                }
                cards.onFailure {
                    _transactionsState.emit(CardDetailsFragment.CardDetailsState.Error(it))
                }
            }
        }
    }

    private fun convertTransactionsByTypes(cardsTransactions: List<TransactionResponseBody>): List<CardDetailsTransactionsAdapter.TransactionViewType> {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
        val outputFormatter: DateFormat = SimpleDateFormat("MMM dd", Locale.US)
        val sorted = cardsTransactions.sortedByDescending {
            inputFormatter.parse(it.createDate)
        }
        val transactionViewTypesList = mutableListOf<CardDetailsTransactionsAdapter.TransactionViewType>()

        sorted.forEachIndexed { index, transactionResponseBody ->
            val currentParsedDate = inputFormatter.parse(sorted[index].createDate)
            transactionViewTypesList.add(CardDetailsTransactionsAdapter.TransactionViewType.Transaction(transactionResponseBody.toTransactionUI()))

            if (index != sorted.size-1 && currentParsedDate.time > inputFormatter.parse(sorted[index+1].createDate).time
            ) transactionViewTypesList.add(CardDetailsTransactionsAdapter.TransactionViewType.DateDivider(outputFormatter.format(currentParsedDate)))
        }
        return transactionViewTypesList
    }


}