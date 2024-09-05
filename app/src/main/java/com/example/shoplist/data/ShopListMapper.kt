package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class ShopListMapper {
    fun mapDBModelToEntity(shopItemDBModel: ShopItemDBModel): ShopItem{
        return ShopItem(name =  shopItemDBModel.name,
            count = shopItemDBModel.count,
            isActive = shopItemDBModel.isActive,
            id = shopItemDBModel.id)
    }

    fun mapEntityToDBModel(shopItem: ShopItem): ShopItemDBModel{
        return ShopItemDBModel(name =  shopItem.name,
            count = shopItem.count,
            isActive = shopItem.isActive,
            id = shopItem.id)
    }

    fun mapListDBModelToEntity(shopItemDBModelList: List<ShopItemDBModel>) = shopItemDBModelList.map {
        mapDBModelToEntity(it)
    }
}