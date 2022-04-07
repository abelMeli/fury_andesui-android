package com.mercadolibre.android.andesui.moneyamount

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesLayoutMoneyAmountBinding
import com.mercadolibre.android.andesui.moneyamount.accessibility.AndesMoneyAmountDiscountAccessibilityDelegate
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrsParser
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfiguration
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

class AndesMoneyAmountDiscount : ConstraintLayout {

    /**
     * Getter and setter for [discount].
     */
    var discount: Int
        get() = andesMoneyAmountDiscountAttrs.discount
        set(value) {
            andesMoneyAmountDiscountAttrs = andesMoneyAmountDiscountAttrs.copy(discount = value)
            createConfig().also {
                validateData(it)
                setupDiscount(it)
            }
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesMoneyAmountSize
        get() = andesMoneyAmountDiscountAttrs.discountSize
        set(value) {
            andesMoneyAmountDiscountAttrs = andesMoneyAmountDiscountAttrs.copy(discountSize = value)
            createConfig().also {
                validateData(it)
                setupDiscount(it)
                setupIcon(it)
            }
        }

    /**
     * Getter and setter icon.
     */
    var icon: Drawable?
        get() = andesMoneyAmountDiscountAttrs.discountIcon
        set(value) {
            andesMoneyAmountDiscountAttrs = andesMoneyAmountDiscountAttrs.copy(discountIcon = value)
            createConfig().also {
                validateData(it)
                setupIcon(it)
            }
        }

    private lateinit var andesMoneyAmountDiscountAttrs: AndesMoneyAmountDiscountAttrs
    private val binding by lazy {
        AndesLayoutMoneyAmountBinding.inflate(LayoutInflater.from(context), this, true)
    }

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

    constructor(
        context: Context,
        discount: Int,
        icon: Drawable,
        size: AndesMoneyAmountSize = ANDES_SIZE_DEFAULT_VALUE
    ) : super(context) {
        initAttrs(discount, size, icon)
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
        size: AndesMoneyAmountSize = ANDES_SIZE_DEFAULT_VALUE,
        icon: Drawable? = null
    ) {
        andesMoneyAmountDiscountAttrs = AndesMoneyAmountDiscountAttrs(discount, size, icon)
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesMoneyAmountDiscountConfiguration) {
        setupViewId()
        validateData(config)
        setupDiscount(config)
        setupIcon(config)
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
            throw IllegalArgumentException(context.resources.getString(R.string.andes_money_amount_error_discount))
        } else if (config.discountIcon != null && size.size.discountIconSize(context) == 0F) {
            throw IllegalArgumentException(context.resources.getString(R.string.andes_money_amount_error_discount_size))
        }
    }

    /**
     * Construct and draw the component
     */
    @SuppressLint("SetTextI18n")
    private fun setupDiscount(config: AndesMoneyAmountDiscountConfiguration) {
        binding.moneyAmountText.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.discountSize)
            typeface = context.getFontOrDefault(R.font.andes_font_regular)
            setTextColor(ContextCompat.getColor(context, R.color.andes_green_500))
            text = "${config.discount}${context.getString(R.string.andes_money_amount_discount)}"
        }
    }

    private fun setupIcon(config: AndesMoneyAmountDiscountConfiguration) {
        config.discountIcon?.let {
            binding.moneyAmountIcon.apply {
                layoutParams.height = config.discountIconSize.toInt()
                layoutParams.width = config.discountIconSize.toInt()
                setPadding(
                    DEFAULT_PADDING,
                    DEFAULT_PADDING,
                    config.iconPadding.toInt(),
                    DEFAULT_PADDING
                )
                setColorFilter(ContextCompat.getColor(context, R.color.andes_green_500))
                setImageDrawable(it)
                visibility = View.VISIBLE
            }
        } ?: run {
            binding.moneyAmountIcon.visibility = View.GONE
        }
    }

    private fun createConfig() = AndesMoneyAmountDiscountConfigurationFactory.create(context, andesMoneyAmountDiscountAttrs)

    override fun getAccessibilityClassName(): CharSequence {
        return TextView::class.java.name
    }

    /**
     * Method used to expose [AndesMoneyAmountDiscount] contentDescription.
     */
    @SuppressLint("GetContentDescriptionOverride")
    override fun getContentDescription(): CharSequence? {
        return AndesMoneyAmountDiscountAccessibilityDelegate.generateMoneyAmountDiscountContentDescriptionText(
            this.discount,
            this.resources
        )
    }

    companion object {
        private val ANDES_SIZE_DEFAULT_VALUE = AndesMoneyAmountSize.SIZE_24
        const val DISCOUNT_0 = 0
        const val DEFAULT_PADDING = 0
        const val DISCOUNT_100 = 100
    }
}
