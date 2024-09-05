package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList():LiveData<List<ShopItem>>
    suspend fun getShopItem(shopItemId: Int): ShopItem?
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun updateShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
}