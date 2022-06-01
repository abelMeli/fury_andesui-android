package com.mercadolibre.android.andesui.amountfield

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.accessibility.AndesAmountFieldSimpleMoneyA11yEventDispatcher
import com.mercadolibre.android.andesui.amountfield.accessibility.AndesAmountFieldSimpleMoneyAccessibilityDelegate
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.factory.AndesAmountFieldSimpleMoneyAttrs
import com.mercadolibre.android.andesui.amountfield.factory.AndesAmountFieldSimpleMoneyAttrsParser
import com.mercadolibre.android.andesui.amountfield.factory.AndesAmountFieldSimpleMoneyConfigFactory
import com.mercadolibre.android.andesui.amountfield.factory.AndesAmountFieldSimpleMoneyConfiguration
import com.mercadolibre.android.andesui.amountfield.listener.OnTextChangeListener
import com.mercadolibre.android.andesui.amountfield.listener.OnTextPasteListener
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.amountfield.utils.AmountListener
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldEditText
import com.mercadolibre.android.andesui.amountfield.utils.OnTextPasteCallback
import com.mercadolibre.android.andesui.amountfield.utils.ResizingListener
import com.mercadolibre.android.andesui.amountfield.utils.TextDimensionUtils
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.databinding.AndesLayoutAmountfieldBinding
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.ScreenUtils
import com.mercadolibre.android.andesui.utils.deformatAmount
import com.mercadolibre.android.andesui.utils.formatAmount
import com.mercadolibre.android.andesui.utils.formatPastedText
import com.mercadolibre.android.andesui.utils.inputLength
import com.mercadolibre.android.andesui.utils.limitValueByMax
import java.math.BigDecimal

class AndesAmountFieldSimple : ConstraintLayout, ResizingListener, AmountListener {

    /**
     * Getter and setter for the onPaste listener.
     * This listener is called when the user performs a "Paste" event over the component
     */
    var onTextPastedListener: OnTextPasteListener? = null

    /**
     * getter and setter for the onChange listener.
     * This listener is called every time the [value] for the component changes.
     */
    var onTextChangedListener: OnTextChangeListener? = null

    /**
     * Getter and setter for the [value] of the component.
     * Pass a string value formatted as a double type. Example: "1000.50"
     * When setting a new value, only pass values greater than 0.
     * Pass null if you want to clear the current value.
     * @throws NumberFormatException when passing a string that is not formatted as double.
     */
    var value: String?
        get() = getTextInString()
        set(value) {
            formatAndAddNewValue(createConfig(), value)
            resizeComponentIfNeeded()
        }

    /**
     * Getter and setter for the [state] value
     */
    var state: AndesAmountFieldState
        get() = andesAmountFieldAttrs.andesAmountFieldState
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldState = value)
            createConfig().let {
                setupState(it)
                setupFormatter(it)
                informStateChangeToA11y(it, value)
            }
        }

    /**
     * Getter and setter for the [entryMode].
     * Pass null if you want to use the default entry mode for the selected [country]
     */
    var entryMode: AndesAmountFieldEntryMode?
        get() = andesAmountFieldAttrs.andesAmountFieldEntryMode
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldEntryMode = value)
            createConfig().let {
                setupFormatter(it)
                setupTextAlignment(it)
                setupPlaceholder(it)
                resizeComponentIfNeeded()
            }
        }

    /**
     * Getter and setter for the [entryType] value
     */
    var entryType: AndesAmountFieldEntryType
        get() = andesAmountFieldAttrs.andesAmountFieldEntryType
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldEntryType = value)
            createConfig().let {
                setupCurrencyComponent(it)
                setupSuffixComponent(it)
                resizeComponentIfNeeded()
            }
        }

    /**
     * Getter and setter for the [currency] value.
     * This property sets a default number of decimal places and the currency symbol
     */
    var currency: AndesMoneyAmountCurrency
        get() = andesAmountFieldAttrs.andesAmountFieldCurrency
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldCurrency = value)
            createConfig().let {
                setupCurrencyComponent(it)
                setupFormatter(it)
                setOnPasteCallback(it)
                resizeComponentIfNeeded()
            }
        }

    /**
     * Getter and setter for the [showCurrencyAsIsoValue].
     * This property sets if the currency component should show the symbol or the ISO value
     * of the selected [currency].
     * Example: when true and with currency argentinian peso, the value to show is "ARS", while in false,
     * it should show "$".
     */
    var showCurrencyAsIsoValue: Boolean
        get() = andesAmountFieldAttrs.andesAmountFieldShowCurrencyAsIsoValue
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldShowCurrencyAsIsoValue = value)
            setupCurrencyComponent(createConfig())
            resizeComponentIfNeeded()
        }

    /**
     * Getter and setter for the [country] value.
     * This property sets the decimal and thousands separator (comma, dot), and the default value
     * for the [entryMode].
     */
    var country: AndesCountry
        get() = andesAmountFieldAttrs.andesAmountFieldCountry
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldCountry = value)
            createConfig().let {
                setupFormatter(it)
                setupPlaceholder(it)
                setOnPasteCallback(it)
                resizeComponentIfNeeded()
            }
        }

    /**
     * Getter and setter for the [helperText] value.
     */
    var helperText: CharSequence?
        get() = andesAmountFieldAttrs.andesAmountFieldHelperText
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldHelperText = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for the [helperText] value.
     */
    var exceededHelperText: CharSequence?
        get() = andesAmountFieldAttrs.andesAmountFieldExceededHelperText
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldExceededHelperText = value)
            setupHelperComponent(createConfig())
        }

    /**
     * Getter and setter for the [suffixText] value.
     */
    var suffixText: CharSequence?
        get() = andesAmountFieldAttrs.andesAmountFieldSuffixText
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldSuffixText = value)
            setupSuffixComponent(createConfig())
            resizeComponentIfNeeded()
        }

    /**
     * Getter and setter for the [suffixA11yText].
     * When adding a [suffixText], set also this property so the TalkBack can have a proper description
     * to read when selecting the component.
     * Example: When setting a suffix like "/m2", add a [suffixA11yText] such as "por metro cuadrado",
     * or "by square meter". This value will be read instead of the [suffixText].
     *
     * Pass null if you do not need an alternative text for the [suffixText].
     */
    var suffixA11yText: String?
        get() = andesAmountFieldAttrs.andesAmountFieldSuffixA11yText
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldSuffixA11yText = value)
        }

    /**
     * Getter and setter for the [maxValue] value the component can take.
     * Pass null if you do not need a max limit for the input.
     * Pass a string value formatted as a double type. Example: "1000.50"
     * @throws NumberFormatException when passing a string that is not formatted as double.
     */
    var maxValue: String?
        get() = andesAmountFieldAttrs.andesAmountFieldMaxValue
        set(value) {
            andesAmountFieldAttrs = andesAmountFieldAttrs.copy(andesAmountFieldMaxValue = value)
            createConfig().let {
                setupFormatter(it)
                setOnPasteCallback(it)
            }
        }

    /**
     * Getter and setter for the [numberOfDecimals] value.
     * Pass null if you want to manage this property with the default value present in the selected [currency]
     */
    var numberOfDecimals: Int?
        get() = andesAmountFieldAttrs.andesAmountFieldNumberOfDecimals
        set(value) {
            andesAmountFieldAttrs =
                andesAmountFieldAttrs.copy(andesAmountFieldNumberOfDecimals = value)
            createConfig().let {
                setupFormatter(it)
                setupPlaceholder(it)
                setOnPasteCallback(it)
                resizeComponentIfNeeded()
            }
        }

    internal var size: AndesAmountFieldSize
        get() = andesAmountFieldAttrs.andesAmountFieldSize
        private set(value) {
            andesAmountFieldAttrs =
                andesAmountFieldAttrs.copy(andesAmountFieldSize = value)
            resizeInternalComponents(createConfig())
        }

    private lateinit var andesAmountFieldAttrs: AndesAmountFieldSimpleMoneyAttrs
    private lateinit var internalEditText: AndesAmountFieldEditText
    private lateinit var suffixTextView: AndesTextView
    private lateinit var helperTextView: AndesTextView
    private lateinit var helperIcon: AndesBadgeIconPill
    private lateinit var currencyTextView: AndesTextView
    private lateinit var internalFormatter: TextWatcher
    private var maxComponentWidth: Int = 0
    private val binding by lazy {
        AndesLayoutAmountfieldBinding.inflate(LayoutInflater.from(context), this)
    }
    private val a11yEventDispatcher by lazy {
        AndesAmountFieldSimpleMoneyA11yEventDispatcher()
    }

    /**
     * Default xml constructor
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    /**
     * Default code constructor
     */
    constructor(
        context: Context,
        state: AndesAmountFieldState = AndesAmountFieldState.Idle,
        entryMode: AndesAmountFieldEntryMode? = null,
        entryType: AndesAmountFieldEntryType = AndesAmountFieldEntryType.MONEY,
        currency: AndesMoneyAmountCurrency = AndesMoneyAmountCurrency.ARS,
        showCurrencyAsIsoValue: Boolean = false,
        country: AndesCountry = AndesCountry.AR,
        numberOfDecimals: Int? = null,
        initialValue: String? = null,
        helperText: CharSequence? = null,
        suffix: CharSequence? = null,
        suffixA11yText: String? = null,
        maxValue: String? = null
    ) : this(
        context,
        state,
        entryMode,
        entryType,
        currency,
        showCurrencyAsIsoValue,
        country,
        numberOfDecimals,
        initialValue,
        helperText,
        null,
        suffix,
        suffixA11yText,
        maxValue
    )

    /**
     * Default code constructor
     */
    constructor(
        context: Context,
        state: AndesAmountFieldState = AndesAmountFieldState.Idle,
        entryMode: AndesAmountFieldEntryMode? = null,
        entryType: AndesAmountFieldEntryType = AndesAmountFieldEntryType.MONEY,
        currency: AndesMoneyAmountCurrency = AndesMoneyAmountCurrency.ARS,
        showCurrencyAsIsoValue: Boolean = false,
        country: AndesCountry = AndesCountry.AR,
        numberOfDecimals: Int? = null,
        initialValue: String? = null,
        helperText: CharSequence? = null,
        exceededHelperText: CharSequence? = null,
        suffix: CharSequence? = null,
        suffixA11yText: String? = null,
        maxValue: String? = null
    ) : super(context) {
        initAttrs(
            state,
            entryMode,
            entryType,
            SIZE_DEFAULT,
            currency,
            showCurrencyAsIsoValue,
            country,
            numberOfDecimals,
            initialValue,
            helperText,
            exceededHelperText,
            suffix,
            suffixA11yText,
            maxValue
        )
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        andesAmountFieldAttrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrs)
        setupComponents(createConfig())
    }

    private fun initAttrs(
        state: AndesAmountFieldState,
        entryMode: AndesAmountFieldEntryMode?,
        entryType: AndesAmountFieldEntryType,
        size: AndesAmountFieldSize,
        currency: AndesMoneyAmountCurrency,
        showCurrencyAsIsoValue: Boolean,
        country: AndesCountry,
        numberOfDecimals: Int?,
        initialValue: String?,
        helperText: CharSequence?,
        exceededHelperText: CharSequence?,
        suffix: CharSequence?,
        suffixA11yText: String?,
        maxValue: String?
    ) {
        andesAmountFieldAttrs = AndesAmountFieldSimpleMoneyAttrs(
            state,
            entryMode,
            entryType,
            size,
            currency,
            showCurrencyAsIsoValue,
            country,
            numberOfDecimals,
            initialValue,
            helperText,
            exceededHelperText,
            suffix,
            suffixA11yText,
            maxValue
        )
        setupComponents(createConfig())
    }

    private fun setupComponents(config: AndesAmountFieldSimpleMoneyConfiguration) {
        initComponents()
        setupViewId()
        setupInternalEditText(config)
        setupCurrencyComponent(config)
        setupSuffixComponent(config)
        setupSuffixColor(config)
        setupHelperComponent(config)
        callStateActions(config)
        setupInternalListeners(config)
        setupAccessibility()
    }

    private fun initComponents() {
        internalEditText = binding.amountFieldEditText
        suffixTextView = binding.amountFieldSuffixText
        helperTextView = binding.amountFieldHelperText
        helperIcon = binding.amountFieldHelperIcon
        currencyTextView = binding.amountFieldCurrencySymbol
    }

    private fun setupViewId() {
        if (id == NO_ID) {
            id = View.generateViewId()
        }
    }

    private fun setupInternalEditText(config: AndesAmountFieldSimpleMoneyConfiguration) {
        setupFormatter(config)
        setupPlaceholder(config)
        setupTypeface()
        setupInitialValue(config)
        setupTextAlignment(config)
    }

    /**
     * Only one formatter at a time is allowed. Since the [internalEditText] can accept more than one
     * textWatchers, we need to monitor when the formatter needs to be replaced, so we remove the
     * value previously set before adding a new one.
     */
    private fun setupFormatter(config: AndesAmountFieldSimpleMoneyConfiguration) {
        if (this::internalFormatter.isInitialized) {
            internalEditText.removeTextChangedListener(internalFormatter)
        }
        internalFormatter = config.formatter
        internalEditText.addTextChangedListener(internalFormatter)
    }

    private fun setupPlaceholder(config: AndesAmountFieldSimpleMoneyConfiguration) {
        internalEditText.hint = config.placeholder
    }

    private fun setupTypeface() {
        internalEditText.typeface = context.getFontOrDefault(R.font.andes_font_regular)
    }

    private fun setupInitialValue(config: AndesAmountFieldSimpleMoneyConfiguration) {
        value = config.initialValue
    }

    private fun setupTextAlignment(config: AndesAmountFieldSimpleMoneyConfiguration) {
        internalEditText.textAlignment = config.textAlignment
    }

    /**
     * Setter needed to capture the paste event over the editText component
     */
    private fun setOnPasteCallback(config: AndesAmountFieldSimpleMoneyConfiguration) {
        internalEditText.onTextPasteCallback = object : OnTextPasteCallback {
            override fun onTextPaste(pastedText: CharSequence) {
                val formattedText = formatPastedText(config, pastedText)
                val newValue = formattedText.deformatAmount(config.decimalSeparator, config.numberOfDecimals)
                onTextPastedListener?.onTextPasted(newValue)
                forceNewValue(formattedText, maxValue?.let { newValue.toBigDecimal() > it.toBigDecimal() } ?: false)
                resizeComponentIfNeeded()
            }
        }
    }

    private fun formatPastedText(
        config: AndesAmountFieldSimpleMoneyConfiguration,
        pastedText: CharSequence
    ): String {
        val formattedText = pastedText.formatPastedText(config.decimalSeparator)

        config.maxValue?.let { maxvalue ->
            if (formattedText.toBigDecimal() > maxvalue.toBigDecimal()) {
                state = AndesAmountFieldState.AmountExceeded
            }
            return formattedText.limitValueByMax(
                maxvalue,
                config.decimalSeparator,
                config.numberOfDecimals
            )
        }

        return formattedText.formatAmount(config.decimalSeparator, config.numberOfDecimals)
    }

    private fun setupAccessibility() {
        isFocusable = true
        accessibilityDelegate = AndesAmountFieldSimpleMoneyAccessibilityDelegate(this)
    }

    private fun setupSuffixComponent(config: AndesAmountFieldSimpleMoneyConfiguration) {
        suffixTextView.apply {
            text = config.suffixText
        }
    }

    private fun setupCurrencyComponent(config: AndesAmountFieldSimpleMoneyConfiguration) {
        currencyTextView.apply {
            text = config.currencySymbol
            visibility = config.currencyVisibility
        }
    }

    private fun setupInternalListeners(config: AndesAmountFieldSimpleMoneyConfiguration) {
        setOnPasteCallback(config)
        setInternalClickListener()
    }

    private fun setInternalClickListener() {
        setOnClickListener {
            internalEditText.requestFocus()
        }
    }

    private fun informStateChangeToA11y(
        config: AndesAmountFieldSimpleMoneyConfiguration,
        state: AndesAmountFieldState
    ) {
        a11yEventDispatcher.notifyNewStateToA11y(this, state, config.helperText)
    }

    /**
     * Function called every time the text in the component changes.
     * We need this method to be able to resize the view when the text does not fit in the screen.
     * This method also colorizes the currency symbol and the suffix text according to the chars entered in the field.
     */
    override fun resizeComponentIfNeeded() {
        val newText = internalEditText.text?.toString() ?: ""
        val config = createConfig()
        val textToMeasure = newText.takeIf { it.isNotEmpty() } ?: config.placeholder
        val currencyText = config.currencySymbol
        val suffixText = config.suffixText ?: ""

        AndesAmountFieldSize.values().forEach { size ->
            val textWidth = TextDimensionUtils.getStringWidth(
                context,
                size,
                textToMeasure.toString()
            )
            val nonEditableViewsWidth = TextDimensionUtils.getNonEditableViewsTotalWidth(
                context,
                currencyText.toString(),
                suffixText.toString(),
                size
            )

            val totalComponentWidth = textWidth + nonEditableViewsWidth
            if (totalComponentWidth <= maxComponentWidth) {
                this.size = size
                return@resizeComponentIfNeeded
            }
        }
    }

    /**
     * We need this method to be able to hide the placeholder when the component has text.
     * This is needed since the internal edittext min width dimension is affected by the length of
     * the placeholder, and in the cases that the entered text is smaller than the placeholder,
     * the component does not shrink to fit the entered text but mantains the (not visible) placeholder size.
     */
    private fun hidePlaceholderWhenTextPresent(config: AndesAmountFieldSimpleMoneyConfiguration, textLength: Int) {
        if (textLength > 0) {
            internalEditText.hint = ""
        } else {
            setupPlaceholder(config)
        }
    }

    private fun setupCurrencyColor() {
        if (internalEditText.inputLength() == 0) {
            currencyTextView.setTextColor(AndesTextViewColor.Secondary)
        } else {
            currencyTextView.setTextColor(AndesTextViewColor.Primary)
        }
    }

    private fun setupSuffixColor(config: AndesAmountFieldSimpleMoneyConfiguration) {
        if (internalEditText.inputLength() == 0) {
            suffixTextView.setTextColor(config.suffixTextColorForEmptyField)
        } else {
            suffixTextView.setTextColor(config.suffixTextColor)
        }
    }

    private fun setupMaxComponentWidth() {
        maxComponentWidth = getMaxComponentWidth()
        resizeComponentIfNeeded()
    }

    private fun getMaxComponentWidth(): Int {
        val layoutParams = layoutParams as? LayoutParams
        val horizontalMargin = layoutParams?.leftMargin?.plus(layoutParams.rightMargin) ?: 0
        val screenWidth = ScreenUtils.getScreenWidth()
        return screenWidth - horizontalMargin
    }

    private fun resizeInternalComponents(config: AndesAmountFieldSimpleMoneyConfiguration) {
        internalEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.editTextSize)
        currencyTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.currencyTextSize)
        suffixTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, config.suffixTextSize)
        setupMargins(config)
    }

    private fun setupMargins(config: AndesAmountFieldSimpleMoneyConfiguration) {
        setupCurrencyMargin(config)
        setupEditTextMargin(config)
    }

    private fun setupEditTextMargin(config: AndesAmountFieldSimpleMoneyConfiguration) {
        val params = internalEditText.layoutParams as LayoutParams
        params.marginEnd = config.resizableComponentsHorizontalMargin
        internalEditText.layoutParams = params
    }

    private fun setupCurrencyMargin(config: AndesAmountFieldSimpleMoneyConfiguration) {
        val params = currencyTextView.layoutParams as LayoutParams
        params.marginEnd = config.resizableComponentsHorizontalMargin
        currencyTextView.layoutParams = params
    }

    /**
     * Callback triggered every time the user changes the component value
     */
    override fun onAmountChanged(isExceeded: Boolean) {
        val config = createConfig()
        val valueFormattedToBigDecimalString =
            internalEditText
                .text?.deformatAmount(config.decimalSeparator, config.numberOfDecimals)

        hidePlaceholderWhenTextPresent(config, internalEditText.inputLength())
        setupCurrencyColor()
        setupSuffixColor(config)

        onTextChangedListener?.onTextChanged(valueFormattedToBigDecimalString)

        config.maxValue?.let {
            if (isExceeded) {
                if (state != AndesAmountFieldState.AmountExceeded) {
                    state = AndesAmountFieldState.AmountExceeded
                }
            } else {
                if (state == AndesAmountFieldState.AmountExceeded) {
                    state = AndesAmountFieldState.Idle
                }
            }
        }
    }

    private fun getTextInString(): String? {
        val config = createConfig()
        return internalEditText.text?.deformatAmount(config.decimalSeparator, config.numberOfDecimals)
    }

    private fun formatAndAddNewValue(config: AndesAmountFieldSimpleMoneyConfiguration, newValue: String?) {
        internalEditText.text?.clear()

        newValue?.let {
            if (isValidInput(it)) {
                // format string
                val formattedValue =
                    it.formatAmount(config.decimalSeparator, config.numberOfDecimals)
                forceNewValue(formattedValue, false)
            }
        }
    }

    private fun isValidInput(newValue: String): Boolean {
        maxValue?.let {
            return newValue.toBigDecimal() >= ZERO && newValue.toBigDecimal() <= it.toBigDecimal()
        }
        return newValue.toBigDecimal() >= ZERO
    }

    /**
     * Takes an already formatted value and passes into the internal edittext as it is.
     */
    private fun forceNewValue(newValue: String, isExceeded: Boolean) {
        internalEditText.apply {
            text?.clear()
            removeTextChangedListener(internalFormatter)
            append(newValue)
            onAmountChanged(isExceeded)
            addTextChangedListener(internalFormatter)
        }
    }

    private fun setupState(config: AndesAmountFieldSimpleMoneyConfiguration) {
        setupHelperComponent(config)
        callStateActions(config)
    }

    private fun setupHelperComponent(config: AndesAmountFieldSimpleMoneyConfiguration) {
        setupHelperText(config)
        setupHelperIcon(config)
    }

    private fun setupHelperText(config: AndesAmountFieldSimpleMoneyConfiguration) {
        helperTextView.apply {
            text = config.helperText
            setTypeface(typeface, config.helperTextStyle)
            setTextColor(config.helperTextColor)
        }
    }

    private fun setupHelperIcon(config: AndesAmountFieldSimpleMoneyConfiguration) {
        helperIcon.visibility = config.helperIconVisibility
    }

    private fun callStateActions(config: AndesAmountFieldSimpleMoneyConfiguration) {
        config.stateActions.invoke(binding.amountFieldResizableGroup)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupMaxComponentWidth()
    }

    private fun createConfig() = AndesAmountFieldSimpleMoneyConfigFactory.create(
        context = context,
        resizingListener = this,
        amountListener = this,
        attrs = andesAmountFieldAttrs
    )

    private companion object {
        private val ZERO = BigDecimal(0)
        private val SIZE_DEFAULT = AndesAmountFieldSize.LARGE
    }
}
