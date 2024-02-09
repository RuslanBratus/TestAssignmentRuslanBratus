package com.example.testassignment.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignment.R
import com.example.testassignment.databinding.ItemHomeTransactionBinding
import com.example.testassignment.extensions.convertMoneyToString
import com.example.testassignment.presentation.home.model.TransactionUI


class HomeTransactionsAdapter : ListAdapter<TransactionUI, HomeTransactionsAdapter.ItemViewHolder>(DIFF_UTIL) {

    var onTransactionClickListener: IOnTransactionClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemHomeTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemHomeTransactionBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(currentItem: TransactionUI) {
            with(itemBinding) {
                val amount = convertMoneyToString(
                    amount = currentItem.amount,
                    context = itemBinding.root.context
                )
                tvAmount.text = amount
                tvCardLast4.text = itemView.context.getString(R.string.transaction_lst4_placeholder, currentItem.card?.cardLast4 ?: "")
                tvCardName.text = currentItem.card?.cardName
                ivTransactionResultIcon.setImageDrawable(
                    if (currentItem.status == "Success") AppCompatResources.getDrawable(itemView.context, R.drawable.ic_successful_transaction)
                    else null
                )

                if (currentItem.card != null && currentItem.card.cardHolder.logoUrl.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(currentItem.card.cardHolder.logoUrl)
                        .into(ivLogo)
                }

                clItemCard.setOnClickListener {
                    onTransactionClickListener?.onTransactionClick(currentItem.id)
                }
            }
        }
    }

    interface IOnTransactionClickListener {
        fun onTransactionClick(transactionId: String)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<TransactionUI>() {

            override fun areItemsTheSame(
                oldItem: TransactionUI,
                newItem: TransactionUI,
            ): Boolean =
                oldItem.id  == newItem.id

            override fun areContentsTheSame(
                oldItem: TransactionUI,
                newItem: TransactionUI,
            ): Boolean =
                oldItem == newItem
        }
    }

}