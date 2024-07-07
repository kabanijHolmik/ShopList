package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.shopList.observe(this){
            adapter.shopList = it
        }

    }

    private fun setupRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ShopListAdapter()
        recyclerView.adapter = adapter
        recyclerView.recycledViewPool.setMaxRecycledViews(R.layout.item_enabled, ShopListAdapter.MAX_POOL_SIZE)
        recyclerView.recycledViewPool.setMaxRecycledViews(R.layout.item_disabled, ShopListAdapter.MAX_POOL_SIZE)

        setupLongClickListener()

        setupClickListener()

        setupSwipeListener(recyclerView)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.shopList[position]
                mainViewModel.deleteShopItem(shopItem = item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupClickListener() {
        adapter.shopItemClickListener = {
            Log.d("shopItemClickListener", "Item: $it")
        }
    }

    private fun setupLongClickListener() {
        adapter.shopItemLongClickListener = {
            mainViewModel.changeEnableState(it)
        }
    }

}