package com.mercadolibre.android.andesui.demoapp.components.moneyamount

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.Group
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountCombo
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmountDiscount
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

@Suppress("TooManyFunctions")
class MoneyAmountDynamicPage {

    private lateinit var priceGroup: Group
    private lateinit var discountGroup: Group
    private lateinit var comboGroup: Group

    private lateinit var andesMoneyAmount: AndesMoneyAmount
    private lateinit var andesMoneyAmountDiscount: AndesMoneyAmountDiscount
    private lateinit var andesMoneyAmountCombo: AndesMoneyAmountCombo

    private lateinit var textFieldDiscount: AndesTextfield
    private lateinit var textFieldPrevious: AndesTextfield
    private lateinit var textFieldPrice: AndesTextfield

    private lateinit var spinnerType: Spinner
    private lateinit var spinnerCurrency: Spinner
    private lateinit var spinnerSize: Spinner
    private lateinit var spinnerComboSize: Spinner
    private lateinit var spinnerStyle: Spinner
    private lateinit var spinnerMoneyAmountType: Spinner
    private lateinit var spinnerCountry: Spinner

    private lateinit var changeButton: AndesButton
    private lateinit var clearButton: AndesButton

    fun create(context: Context, container: View) {
        initComponents(container)
        setupSpinners(context)
        setupButtons(context)
        resetDefaultValues()
    }

    private fun initComponents(view: View) {
        priceGroup = view.findViewById(R.id.priceGroup)
        discountGroup = view.findViewById(R.id.discountGroup)
        comboGroup = view.findViewById(R.id.comboGroup)
        andesMoneyAmount = view.findViewById(R.id.andes_money_amount)
        andesMoneyAmountDiscount = view.findViewById(R.id.andes_money_amount_discount)
        andesMoneyAmountCombo = view.findViewById(R.id.andes_money_amount_combo)
        textFieldDiscount = view.findViewById(R.id.money_amount_discount)
        textFieldPrevious = view.findViewById(R.id.money_amount_previous)
        textFieldPrice = view.findViewById(R.id.money_amount_price)
        spinnerType = view.findViewById(R.id.spinner_type)
        spinnerMoneyAmountType = view.findViewById(R.id.spinner_money_amount_type)
        spinnerCountry = view.findViewById(R.id.spinner_country)
        spinnerStyle = view.findViewById(R.id.spinner_style)
        spinnerCurrency = view.findViewById(R.id.spinner_currency)
        spinnerSize = view.findViewById(R.id.spinner_size)
        spinnerComboSize = view.findViewById(R.id.spinner_combo_size)
        changeButton = view.findViewById(R.id.change_button)
        clearButton = view.findViewById(R.id.clear_button)
    }

    private fun setupSpinners(context: Context) {
        setupTypeSpinner(context)
        setupMoneyAmountTypeSpinner(context)
        setupSizeSpinner(context)
        setupComboSizeSpinner(context)
        setupCurrencySpinner(context)
        setupStyleSpinner(context)
        setupCountrySpinner(context)
    }

    private fun setupTypeSpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_type,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }
    }

    private fun setupSizeSpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_size,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSize.adapter = adapter
        }
    }

    private fun setupCountrySpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_country,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountry.adapter = adapter
        }
    }

    private fun setupMoneyAmountTypeSpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_money_amount_type,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMoneyAmountType.adapter = adapter
        }

        spinnerMoneyAmountType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                when (spinnerMoneyAmountType.selectedItem.toString()) {
                    "Price" -> {
                        discountGroup.visibility = View.GONE
                        comboGroup.visibility = View.GONE
                        priceGroup.visibility = View.VISIBLE
                    }
                    "Discount" -> {
                        priceGroup.visibility = View.GONE
                        comboGroup.visibility = View.GONE
                        discountGroup.visibility = View.VISIBLE
                    }
                    "Combo" -> {
                        priceGroup.visibility = View.GONE
                        discountGroup.visibility = View.GONE
                        comboGroup.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Nothing to do
            }
        }
    }

    private fun setupComboSizeSpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_combo_size,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerComboSize.adapter = adapter
        }
    }

    private fun setupCurrencySpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_currency,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCurrency.adapter = adapter
        }
    }

    private fun setupStyleSpinner(context: Context) {
        ArrayAdapter.createFromResource(
                context,
                R.array.andes_money_amount_spinner_style,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStyle.adapter = adapter
        }
    }

    private fun resetDefaultValues() {
        andesMoneyAmount.size = AndesMoneyAmountSize.SIZE_24
        andesMoneyAmount.type = AndesMoneyAmountType.POSITIVE
        andesMoneyAmount.decimalsStyle = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        andesMoneyAmount.currency = AndesMoneyAmountCurrency.ARS
        andesMoneyAmount.country = AndesCountry.AR
        andesMoneyAmount.amount = DEFAULT_PRICE

        andesMoneyAmountDiscount.size = AndesMoneyAmountSize.SIZE_24
        andesMoneyAmountDiscount.discount = DEFAULT_DISCOUNT

        andesMoneyAmountCombo.size = AndesMoneyAmountComboSize.SIZE_24
        andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.ARS
        andesMoneyAmountCombo.previousAmount = DEFAULT_PREVIOUS_PRICE
        andesMoneyAmountCombo.amount = DEFAULT_PRICE
        andesMoneyAmountCombo.discount = DEFAULT_DISCOUNT
        andesMoneyAmountCombo.country = AndesCountry.AR

        textFieldPrice.text = ""
        textFieldDiscount.text = ""
        textFieldPrevious.text = ""

        spinnerSize.setSelection(FIVE_POSITION)
        spinnerComboSize.setSelection(ZERO_POSITION)
        spinnerCurrency.setSelection(TWELVE_POSITION)
        spinnerStyle.setSelection(SECOND_POSITION)
        spinnerType.setSelection(ZERO_POSITION)
        spinnerMoneyAmountType.setSelection(ZERO_POSITION)
        spinnerCountry.setSelection(ZERO_POSITION)
    }

    private fun setupButtons(context: Context) {
        setupUpdateButton(context)
        setupClearButton()
    }

    private fun setupClearButton() {
        clearButton.setOnClickListener {
            resetDefaultValues()
        }
    }

    private fun setupUpdateButton(context: Context) {
        changeButton.setOnClickListener {
            val size = resolveSize(spinnerSize)
            val comboSize = resolveComboSize(spinnerComboSize)
            val type = resolveType(spinnerType)
            val style = resolveStyle(spinnerStyle)
            val currency = resolveCurrency(spinnerCurrency)
            val country = resolveCountry(spinnerCountry)

            when (spinnerMoneyAmountType.selectedItem.toString()) {
                "Price" -> {
                    val price = textFieldPrice.text.toString()
                    if (price.isEmpty()) {
                        textFieldPrice.state = AndesTextfieldState.ERROR
                        return@setOnClickListener
                    } else {
                        textFieldPrice.state = AndesTextfieldState.IDLE
                    }
                    andesMoneyAmount.amount = price.toDouble()
                    andesMoneyAmount.decimalsStyle = style
                    andesMoneyAmount.type = type
                    andesMoneyAmount.currency = currency
                    andesMoneyAmount.size = size
                    andesMoneyAmount.country = country
                }
                "Discount" -> {
                    val discount = textFieldDiscount.text.toString()
                    if (discount.isEmpty()) {
                        textFieldDiscount.state = AndesTextfieldState.ERROR
                        return@setOnClickListener
                    } else {
                        textFieldDiscount.state = AndesTextfieldState.IDLE
                    }
                    andesMoneyAmountDiscount.discount = discount.toInt()
                    andesMoneyAmountDiscount.size = size
                }
                "Combo" -> {
                    val price = textFieldPrice.text.toString()
                    var discount = textFieldDiscount.text.toString()
                    var previous = textFieldPrevious.text.toString()
                    if (discount.isEmpty()) {
                        discount = "0"
                    }
                    if (previous.isEmpty()) {
                        previous = "0"
                    }
                    if (price.isEmpty()) {
                        textFieldPrice.state = AndesTextfieldState.ERROR
                        return@setOnClickListener
                    } else {
                        textFieldPrice.state = AndesTextfieldState.IDLE
                    }
                    andesMoneyAmountCombo.discount = discount.toInt()
                    andesMoneyAmountCombo.amount = price.toDouble()
                    andesMoneyAmountCombo.previousAmount = previous.toDouble()
                    andesMoneyAmountCombo.currency = currency
                    andesMoneyAmountCombo.size = comboSize
                    andesMoneyAmountCombo.country = country
                }
            }
        }
    }

    private fun resolveSize(spinner: Spinner): AndesMoneyAmountSize {
        return AndesMoneyAmountSize.fromString(PREFIX_SIZE + spinner.selectedItem as String)
    }

    private fun resolveComboSize(spinner: Spinner): AndesMoneyAmountComboSize {
        return AndesMoneyAmountComboSize.fromString(PREFIX_SIZE + spinner.selectedItem as String)
    }

    private fun resolveStyle(spinner: Spinner): AndesMoneyAmountDecimalsStyle {
        return AndesMoneyAmountDecimalsStyle.fromString(spinner.selectedItem as String)
    }

    private fun resolveType(spinner: Spinner): AndesMoneyAmountType {
        return AndesMoneyAmountType.fromString(spinner.selectedItem as String)
    }

    private fun resolveCurrency(spinner: Spinner): AndesMoneyAmountCurrency {
        return AndesMoneyAmountCurrency.fromString(spinner.selectedItem as String)
    }

    private fun resolveCountry(spinner: Spinner): AndesCountry {
        return AndesCountry.fromString(spinner.selectedItem as String)
    }

    companion object {
        const val DEFAULT_PREVIOUS_PRICE = 1500.0
        const val DEFAULT_PRICE = 1350.0
        const val DEFAULT_DISCOUNT = 10
        const val PREFIX_SIZE = "SIZE_"
        const val ZERO_POSITION = 0
        const val SECOND_POSITION = 2
        const val FIVE_POSITION = 5
        const val TWELVE_POSITION = 12
    }
}
