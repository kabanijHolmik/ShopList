package com.example.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val shopItemName: TextView = itemView.findViewById(R.id.textViewName)
    val shopItemCount: TextView = itemView.findViewById(R.id.textViewCount)
}