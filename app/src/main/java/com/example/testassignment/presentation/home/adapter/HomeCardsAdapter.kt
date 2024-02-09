package com.example.testassignment.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testassignment.databinding.ItemHomeCardBinding
import com.example.testassignment.presentation.home.model.HomeCardUI


class HomeCardsAdapter : ListAdapter<HomeCardUI, HomeCardsAdapter.ItemViewHolder>(DIFF_UTIL) {

    var onCardClickListener: IOnCardClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemHomeCardBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(currentItem: HomeCardUI) {
            with(itemBinding) {
                tvCardLast4.text = currentItem.cardLast4
                tvCardName.text = currentItem.cardName

                if (currentItem.cardHolder.logoUrl.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(currentItem.cardHolder.logoUrl)
                        .into(ivLogo)
                }

                clItemCard.setOnClickListener {
                    onCardClickListener?.onCardClick(currentItem.id)
                }
            }
        }
    }

    interface IOnCardClickListener {
        fun onCardClick(cardId: String)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<HomeCardUI>() {

            override fun areItemsTheSame(
                oldItem: HomeCardUI,
                newItem: HomeCardUI,
            ): Boolean =
                oldItem.id  == newItem.id

            override fun areContentsTheSame(
                oldItem: HomeCardUI,
                newItem: HomeCardUI,
            ): Boolean =
                oldItem == newItem
        }
    }

}