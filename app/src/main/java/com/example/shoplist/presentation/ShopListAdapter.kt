package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var countCreateViewHolder = 0
    private var countBindViewHolder = 0

    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shopItemName: TextView = itemView.findViewById(R.id.textViewName)
        val shopItemCount: TextView = itemView.findViewById(R.id.textViewCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("shopListAdapter", "onCreateViewHolder, count: ${++countCreateViewHolder}")

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

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("shopListAdapter", "onBindViewHolder, count: ${++countBindViewHolder}")

        val shopItem = shopList[position]
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
        return if(shopList[position].isActive) ENABLED_ITEM
        else DISABLED_ITEM
    }

    companion object{
        const val ENABLED_ITEM = 0
        const val DISABLED_ITEM = 100

        const val MAX_POOL_SIZE = 5
    }

}