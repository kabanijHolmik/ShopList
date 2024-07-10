package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList():LiveData<List<ShopItem>>
    fun getShopItem(shopItemId: Int): ShopItem?
    fun addShopItem(shopItem: ShopItem)
    fun updateShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}