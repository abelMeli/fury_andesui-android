package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountComboAccessibilityDelegate
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboAttrsParser
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboConfiguration
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType

class AndesMoneyAmountCombo : ConstraintLayout {

    /**
     * Getter and setter for [amount].
     */
    var amount: Double
        get() = andesMoneyAmountComboAttrs.andesMoneyAmount
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyAmount = value)
            setupAmount(createConfig())
        }

    /**
     * Getter and setter for [previousAmount].
     */
    var previousAmount: Double
        get() = andesMoneyAmountComboAttrs.andesMoneyPreviousAmount
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyPreviousAmount = value)
            setupPreviousAmount(createConfig())
        }

    /**
     * Getter and setter for [discount].
     */
    var discount: Int
        get() = andesMoneyAmountComboAttrs.andesMoneyDiscount
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyDiscount = value)
            setupDiscount(createConfig())
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesMoneyAmountComboSize
        get() = andesMoneyAmountComboAttrs.andesMoneyAmountSize
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyAmountSize = value)
            createConfig().also {
                setupAmount(it)
                setupPreviousAmount(it)
                setupDiscount(it)
            }
        }

    /**
     * Getter and setter for [currency].
     */
    var currency: AndesMoneyAmountCurrency
        get() = andesMoneyAmountComboAttrs.andesMoneyAmountCurrency
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyAmountCurrency = value)
            createConfig().also {
                setupAmount(it)
                setupPreviousAmount(it)
                setupAccessibility()
            }
        }

    /**
     * Getter and setter for [country].
     */
    var country: AndesCountry
        get() = andesMoneyAmountComboAttrs.andesMoneyAmountCountry
        set(value) {
            andesMoneyAmountComboAttrs = andesMoneyAmountComboAttrs.copy(andesMoneyAmountCountry = value)
            createConfig().also {
                setupAmount(it)
                setupPreviousAmount(it)
            }
        }

    private lateinit var andesMoneyAmountComboAttrs: AndesMoneyAmountComboAttrs
    private lateinit var andesAmount: AndesMoneyAmount
    private lateinit var andesPreviousAmount: AndesMoneyAmount
    private lateinit var andesDiscount: AndesMoneyAmountDiscount

    private constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        amount: Double,
        currency: AndesMoneyAmountCurrency,
        country: AndesCountry = AndesCurrencyHelper.currentCountry,
        previousAmount: Double = ANDES_PREVIOUS_DEFAULT_VALUE,
        discount: Int = ANDES_DISCOUNT_DEFAULT_VALUE,
        size: AndesMoneyAmountComboSize = ANDES_SIZE_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(currency, country, amount, previousAmount, discount, size)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesMoneyAmountComboAttrs = AndesMoneyAmountComboAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    @Suppress("LongParameterList")
    private fun initAttrs(
        currency: AndesMoneyAmountCurrency,
        country: AndesCountry,
        amount: Double,
        previousAmount: Double = ANDES_PREVIOUS_DEFAULT_VALUE,
        discount: Int = ANDES_DISCOUNT_DEFAULT_VALUE,
        size: AndesMoneyAmountComboSize = ANDES_SIZE_DEFAULT_VALUE
    ) {
        andesMoneyAmountComboAttrs = AndesMoneyAmountComboAttrs(currency, country, amount, previousAmount, discount, size)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesMoneyAmountComboConfiguration) {
        initComponent()
        setupViewId()
        setupAmount(config)
        setupPreviousAmount(config)
        setupDiscount(config)
        setupAccessibility()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupAccessibility() {
        andesDiscount.isFocusable = false
        andesAmount.isFocusable = false
        andesPreviousAmount.isFocusable = false
        isFocusable = true
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES

        accessibilityDelegate = AndesMoneyAmountComboAccessibilityDelegate(
            amount = andesAmount,
            previousAmount = andesPreviousAmount,
            andesDiscount = andesDiscount,
            currency = andesMoneyAmountComboAttrs.andesMoneyAmountCurrency
        )
    }

    private fun initComponent() {
        val container = LayoutInflater.from(context).inflate(R.layout.andes_layout_money_amount, this)
        andesAmount = container.findViewById(R.id.andes_money_amount_combo_amount)
        andesPreviousAmount = container.findViewById(R.id.andes_money_amount_combo_previous_amount)
        andesDiscount = container.findViewById(R.id.andes_money_amount_discount)
    }

    private fun setupAmount(config: AndesMoneyAmountComboConfiguration) {
        andesAmount.type = AndesMoneyAmountType.POSITIVE
        andesAmount.amount = config.amount
        andesAmount.currency = config.currency
        andesAmount.country = config.country
    }

    private fun setupPreviousAmount(config: AndesMoneyAmountComboConfiguration) {
        if (config.previousAmount != ANDES_PREVIOUS_DEFAULT_VALUE) {
            andesPreviousAmount.visibility = VISIBLE
            andesPreviousAmount.currency = config.currency
            andesPreviousAmount.country = config.country
            andesPreviousAmount.amount = config.previousAmount
            andesPreviousAmount.type = AndesMoneyAmountType.PREVIOUS
            andesPreviousAmount.size = config.previousAmountSize
        } else {
            andesPreviousAmount.visibility = GONE
            andesPreviousAmount.amount = ANDES_PREVIOUS_DEFAULT_VALUE
        }
    }

    private fun setupDiscount(config: AndesMoneyAmountComboConfiguration) {
        if (config.discount != ANDES_DISCOUNT_DEFAULT_VALUE) {
            andesDiscount.visibility = VISIBLE
            andesDiscount.discount = config.discount
            andesDiscount.size = config.discountSize
        } else {
            andesDiscount.visibility = GONE
            andesDiscount.discount = ANDES_DISCOUNT_DEFAULT_VALUE
        }
    }

    private fun createConfig() = AndesMoneyAmountComboConfigurationFactory.create(context, andesMoneyAmountComboAttrs)

    companion object {
        private val ANDES_SIZE_DEFAULT_VALUE = AndesMoneyAmountComboSize.SIZE_24
        private const val ANDES_PREVIOUS_DEFAULT_VALUE = 0.0
        private const val ANDES_DISCOUNT_DEFAULT_VALUE = 0
    }
}
