package com.example.testassignment.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testassignment.databinding.FragmentHomeBinding
import com.example.testassignment.extensions.collectWithLifecycle
import com.example.testassignment.extensions.toastThrowableSh
import com.example.testassignment.presentation.core.BaseMVVMFragment
import com.example.testassignment.presentation.home.adapter.HomeCardsAdapter
import com.example.testassignment.presentation.home.adapter.HomeTransactionsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseMVVMFragment<HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), HomeCardsAdapter.IOnCardClickListener, HomeTransactionsAdapter.IOnTransactionClickListener {

    override val viewModel: HomeViewModel by viewModels()

    private val cardsAdapter by lazy {
        HomeCardsAdapter().apply {
            onCardClickListener = this@HomeFragment
        }
    }

    private val transactionsAdapter by lazy {
        HomeTransactionsAdapter().apply {
            onTransactionClickListener = this@HomeFragment
        }
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {
        isViewStateSaveEnabled = true
        setListeners()
        setupAdapter()
    }

    private fun setListeners() {

    }

    override fun initViewModel() {
        viewModel.cardsState.collectWithLifecycle(viewLifecycleOwner) {
            cardsAdapter.submitList(it)
        }
        viewModel.errorState.collectWithLifecycle(viewLifecycleOwner) {
            toastThrowableSh(it)
        }
        viewModel.transactionsState.collectWithLifecycle(viewLifecycleOwner) {
            transactionsAdapter.submitList(it)
        }
    }

    private fun setupAdapter() {
        binding.rvCards.adapter = cardsAdapter
        binding.rvTransactions.adapter = transactionsAdapter
    }

    override fun onCardClick(cardId: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCardDetailsFragment(StringArgCardId = cardId)
        )
    }

    override fun onTransactionClick(transactionId: String) {
        //@TODO wasn't required screen
    }
}