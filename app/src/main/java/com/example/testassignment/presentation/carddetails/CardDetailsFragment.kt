package com.example.testassignment.presentation.carddetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.testassignment.R
import com.example.testassignment.databinding.FragmentCardDetailsBinding
import com.example.testassignment.domain.entity.FullCardInfoEntity
import com.example.testassignment.extensions.collectWithLifecycle
import com.example.testassignment.presentation.carddetails.adapter.CardDetailsTransactionsAdapter
import com.example.testassignment.presentation.core.BaseMVVMFragment
import com.example.testassignment.remote.responsebody.TransactionResponseBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsFragment : BaseMVVMFragment<CardDetailsViewModel, FragmentCardDetailsBinding>(
    FragmentCardDetailsBinding::inflate
) {

    override val viewModel: CardDetailsViewModel by viewModels()
    private val navArg : CardDetailsFragmentArgs by navArgs()

    private val transactionsAdapter by lazy {
        CardDetailsTransactionsAdapter()
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {
        viewModel.getCardInfo(navArg.StringArgCardId)
        setupAdapter()
        initListeners()
    }



    override fun initViewModel() {
        viewModel.transactionsState.collectWithLifecycle(viewLifecycleOwner) {
            when (it) {
                is CardDetailsState.CardTransactions -> transactionsAdapter.submitList(it.transactions)
                else -> {}
            }
        }
        viewModel.cardState.collectWithLifecycle(viewLifecycleOwner) {
            binding.tvCardName.text = it.cardName
            binding.tvCardLast4.text = requireContext().getString(R.string.transaction_lst4_placeholder, it.cardLast4)

            Glide
                .with(requireContext())
                .load(it.cardHolder.logoUrl)
                .into(binding.ivCardLogo)
        }
    }

    private fun setupAdapter() {
        binding.rvTransactions.adapter = transactionsAdapter
    }

    private fun initListeners() {
        binding.ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    sealed class CardDetailsState {
        data class CardTransactions(val transactions: List<CardDetailsTransactionsAdapter.TransactionViewType>): CardDetailsState()
        data class Error(val throwable: Throwable?) : CardDetailsState()
        object CardNotFound: CardDetailsState()
        object NoTransactionsFound: CardDetailsState()
    }

}