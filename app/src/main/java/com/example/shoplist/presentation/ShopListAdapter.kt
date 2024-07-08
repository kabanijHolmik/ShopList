package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(ShopItemDiffCallback()) {


    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopItemName: TextView = itemView.findViewById(R.id.textViewName)
        val shopItemCount: TextView = itemView.findViewById(R.id.textViewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType){
            ENABLED_ITEM -> R.layout.item_enabled
            DISABLED_ITEM -> R.layout.item_disabled
            else -> throw RuntimeException("ГООООООООЛ")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.shopItemName.text = shopItem.name
        holder.shopItemCount.text = shopItem.count.toString()



        holder.itemView.setOnLongClickListener {
            Log.d("Long Click", "Clicked")
            shopItemLongClickListener?.invoke(shopItem)
            true
        }
        
        holder.itemView.setOnClickListener{
            Log.d("Click", "Clicked")
            shopItemClickListener?.invoke(shopItem)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isActive) ENABLED_ITEM
        else DISABLED_ITEM
    }

    companion object{
        const val ENABLED_ITEM = 0
        const val DISABLED_ITEM = 100

        const val MAX_POOL_SIZE = 5
    }

}