package com.example.smartexpensetracker.ui.expenselist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartexpensetracker.R
import com.example.smartexpensetracker.data.model.Expense
import java.time.format.DateTimeFormatter

class ExpenseAdapter : ListAdapter<Expense, ExpenseAdapter.VH>(DIFF) {
    object DIFF : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Expense, newItem: Expense) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.tvTitle)
        private val meta = itemView.findViewById<TextView>(R.id.tvMeta)
        private val iv = itemView.findViewById<ImageView>(R.id.ivThumb)
        private val fmt = DateTimeFormatter.ofPattern("HH:mm")
        fun bind(e: Expense) {
            title.text = e.title
            meta.text = "₹${e.amount.toInt()} • ${e.category} • ${e.createdAt.format(fmt)}"
            if (e.receiptUri != null) iv.setImageURI(e.receiptUri) else iv.setImageResource(android.R.drawable.ic_menu_report_image)
        }
    }
}