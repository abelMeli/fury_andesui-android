package com.mercadolibre.android.andesui.moneyamount

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountDiscountAccessibilityDelegate
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrsParser
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfiguration
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class AndesMoneyAmountDiscount : androidx.appcompat.widget.AppCompatTextView {

    /**
     * Getter and setter for [discount].
     */
    var discount: Int
        get() = andesMoneyAmountDiscountAttrs.andesMoneyDiscount
        set(value) {
            andesMoneyAmountDiscountAttrs = andesMoneyAmountDiscountAttrs.copy(andesMoneyDiscount = value)
            createConfig().also {
                validateData(it)
                setupAmount(it)
            }
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesMoneyAmountSize
        get() = andesMoneyAmountDiscountAttrs.andesMoneyAmountSize
        set(value) {
            andesMoneyAmountDiscountAttrs = andesMoneyAmountDiscountAttrs.copy(andesMoneyAmountSize = value)
            setupAmount(createConfig())
        }

    private lateinit var andesMoneyAmountDiscountAttrs: AndesMoneyAmountDiscountAttrs

    private constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(
        context: Context,
        discount: Int,
        size: AndesMoneyAmountSize = ANDES_SIZE_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(discount, size)
    }

    /**
     * Sets the proper [config] for this component based on the [attrs] received via XML.
     *
     * @param attrs attributes from the XML.
     */
    private fun initAttrs(attrs: AttributeSet?) {
        andesMoneyAmountDiscountAttrs = AndesMoneyAmountDiscountAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        discount: Int,
        size: AndesMoneyAmountSize = ANDES_SIZE_DEFAULT_VALUE
    ) {
        andesMoneyAmountDiscountAttrs = AndesMoneyAmountDiscountAttrs(discount, size)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesMoneyAmountDiscountConfiguration) {
        setupViewId()
        validateData(config)
        setupAmount(config)
        setupAccessibility()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupAccessibility() {
        accessibilityDelegate = AndesMoneyAmountDiscountAccessibilityDelegate(this)
    }

    private fun validateData(config: AndesMoneyAmountDiscountConfiguration) {
        if (config.discount < DISCOUNT_0 || config.discount > DISCOUNT_100) {
            throw IllegalArgumentException("The discount must be a value between 0 and 100")
        }
    }

    /**
     * Construct and draw the component
     */
    @SuppressLint("SetTextI18n")
    private fun setupAmount(config: AndesMoneyAmountDiscountConfiguration) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, config.discountSize)
        typeface = context.getFontOrDefault(R.font.andes_font_regular)
        setTextColor(ContextCompat.getColor(context, R.color.andes_green_500))
        text = "${config.discount}${context.getString(R.string.andes_money_amount_discount)}"
    }

    private fun createConfig() = AndesMoneyAmountDiscountConfigurationFactory.create(context, andesMoneyAmountDiscountAttrs)

    companion object {
        private val ANDES_SIZE_DEFAULT_VALUE = AndesMoneyAmountSize.SIZE_24
        const val DISCOUNT_0 = 0
        const val DISCOUNT_100 = 100
    }
}
