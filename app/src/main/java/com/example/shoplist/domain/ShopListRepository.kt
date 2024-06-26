package com.example.shoplist.domain

interface ShopListRepository {
    fun getShopList():List<ShopItem>
    fun getShopItem(shopItemId: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun updateShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}