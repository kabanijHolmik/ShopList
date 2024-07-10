package com.example.shoplist.data

import androidx.core.os.BuildCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import kotlin.jvm.Throws

object ShopListRepositoryImpl: ShopListRepository {
    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0..121){
            shopList.add(ShopItem("object $i", 1, i%2 == 1, i))
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        updateShopListLiveData()
        return shopListLiveData
    }

    override fun getShopItem(shopItemId: Int): ShopItem? {
        return shopList.find { it.id == shopItemId }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
        shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateShopListLiveData()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopListLiveData()
    }

    override fun updateShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        oldShopItem?.let { deleteShopItem(it) }
        addShopItem(shopItem)
        updateShopListLiveData()
    }

    private fun updateShopListLiveData(){
        shopListLiveData.value = shopList.toList()
    }
}