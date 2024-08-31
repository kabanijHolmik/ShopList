package com.example.shoplist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDBModel::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun shopListDao(): ShopListDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private val NAME = "shop_item_db"

        fun getInstance(application: Application): AppDatabase{
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(application, AppDatabase::class.java, NAME).build()
                INSTANCE = db
                return db
            }
        }
    }
}