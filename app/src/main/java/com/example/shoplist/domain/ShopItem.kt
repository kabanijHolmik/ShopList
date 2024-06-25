package com.example.shoplist.domain

data class ShopItem(
    val id: Int,
    var name: String,
    var count: Int,
    var isActive: Boolean
)