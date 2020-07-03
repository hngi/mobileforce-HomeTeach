package com.mobileforce.hometeach.ui.studentpayments.makepayments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobileforce.hometeach.databinding.StudentsCardListBinding
import com.mobileforce.hometeach.ui.studentpayments.StudentCard

class StudentCardsRecycler : ListAdapter<StudentCard, StudentCardsHolder>(
    DiffClass
) {

    companion object DiffClass : DiffUtil.ItemCallback<StudentCard>() {
        override fun areItemsTheSame(
            oldItem: StudentCard,
            newItem: StudentCard
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StudentCard,
            newItem: StudentCard
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
        val card = getItem(position)
        holder.bind(card)
    }


}

class StudentCardsHolder(private val binding: StudentsCardListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(card: StudentCard) {
        binding.card = card
        binding.executePendingBindings()
    }

}