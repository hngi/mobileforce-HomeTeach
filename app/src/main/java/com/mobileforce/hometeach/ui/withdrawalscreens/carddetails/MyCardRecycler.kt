package com.mobileforce.hometeach.ui.withdrawalscreens.carddetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.MyCardslistItemBinding
import com.mobileforce.hometeach.ui.withdrawalscreens.MyCard

class MyCardRecycler : ListAdapter<MyCard, MyCardsViewHolder>(
    DiffClass
) {

    companion object DiffClass : DiffUtil.ItemCallback<MyCard>() {
        override fun areItemsTheSame(
            oldItem: MyCard,
            newItem: MyCard
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MyCard,
            newItem: MyCard
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardsViewHolder {
        return MyCardsViewHolder(
            MyCardslistItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MyCardsViewHolder, position: Int) {
        val bank = getItem(position)
        holder.bind(bank)
    }


}

class MyCardsViewHolder(private val binding: MyCardslistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(card: MyCard) {
        //binding.card = card
        binding.executePendingBindings()
    }

}