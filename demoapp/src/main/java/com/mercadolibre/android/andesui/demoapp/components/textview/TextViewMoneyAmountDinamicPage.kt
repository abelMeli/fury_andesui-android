package com.mercadolibre.android.andesui.demoapp.components.textview

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextviewMoneyAmountBinding
import com.mercadolibre.android.andesui.moneyamount.AndesMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle

class TextViewMoneyAmountDinamicPage {

    private lateinit var textView: AndesTextView
    private lateinit var textViewA11y: AndesTextView

    private lateinit var spinnerType: Spinner
    private lateinit var spinnerCurrency: Spinner
    private lateinit var spinnerSize: Spinner
    private lateinit var spinnerStyle: Spinner
    private lateinit var spinnerCountry: Spinner
    private lateinit var spinnerSuffix: Spinner

    private lateinit var textFieldText: AndesTextfield
    private lateinit var buttonAddText: AndesButton

    private lateinit var textFieldAmount: AndesTextfield
    private lateinit var buttonAddAmount: AndesButton

    private lateinit var buttonUpdateRange: AndesButton
    private lateinit var textFieldFrom: AndesTextfield
    private lateinit var textFieldTo: AndesTextfield

    private lateinit var spinnerColor: Spinner
    private var selectedColor: AndesColor = AndesColor(com.mercadolibre.android.andesui.R.color.andes_bg_color_primary)
    private lateinit var viewColor: View
    private lateinit var buttonClear: AndesButton

    fun create(context: Context, containerView: View) {
        initComponents(containerView)
        setupSpinners(context)
        setupButtons(context)
    }

    private fun setupButtons(context: Context) {
        setupAddAmountButton(context)
        setupAddTextButton()
        setupUpdateButton()
        setupClearButton()
    }

    private fun initComponents(containerView: View) {
        val binding = AndesuiDynamicTextviewMoneyAmountBinding.bind(containerView)

        textView = binding.dynamicPageTextview
        textViewA11y = binding.dynamicPageTextviewA11y

        textFieldText = binding.dynamicPageTextviewAddText
        buttonAddText = binding.dynamicPageTextviewButtonAddText

        spinnerSize = binding.spinnerSize
        spinnerCurrency = binding.spinnerCurrency
        spinnerCountry = binding.spinnerCountry
        spinnerStyle = binding.spinnerStyle
        spinnerType = binding.spinnerType
        spinnerSuffix = binding.spinnerSuffix

        textFieldAmount = binding.dynamicPageTextviewAddAmount
        buttonAddAmount = binding.dynamicPageTextviewButtonAddAmount

        buttonUpdateRange = binding.dynamicPageTextviewButtonUpdateRange
        textFieldFrom = binding.dynamicPageTextviewTfColorFrom
        textFieldTo = binding.dynamicPageTextviewTfColorTo

        spinnerColor = binding.spinnerColor
        viewColor = binding.colorView

        buttonClear = binding.dynamicPageTextviewButtonClear
    }

    private fun setupSpinners(context: Context) {
        setupSizeSpinner(context)
        setupCurrencySpinner(context)
        setupCountrySpinner(context)
        setupStyleSpinner(context)
        setupTypeSpinner(context)
        setupSuffixSpinner(context)
        setupColorList(context)
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

    private fun setupSuffixSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_money_amount_spinner_prefix,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSuffix.adapter = adapter
        }
    }

    private fun setupColorList(context: Context) {
        val colors = listOf(
            "Primary",
            "Secondary",
            "Disabled",
            "Inverted",
            "Negative",
            "Caution",
            "Positive"
        )

        spinnerColor.adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            colors
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                selectedColor = when(spinnerColor.selectedItem.toString()) {
                    "Primary" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_primary)
                    "Secondary" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_secondary)
                    "Disabled" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_disabled)
                    "Inverted" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_white)
                    "Negative" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_negative)
                    "Caution" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_caution)
                    "Positive" -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_positive)
                    else -> AndesColor(com.mercadolibre.android.andesui.R.color.andes_text_color_primary)
                }
                viewColor.setBackgroundColor(selectedColor.colorInt(context))
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    private fun setupAddAmountButton(context: Context) {
        buttonAddAmount.setOnClickListener {
            setMoneyAmounts(context)
        }
    }

    private fun setupAddTextButton() {
        buttonAddText.setOnClickListener {
            setTextValues()
        }
    }

    private fun setupUpdateButton() {
        buttonUpdateRange.setOnClickListener {
            setColorRanges()
        }
    }

    private fun setupClearButton() {
        buttonClear.setOnClickListener {
            clearText()
        }
    }

    private fun setMoneyAmounts(context: Context) {
        val setupSize = resolveSize(spinnerSize)
        val setupType = resolveType(spinnerType)
        val setupStyle = resolveStyle(spinnerStyle)
        val setupCurrency = resolveCurrency(spinnerCurrency)
        val setupCountry = resolveCountry(spinnerCountry)
        val currencyInfo = AndesCurrencyHelper.getCurrency(setupCurrency)
        val suffix = spinnerSuffix.selectedItem
        val error = getMessageError(context, setupStyle, setupSize, currencyInfo.isCrypto, suffix != SUFFIX_NONE )
        val price = textFieldAmount.text.toString()

        error?.let { errorMessage ->
            AndesSnackbar(
                context, textView, AndesSnackbarType.ERROR, errorMessage, AndesSnackbarDuration.NORMAL
            ).show()
            return
        }

        val moneyAmount = AndesMoneyAmount(
            context,
            price.toDouble(),
            setupCurrency,
            true,
            setupSize,
            setupType,
            setupStyle,
            setupCountry
        )

        when (suffix) {
            SUFFIX_NONE -> { moneyAmount.setSuffix(null, null) }
            SUFFIX_UNIT -> { moneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad.") }
        }

        textView.apply {
            text = textView.text.takeIf { !it.isNullOrEmpty() } ?: DEFAULT_TEXT
            style = AndesTextViewStyle.TitleM
            if (price.isEmpty()) {
                textFieldAmount.state = AndesTextfieldState.ERROR
                return
            } else {
                textFieldAmount.state = AndesTextfieldState.IDLE
            }

            append(
                moneyAmount,
                selectedColor
            )
        }
        updateA11y()
    }

    private fun setTextValues() {
        textView.apply {
            val textToAdd = textFieldText.text.toString()
            text = textView.text.takeIf {
                !it.isNullOrEmpty()
            }
            style = AndesTextViewStyle.TitleM

            if (textToAdd.isEmpty()) {
                textFieldText.state = AndesTextfieldState.ERROR
                return
            } else {
                textFieldText.state = AndesTextfieldState.IDLE
            }

            append(textToAdd, selectedColor)
        }
        updateA11y()
    }

    private fun updateA11y() {
        "a11y: ${textView.accessibilityText}".also {
            textViewA11y.text = it
        }
    }

    private fun setColorRanges() {
        textView.apply {
            text = textView.text.takeIf {
                !it.isNullOrEmpty()
            }
            style = AndesTextViewStyle.TitleM
            val from = textFieldFrom.text?.toIntOrNull() ?: 0
            val to = textFieldTo.text?.toIntOrNull() ?: 0

            updateColor(IntRange(from, to), selectedColor)
        }
    }

    private fun clearText() {
        textView.clear()
        updateA11y()
    }

    private fun getMessageError(
        context: Context, style: AndesMoneyAmountDecimalsStyle,
        size: AndesMoneyAmountSize, isCrypto: Boolean, hasSuffix: Boolean): String? {

        if (style == AndesMoneyAmountDecimalsStyle.SUPERSCRIPT &&
            (size == AndesMoneyAmountSize.SIZE_12 || size == AndesMoneyAmountSize.SIZE_14)) {
            return context.getString(R.string.andes_money_amount_error_size)
        }
        if (isCrypto && style != AndesMoneyAmountDecimalsStyle.NORMAL) {
            return context.getString(R.string.andes_money_amount_error_decimal_format)
        }
        if (size == AndesMoneyAmountSize.SIZE_12 && hasSuffix) {
            return context.getString(R.string.andes_money_amount_error_suffix_size)
        }

        return null
    }

    private fun resolveSize(spinner: Spinner): AndesMoneyAmountSize {
        return AndesMoneyAmountSize.fromString(PREFIX_SIZE + spinner.selectedItem as String)
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

    private companion object {
        private const val DEFAULT_TEXT = ""
        const val SUFFIX_UNIT = "unidad"
        const val SUFFIX_NONE = "none"
        const val PREFIX_SIZE = "SIZE_"
    }
}
