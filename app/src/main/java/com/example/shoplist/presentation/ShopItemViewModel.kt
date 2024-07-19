package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.UpdateShopItemUseCase

class ShopItemViewModel: ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _isReadyToClose = MutableLiveData<Unit>()
    val isReadyToClose: LiveData<Unit>
        get() = _isReadyToClose

    fun getShopItem(shopItemId: Int){
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            val item = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(item)
            finishWork()

        }

    }

    fun updateShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                updateShopItemUseCase.updateShopItem(item)
                finishWork()
            }
        }

    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int{
        return count?.trim()?.toIntOrNull() ?: 0
    }

    private fun validateInput(name: String, count: Int): Boolean{
        var isValidate = true
        if(name.isBlank()){
            isValidate = false
            _errorInputName.value = true
        }
        if (count <= 0){
            isValidate = false
            _errorInputCount.value = true
        }
        return isValidate
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _isReadyToClose.value = Unit
    }
}