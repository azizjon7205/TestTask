package uz.frankie.tasktest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.frankie.tasktest.databinding.ItemCardBinding
import uz.frankie.tasktest.models.Card
import java.util.*

class CardsAdapter: ListAdapter<Card, CardsAdapter.ViewHolder>(ITEM_DIFF) {
    var onClick: ((Card) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val card = getItem(adapterPosition)
            with(binding) {
                tvCardName.text = card.cardName.toString()
                tvCardNumber.text = card.cardNumber.toString()
                tvCardBalance.text = "${card.cardBalance.toString()} UZS"
                root.setOnClickListener {
                    onClick?.invoke(card)
                }
            }
        }
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }


}