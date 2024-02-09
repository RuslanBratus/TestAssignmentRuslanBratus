package com.example.testassignment.presentation.carddetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testassignment.R
import com.example.testassignment.databinding.ItemCardDetailsDateDividerBinding
import com.example.testassignment.databinding.ItemCardDetailsTransactionBinding
import com.example.testassignment.extensions.convertMoneyToString
import com.example.testassignment.presentation.home.model.TransactionUI

private const val DATE_VIEW_TYPE = 0
private const val TRANSACTION_VIEW_TYPE = 1
private const val SUCCESS_STATUS = "Success"

class CardDetailsTransactionsAdapter : ListAdapter<CardDetailsTransactionsAdapter.TransactionViewType, ViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            DATE_VIEW_TYPE -> return ItemDateViewHolder(ItemCardDetailsDateDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else ->return ItemTransactionViewHolder(ItemCardDetailsTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            when (holder) {
                is ItemTransactionViewHolder -> holder.bind(item)
                is ItemDateViewHolder -> holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TransactionViewType.DateDivider -> DATE_VIEW_TYPE
            is TransactionViewType.Transaction -> TRANSACTION_VIEW_TYPE
        }
    }

    inner class ItemTransactionViewHolder(
        private val itemBinding: ItemCardDetailsTransactionBinding,
    ) : ViewHolder(itemBinding.root) {

        fun bind(currentItem: TransactionViewType) {
            val item = currentItem as TransactionViewType.Transaction
            with(itemBinding) {
                val amount = convertMoneyToString(
                    amount = item.transaction.amount,
                    context = itemBinding.root.context
                )
                if (item.transaction.status == SUCCESS_STATUS) {
                    tvDeclined.visibility = View.GONE
                    vDeclinedTransactionRedCircle.visibility = View.GONE
                }
                else {
                    ivTransactionResult.visibility = View.INVISIBLE
                }
                tvPrice.text = amount
                tvTransactionName.text = item.transaction.card?.cardName
            }
        }
    }

    inner class ItemDateViewHolder(
        private val itemBinding: ItemCardDetailsDateDividerBinding,
    ) : ViewHolder(itemBinding.root) {

        fun bind(currentItem: TransactionViewType) {
            val item = currentItem as TransactionViewType.DateDivider
            with(itemBinding) {
                tvDate.text = item.date
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<TransactionViewType>() {

            override fun areItemsTheSame(
                oldItem: TransactionViewType,
                newItem: TransactionViewType,
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: TransactionViewType,
                newItem: TransactionViewType,
            ): Boolean =
                oldItem == newItem
        }
    }

    sealed class TransactionViewType {
        data class DateDivider(val date: String): TransactionViewType()
        data class Transaction(val transaction: TransactionUI): TransactionViewType()
    }

}
