package com.example.shoplist.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        buttonAddItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
        setupRecyclerView()

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.shopList.observe(this){
            adapter.submitList(it)
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
                val item = adapter.currentList[position]
                mainViewModel.deleteShopItem(shopItem = item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupClickListener() {
        adapter.shopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }

    }

    private fun setupLongClickListener() {
        adapter.shopItemLongClickListener = {
            mainViewModel.changeEnableState(it)
        }
    }

}