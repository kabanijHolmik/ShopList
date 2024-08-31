package com.example.shoplist.data

import android.app.Application
import androidx.core.os.BuildCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import kotlin.jvm.Throws

class ShopListRepositoryImpl(application: Application) : ShopListRepository {
    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun getShopList(): LiveData<List<ShopItem>> {
         return shopListDao.getShopList().map {
             mapper.mapListDBModelToEntity(it)
         }
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem? {
        return mapper.mapDBModelToEntity(shopListDao.getShopItem(shopItemId))
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDBModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun updateShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDBModel(shopItem))
    }
}