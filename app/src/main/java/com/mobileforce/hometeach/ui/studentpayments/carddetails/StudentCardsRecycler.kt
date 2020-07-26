package com.mobileforce.hometeach.ui.studentpayments.carddetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.local.entities.CardEntity
import com.mobileforce.hometeach.databinding.StudentsCardListBinding

class StudentCardsRecycler : ListAdapter<CardEntity, StudentCardsHolder>(
    DiffClass
) {

    private lateinit var onClickListener: View.OnClickListener

    fun setOnclickListener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    companion object DiffClass : DiffUtil.ItemCallback<CardEntity>() {
        override fun areItemsTheSame(
            oldItem: CardEntity,
            newItem: CardEntity
        ): Boolean {
            return oldItem.card_number == newItem.card_number
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CardEntity,
            newItem: CardEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentCardsHolder {
        return StudentCardsHolder(
            StudentsCardListBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: StudentCardsHolder, position: Int) {
        val cardDetailResponse = getItem(position)
        holder.bind(cardDetailResponse)
        holder.itemView.setOnClickListener(onClickListener)
    }
}

class StudentCardsHolder(private val binding: StudentsCardListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.tag = this
    }

    @SuppressLint("SetTextI18n")
    fun bind(cardDetailResponse: CardEntity) {
        if (cardDetailResponse.card_number.startsWith("4")) {
            binding.ivCardIcon.setImageResource(R.drawable.ic_visa)
        } else {
            binding.ivCardIcon.setImageResource(R.drawable.ic_master)
        }
        binding.tvCardNumber.text =
            cardDetailResponse.card_number //binding.root.context.getString(R.string.ellipsis) + .substring(12, 16)
        //binding.rbSelectCard.visibility = View.INVISIBLE
    }

}