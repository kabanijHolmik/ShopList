package com.example.shoplist.domain

class UpdateShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun updateShopItem(shopItem: ShopItem){
        shopListRepository.updateShopItem(shopItem)
    }
}