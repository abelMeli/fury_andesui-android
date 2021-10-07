package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountAccessibilityDelegate
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountAttrsParser
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountConfiguration
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class AndesMoneyAmount : androidx.appcompat.widget.AppCompatTextView {

    /**
     * Getter and setter for [amount].
     */
    var amount: Double
        get() = andesMoneyAmountAttrs.andesMoneyAmount
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmount = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [type].
     */
    var type: AndesMoneyAmountType
        get() = andesMoneyAmountAttrs.andesMoneyAmountType
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountType = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesMoneyAmountSize
        get() = andesMoneyAmountAttrs.andesMoneyAmountSize
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountSize = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [currency].
     */
    var currency: AndesMoneyAmountCurrency
        get() = andesMoneyAmountAttrs.andesMoneyAmountCurrency
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountCurrency = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [country].
     */
    var country: AndesCountry
        get() = andesMoneyAmountAttrs.andesMoneyAmountCountry
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountCountry = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [decimalsStyle].
     */
    var decimalsStyle: AndesMoneyAmountDecimalsStyle
        get() = andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountDecimalsStyle = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [showZerosDecimal].
     */
    var showZerosDecimal: Boolean
        get() = andesMoneyAmountAttrs.andesShowZerosDecimal
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesShowZerosDecimal = value)
            setupAmount(createConfig())
        }

    private lateinit var andesMoneyAmountAttrs: AndesMoneyAmountAttrs

    private constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        amount: Double,
        currency: AndesMoneyAmountCurrency,
        showZerosDecimal: Boolean = ANDES_SHOW_ZEROS_DEFAULT_VALUE,
        size: AndesMoneyAmountSize = ANDES_SIZE_DEFAULT_VALUE,
        type: AndesMoneyAmountType = ANDES_TYPE_DEFAULT_VALUE,
        style: AndesMoneyAmountDecimalsStyle = ANDES_STYLE_DEFAULT_VALUE,
        country: AndesCountry = AndesCurrencyHelper.currentCountry
    ) : super(context) {
        initAttrs(amount, currency, showZerosDecimal, size, type, style, country)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesMoneyAmountAttrs = AndesMoneyAmountAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        amount: Double,
        currency: AndesMoneyAmountCurrency,
        showZerosDecimal: Boolean,
        size: AndesMoneyAmountSize,
        type: AndesMoneyAmountType,
        style: AndesMoneyAmountDecimalsStyle,
        country: AndesCountry
    ) {
        andesMoneyAmountAttrs = AndesMoneyAmountAttrs(amount, showZerosDecimal, size, type, style, currency, country)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesMoneyAmountConfiguration) {
        setupViewId()
        setupAmount(config)
        setupAccessibility()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupAccessibility() {
        accessibilityDelegate = AndesMoneyAmountAccessibilityDelegate(this)
    }

    private fun setupAmount(config: AndesMoneyAmountConfiguration) {
        if (config.isValidSize) {
            setTextColor(config.color.colorInt(context))
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.amountSize)
            typeface = context.getFontOrDefault(R.font.andes_font_regular)
            text = config.amountFormatted
        } else {
            throw IllegalArgumentException("This size is not valid for superscript select a higher value")
        }
    }

    private fun createConfig() = AndesMoneyAmountConfigurationFactory.create(context, andesMoneyAmountAttrs)

    companion object {
        private val ANDES_SIZE_DEFAULT_VALUE = AndesMoneyAmountSize.SIZE_24
        private val ANDES_TYPE_DEFAULT_VALUE = AndesMoneyAmountType.POSITIVE
        private val ANDES_STYLE_DEFAULT_VALUE = AndesMoneyAmountDecimalsStyle.NORMAL
        private const val ANDES_SHOW_ZEROS_DEFAULT_VALUE = false
    }
}
