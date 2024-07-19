package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    lateinit var shopItemViewModel: ShopItemViewModel

    private lateinit var buttonSave: Button
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputName: TextInputEditText
    private lateinit var textInputCount: TextInputEditText

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseIntent()
        setContentView(R.layout.activity_shop_item)
        initViews()

        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        shopItemViewModel.errorInputName.observe(this) {
            if (it)
            textInputLayoutName.error = "Ошибка при вводе имени"
            else textInputLayoutName.error = null
        }

        shopItemViewModel.errorInputCount.observe(this) {
            if (it)
                textInputLayoutCount.error = "Ошибка при вводе количества"
            else textInputLayoutCount.error = null
        }

        when (screenMode) {
            MODE_ADD -> launchAddScreen()
            MODE_UPDATE -> launchUpdateScreen()
        }

        textInputName.doOnTextChanged { text, start, before, count ->
            shopItemViewModel.resetErrorInputName()
        }

        textInputCount.doOnTextChanged { text, start, before, count ->
            shopItemViewModel.resetErrorInputCount()
        }
    }

    private fun launchAddScreen() {
        buttonSave.setOnClickListener {
            shopItemViewModel.addShopItem(
                textInputName.text.toString(),
                textInputCount.text.toString()
            )

            closeScreen()
        }
    }


    private fun launchUpdateScreen() {
        shopItemViewModel.getShopItem(shopItemId)

        textInputName.setText(shopItemViewModel.shopItem.value?.name)
        textInputCount.setText(shopItemViewModel.shopItem.value?.count.toString())

        buttonSave.setOnClickListener {
            shopItemViewModel.updateShopItem(
                textInputName.text.toString(),
                textInputCount.text.toString()
            )

            closeScreen()
        }

    }

    private fun closeScreen() {
        if (shopItemViewModel.isReadyToClose.value != null) {
            finish()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_UPDATE) {
            throw RuntimeException("Unknown screen mode: $mode")
        }

        screenMode = mode

        if (screenMode == MODE_UPDATE && !intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
            throw RuntimeException("Shop item id is absent")
        }

        if (screenMode == MODE_UPDATE) {
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    private fun initViews() {
        buttonSave = findViewById(R.id.buttonSave)
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
        textInputName = findViewById(R.id.textInputName)
        textInputCount = findViewById(R.id.textInputCount)
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UPDATE = "mode_update"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"
        private const val UNDEFINED_ID = -1

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_UPDATE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

}