package com.example.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
    lateinit var shopItemViewModel: ShopItemViewModel
    var onEditingFinishedListener: OnEditingFinishedListener? = null

    private lateinit var buttonSave: Button
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputName: TextInputEditText
    private lateinit var textInputCount: TextInputEditText

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Fragment", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Fragment", "OnCreate")
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_item, container, false)
        Log.d("Fragment", "onCreateView")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Fragment", "onViewCreated")
        initViews(view)

        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it)
                textInputLayoutName.error = "Ошибка при вводе имени"
            else textInputLayoutName.error = null
        }

        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
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

    override fun onStart() {
        Log.d("Fragment", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("Fragment", "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("Fragment", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("Fragment", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("Fragment", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("Fragment", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("Fragment", "onDetach")
        super.onDetach()
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
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun parseParams() {
        screenMode = requireArguments().getString(EXTRA_SCREEN_MODE).toString()
        shopItemId = requireArguments().getInt(EXTRA_SHOP_ITEM_ID)
        if (screenMode != MODE_ADD && screenMode != MODE_UPDATE) {
            throw RuntimeException("Unknown screen mode: $screenMode")
        }

        if (screenMode == MODE_UPDATE && shopItemId == UNDEFINED_ID) {
            throw RuntimeException("Shop item id is absent")
        }

    }

    private fun initViews(view: View) {
        buttonSave = view.findViewById(R.id.buttonSave)
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        textInputName = view.findViewById(R.id.textInputName)
        textInputCount = view.findViewById(R.id.textInputCount)
    }

    interface OnEditingFinishedListener{
        fun onEditingFinished( )
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UPDATE = "mode_update"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"
        private const val UNDEFINED_ID = -1

        fun newInstanceAddItem():ShopItemFragment{
            val args = Bundle().apply { putString(EXTRA_SCREEN_MODE, MODE_ADD) }
            return ShopItemFragment().apply { arguments = args }
        }

        fun newInstanceUpdateItem(shopItemId: Int):ShopItemFragment{
            val args = Bundle().apply {
                putString(EXTRA_SCREEN_MODE, MODE_UPDATE)
                putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
            return ShopItemFragment().apply { arguments = args }
        }

    }
}