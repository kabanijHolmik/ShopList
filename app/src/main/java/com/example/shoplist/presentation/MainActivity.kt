package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.shopList.observe(this){
            Log.d("MainActivity", "$it")
            val item = it[0]
            if (count == 0){
            mainViewModel.deleteShopItem(item)
            count++
            mainViewModel.changeEnableState(item)
            }
        }
        setContentView(R.layout.activity_main)
    }
}