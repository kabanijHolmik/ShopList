package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoplist.domain.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM SHOP_ITEM")
    fun getShopList(): LiveData<List<ShopItemDBModel>>

    @Query("DELETE FROM SHOP_ITEM WHERE id = :shopItemId")
    suspend fun deleteShopItem(shopItemId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDBModel)

    @Query("SELECT * FROM SHOP_ITEM WHERE id = :shopItemId LIMIT 1")
    suspend fun getShopItem(shopItemId: Int): ShopItemDBModel
}