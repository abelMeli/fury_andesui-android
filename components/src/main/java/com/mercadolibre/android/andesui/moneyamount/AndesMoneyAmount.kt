package com.mercadolibre.android.andesui.moneyamount

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.databinding.AndesLayoutMoneyAmountBinding
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.ERROR
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

/**
 * Interface that becomes the money amount in info provider.
 */
internal interface AndesMoneyAmountInfoProvider {
    /**
     * Provides the styled text for external purposes.
     * @param color in text that is required
     */
    fun provideText(color: Int? = null): SpannableStringBuilder
}

class AndesMoneyAmount : ConstraintLayout, AndesMoneyAmountInfoProvider {

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
            setupMoneyAmount(createConfig())
        }

    /**
     * Getter and setter for [size].
     */
    var size: AndesMoneyAmountSize
        get() = andesMoneyAmountAttrs.andesMoneyAmountSize
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountSize = value)
            setupMoneyAmount(createConfig())
        }

    /**
     * Getter and setter for [currency].
     */
    var currency: AndesMoneyAmountCurrency
        get() = andesMoneyAmountAttrs.andesMoneyAmountCurrency
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesMoneyAmountCurrency = value)
            setupMoneyAmount(createConfig())
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
            setupMoneyAmount(createConfig())
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

    /**
     * Getter and setter for [showIcon].
     */
    var showIcon: Boolean
        get() = andesMoneyAmountAttrs.andesShowIcon
        set(value) {
            andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesShowIcon = value)
            setupIcon(createConfig())
        }

    /**
     * Setter for [suffix] with your accessibility.
     */
    fun setSuffix(suffix: SpannableStringBuilder?, suffixAccessibility: String?) {
        andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(
            andesSuffix = suffix, andesSuffixAccessibility = suffixAccessibility
        )
        setupMoneyAmount(createConfig())
    }

    internal fun getSuffixAccessibility(): String? = andesMoneyAmountAttrs.andesSuffixAccessibility

    /**
     * Setter for the textColor
     */
    fun setTextColor(color: AndesColor) {
        andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesTextColor = color)
        setupMoneyAmount(createConfig())
    }

    /**
     * Setter for the textColor
     */
    fun setTextColor(@ColorRes color: Int) {
        andesMoneyAmountAttrs = andesMoneyAmountAttrs.copy(andesTextColor = color.toAndesColor())
        setupMoneyAmount(createConfig())
    }

    private lateinit var andesMoneyAmountAttrs: AndesMoneyAmountAttrs
    private val binding by lazy {
        AndesLayoutMoneyAmountBinding.inflate(LayoutInflater.from(context), this, true)
    }

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
        country: AndesCountry,
        showIcon: Boolean = false
    ) {
        andesMoneyAmountAttrs = AndesMoneyAmountAttrs(
            amount, showZerosDecimal, size, type, style, currency, country, showIcon
        )
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesMoneyAmountConfiguration) {
        setupViewId()
        setupMoneyAmount(config)
        setupAccessibility()
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupAccessibility() {
        isFocusable = true
        accessibilityDelegate = AndesMoneyAmountAccessibilityDelegate(this)
    }

    private fun setupMoneyAmount(config: AndesMoneyAmountConfiguration) {
        when (config.isValidData) {
            ERROR.NONE -> {
                setupIcon(config)
                setupAmount(config)
            }
            ERROR.SIZE -> throw IllegalArgumentException(context.resources.getString(R.string.andes_money_amount_error_size))
            ERROR.DECIMAL_FORMAT -> throw IllegalArgumentException(context.resources.getString(R.string.andes_money_amount_error_decimal_format))
            ERROR.SUFFIX_SIZE -> throw IllegalArgumentException(context.resources.getString(R.string.andes_money_amount_error_suffix_size))
        }
    }

    private fun setupIcon(config: AndesMoneyAmountConfiguration) {
        config.currencyInfo.icon.let {
            val showIcon = it != null && config.showIcon
            binding.moneyAmountIcon.apply {
                visibility = if (showIcon) View.VISIBLE else  View.GONE
                layoutParams.height = config.iconSize.toInt()
                layoutParams.width = config.iconSize.toInt()
                setPadding(DEFAULT_PADDING,DEFAULT_PADDING,config.iconPadding.toInt(),DEFAULT_PADDING)
                setImageDrawable(it?.let { drawable -> ContextCompat.getDrawable(context, drawable) })
            }
        }
    }

    private fun setupAmount(config: AndesMoneyAmountConfiguration) {
        binding.moneyAmountText.apply {
            setTextColor(config.currencyColor.colorInt(context))
            typeface = context.getFontOrDefault(R.font.andes_font_regular)
            text = config.amountFormatted
        }
    }

    override fun getAccessibilityClassName(): CharSequence {
        return TextView::class.java.name
    }

    private fun createConfig() = AndesMoneyAmountConfigurationFactory.create(context, andesMoneyAmountAttrs)

    override fun provideText(color: Int?): SpannableStringBuilder = SpannableStringBuilder(binding.moneyAmountText.text).apply {
        if (color != null) {
            setSpan(
                ForegroundColorSpan(color),
                0,
                this.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    /**
     * Method used to expose [AndesMoneyAmount] contentDescription.
     */
    @SuppressLint("GetContentDescriptionOverride")
    override fun getContentDescription(): CharSequence? {
        return AndesMoneyAmountAccessibilityDelegate.getContentDescription(this)
    }

    /**
     * method used to intercept the click event in order to send it to the parent layout
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            return false
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private val ANDES_SIZE_DEFAULT_VALUE = AndesMoneyAmountSize.SIZE_24
        private val ANDES_TYPE_DEFAULT_VALUE = AndesMoneyAmountType.POSITIVE
        private val ANDES_STYLE_DEFAULT_VALUE = AndesMoneyAmountDecimalsStyle.NORMAL
        private const val ANDES_SHOW_ZEROS_DEFAULT_VALUE = false
        private const val DEFAULT_PADDING = 0
    }
}
