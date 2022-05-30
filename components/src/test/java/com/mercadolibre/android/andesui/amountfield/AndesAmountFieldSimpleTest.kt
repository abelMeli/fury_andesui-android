package com.mercadolibre.android.andesui.amountfield

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.listener.OnTextChangeListener
import com.mercadolibre.android.andesui.amountfield.listener.OnTextPasteListener
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.buildAttributeSet
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.getSuffixComponent
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import com.mercadolibre.android.andesui.utils.configureTextDimensionUtilsExtraSmallText
import com.mercadolibre.android.andesui.utils.configureTextDimensionUtilsLargeText
import com.mercadolibre.android.andesui.utils.configureTextDimensionUtilsMediumText
import com.mercadolibre.android.andesui.utils.configureTextDimensionUtilsSmallText
import com.mercadolibre.android.andesui.utils.emulateCopyEventToClipboard
import com.mercadolibre.android.andesui.utils.emulateDeleteKeyPressed
import com.mercadolibre.android.andesui.utils.emulatePasteEvent
import com.mercadolibre.android.andesui.utils.emulateTypingWithKeyboard
import com.mercadolibre.android.andesui.utils.getInternalEditTextComponent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesAmountFieldSimpleTest {

    private lateinit var context: Context
    private lateinit var amountField: AndesAmountFieldSimple

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `given creation by code with all set values, then all getters return correct values`() {
        amountField = AndesAmountFieldSimple(
            context,
            AndesAmountFieldState.Error,
            AndesAmountFieldEntryMode.DECIMAL,
            AndesAmountFieldEntryType.MONEY,
            AndesMoneyAmountCurrency.MXN,
            true,
            AndesCountry.MX,
            3,
            "1000.00",
            "helper text",
            "suffix text",
            "suffix text for a11y",
            "10000.00"
        )

        amountField.state assertEquals AndesAmountFieldState.Error
        amountField.entryMode assertEquals AndesAmountFieldEntryMode.DECIMAL
        amountField.entryType assertEquals AndesAmountFieldEntryType.MONEY
        amountField.currency assertEquals AndesMoneyAmountCurrency.MXN
        amountField.showCurrencyAsIsoValue assertEquals true
        amountField.country assertEquals AndesCountry.MX
        amountField.numberOfDecimals assertEquals 3
        amountField.value assertEquals "1000.000"
        amountField.helperText assertEquals "helper text"
        amountField.suffixText assertEquals "suffix text"
        amountField.suffixA11yText assertEquals "suffix text for a11y"
        amountField.maxValue assertEquals "10000.00"
    }

    @Test
    fun `given creation by code with all default values, then all getters return correct values`() {
        amountField = AndesAmountFieldSimple(context)

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.entryMode assertIsNull true
        amountField.entryType assertEquals AndesAmountFieldEntryType.MONEY
        amountField.currency assertEquals AndesMoneyAmountCurrency.ARS
        amountField.showCurrencyAsIsoValue assertEquals false
        amountField.country assertEquals AndesCountry.AR
        amountField.numberOfDecimals assertIsNull true
        amountField.value assertEquals "0.00"
        amountField.helperText assertIsNull true
        amountField.suffixText assertIsNull true
        amountField.suffixA11yText assertIsNull true
        amountField.maxValue assertIsNull true
    }

    @Test
    fun `given creation by code with all default values, when setting new values, then all getters return correct values`() {
        amountField = AndesAmountFieldSimple(context)

        amountField.apply {
            state = AndesAmountFieldState.Error
            entryMode = AndesAmountFieldEntryMode.DECIMAL
            entryType = AndesAmountFieldEntryType.PERCENTAGE
            currency = AndesMoneyAmountCurrency.BRL
            showCurrencyAsIsoValue = true
            country = AndesCountry.BR
            numberOfDecimals = 3
            value = "1000.001"
            helperText = "helper text"
            suffixText = "suffix text"
            suffixA11yText = "suffix text for a11y"
            maxValue = "10000.00"
        }

        amountField.state assertEquals AndesAmountFieldState.Error
        amountField.entryMode assertEquals AndesAmountFieldEntryMode.DECIMAL
        amountField.entryType assertEquals AndesAmountFieldEntryType.PERCENTAGE
        amountField.currency assertEquals AndesMoneyAmountCurrency.BRL
        amountField.showCurrencyAsIsoValue assertEquals true
        amountField.country assertEquals AndesCountry.BR
        amountField.numberOfDecimals assertEquals 3
        amountField.value assertEquals "1000.001"
        amountField.helperText assertEquals "helper text"
        amountField.suffixText assertEquals "suffix text"
        amountField.suffixA11yText assertEquals "suffix text for a11y"
        amountField.maxValue assertEquals "10000.00"
    }

    @Test
    fun `given creation by xml with all set values, then all getters return correct values`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4012")
            addAttribute(R.attr.andesAmountFieldCountry, "5000")
            addAttribute(R.attr.andesAmountFieldShowCurrencyAsIsoValue, "true")
            addAttribute(R.attr.andesAmountFieldState, "1001")
            addAttribute(R.attr.andesAmountFieldEntryMode, "2001")
            addAttribute(R.attr.andesAmountFieldEntryType, "7001")
            addAttribute(R.attr.andesAmountFieldNumberOfDecimals, "3")
            addAttribute(R.attr.andesAmountFieldInitialValue, "1000.00")
            addAttribute(R.attr.andesAmountFieldHelperText, "helper")
            addAttribute(R.attr.andesAmountFieldSuffixText, "suffix")
            addAttribute(R.attr.andesAmountFieldSuffixA11yText, "suffix for a11y")
            addAttribute(R.attr.andesAmountFieldMaxValue, "10000.00")
        }

        amountField = AndesAmountFieldSimple(context, attrSet)

        amountField.state assertEquals AndesAmountFieldState.Error
        amountField.entryMode assertEquals AndesAmountFieldEntryMode.DECIMAL
        amountField.entryType assertEquals AndesAmountFieldEntryType.PERCENTAGE
        amountField.currency assertEquals AndesMoneyAmountCurrency.ARS
        amountField.showCurrencyAsIsoValue assertEquals true
        amountField.country assertEquals AndesCountry.AR
        amountField.numberOfDecimals assertEquals 3
        amountField.value assertEquals "1000.000"
        amountField.helperText assertEquals "helper"
        amountField.suffixText assertEquals "suffix"
        amountField.suffixA11yText assertEquals "suffix for a11y"
        amountField.maxValue assertEquals "10000.00"
    }

    @Test
    fun `given creation by xml with all default values, then all getters return correct values`() {
        val attrSet = buildAttributeSet { }
        amountField = AndesAmountFieldSimple(context, attrSet)

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.entryMode assertIsNull true
        amountField.entryType assertEquals AndesAmountFieldEntryType.MONEY
        amountField.currency assertEquals AndesMoneyAmountCurrency.ARS
        amountField.showCurrencyAsIsoValue assertEquals false
        amountField.country assertEquals AndesCountry.AR
        amountField.numberOfDecimals assertIsNull true
        amountField.value assertEquals "0.00"
        amountField.helperText assertIsNull true
        amountField.suffixText assertIsNull true
        amountField.suffixA11yText assertIsNull true
        amountField.maxValue assertIsNull true
    }

    @Test
    fun `given creation by xml with all default values, when setting new values, then all getters return correct values`() {
        val attrSet = buildAttributeSet { }
        amountField = AndesAmountFieldSimple(context, attrSet)

        amountField.apply {
            state = AndesAmountFieldState.Error
            entryMode = AndesAmountFieldEntryMode.DECIMAL
            entryType = AndesAmountFieldEntryType.PERCENTAGE
            currency = AndesMoneyAmountCurrency.BRL
            showCurrencyAsIsoValue = true
            country = AndesCountry.BR
            numberOfDecimals = 3
            value = "1000.001"
            helperText = "helper text"
            suffixText = "suffix text"
            suffixA11yText = "suffix text for a11y"
            maxValue = "10000.00"
        }

        amountField.state assertEquals AndesAmountFieldState.Error
        amountField.entryMode assertEquals AndesAmountFieldEntryMode.DECIMAL
        amountField.entryType assertEquals AndesAmountFieldEntryType.PERCENTAGE
        amountField.currency assertEquals AndesMoneyAmountCurrency.BRL
        amountField.showCurrencyAsIsoValue assertEquals true
        amountField.country assertEquals AndesCountry.BR
        amountField.numberOfDecimals assertEquals 3
        amountField.value assertEquals "1000.001"
        amountField.helperText assertEquals "helper text"
        amountField.suffixText assertEquals "suffix text"
        amountField.suffixA11yText assertEquals "suffix text for a11y"
        amountField.maxValue assertEquals "10000.00"
    }

    @Test
    fun `given empty component with money type, when setting a value, then currency color is correctly set`() {
        amountField = AndesAmountFieldSimple(context)

        amountField.value = "1000.00"

        amountField.getSuffixComponent().getTextColor() assertEquals AndesTextViewColor.Secondary
    }

    @Test
    fun `given empty component with money type, when setting and deleting a value, then currency color is correctly set`() {
        amountField = AndesAmountFieldSimple(context)

        amountField.emulateTypingWithKeyboard("1")
        amountField.emulateDeleteKeyPressed()

        amountField.getSuffixComponent().getTextColor() assertEquals AndesTextViewColor.Secondary
    }

    @Test
    fun `given empty component with percentage type, when setting a value, then currency color is correctly set`() {
        amountField = AndesAmountFieldSimple(context = context, entryType = AndesAmountFieldEntryType.PERCENTAGE)

        amountField.value = "1000.00"

        amountField.getSuffixComponent().getTextColor() assertEquals AndesTextViewColor.Primary
    }

    @Test
    fun `given empty component with percentage type, when setting and deleting a value, then currency color is correctly set`() {
        amountField = AndesAmountFieldSimple(context = context, entryType = AndesAmountFieldEntryType.PERCENTAGE)

        amountField.emulateTypingWithKeyboard("1")
        amountField.emulateDeleteKeyPressed()

        amountField.getSuffixComponent().getTextColor() assertEquals AndesTextViewColor.Secondary
    }

    @Test
    fun `given component, when setting a negative value, then new value is ignored`() {
        amountField = AndesAmountFieldSimple(context)

        amountField.value = "-1000.00"

        amountField.value assertEquals "0.00"
    }

    @Test
    fun `given component with maxvalue, when setting a value bigger than maxvalue, then new value is ignored`() {
        amountField = AndesAmountFieldSimple(context)
        amountField.maxValue = "1000.00"

        amountField.value = "2000.00"

        amountField.value assertEquals "0.00"
    }

    @Test
    fun `given component with textchange callback, when entering text, callback is called correctly`() {
        amountField = AndesAmountFieldSimple(context)
        val textChangeCallback = provideTextChangeCallback()
        amountField.onTextChangedListener = textChangeCallback

        amountField.emulateTypingWithKeyboard("123")

        amountField.onTextChangedListener assertIsNull false
        verify(textChangeCallback, times(3)).onTextChanged(any())
    }

    @Test
    fun `given component with textchange callback, when adding value by setter, callback is called correctly`() {
        amountField = AndesAmountFieldSimple(context)
        val textChangeCallback = provideTextChangeCallback()
        amountField.onTextChangedListener = textChangeCallback

        amountField.value = "123"

        amountField.onTextChangedListener assertIsNull false
        verify(textChangeCallback, times(1)).onTextChanged(any())
    }

    @Test
    fun `given component with textchange callback, when pasting a new value, callback is called correctly`() {
        amountField = AndesAmountFieldSimple(context)
        val textChangeCallback = provideTextChangeCallback()
        amountField.onTextChangedListener = textChangeCallback
        val internalEditText = amountField.getInternalEditTextComponent()

        context.emulateCopyEventToClipboard("123")
        internalEditText.emulatePasteEvent()

        amountField.onTextChangedListener assertIsNull false
        verify(textChangeCallback, times(1)).onTextChanged(any())
    }

    @Test
    fun `given component with onpaste callback, when pasting a new value, callback is called correctly`() {
        amountField = AndesAmountFieldSimple(context)
        val onPasteCallback = provideOnPasteCallback()
        amountField.onTextPastedListener = onPasteCallback
        val internalEditText = amountField.getInternalEditTextComponent()

        context.emulateCopyEventToClipboard("123")
        internalEditText.emulatePasteEvent()

        verify(onPasteCallback, times(1)).onTextPasted(any())
    }

    @Test
    fun `given component with maxvalue set, when reaching max value, then state is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )

        amountField.emulateTypingWithKeyboard("1234")

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
        amountField.value assertEquals "1234"
    }

    @Test
    fun `given component with maxvalue set and entrymode decimal, when reaching max value, then state is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            entryMode = AndesAmountFieldEntryMode.DECIMAL,
            maxValue = "1000.00"
        )

        amountField.emulateTypingWithKeyboard("123400")

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
        amountField.value assertEquals "1234.00"
    }

    @Test
    fun `given component with maxvalue set, when reaching max value and entering more values, then input is blocked`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )

        amountField.emulateTypingWithKeyboard("123456")

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
        amountField.value assertEquals "1234"
    }

    @Test
    fun `given component with maxvalue set and entrymode decimal, when reaching max value and entering more values, then input is blocked`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            entryMode = AndesAmountFieldEntryMode.DECIMAL,
            maxValue = "1000.00"
        )

        amountField.emulateTypingWithKeyboard("12345678")

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
        amountField.value assertEquals "1234.56"
    }

    @Test
    fun `given component with maxvalue set and entrymode int, when reaching max value and change state after type, then input is blocked`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            entryMode = AndesAmountFieldEntryMode.DECIMAL,
            maxValue = "1000.00"
        )

        amountField.onTextChangedListener = object : OnTextChangeListener {
            override fun onTextChanged(newText: String?) {
                if (newText!!.toBigDecimal() > amountField.maxValue!!.toBigDecimal()) {
                    amountField.state = AndesAmountFieldState.Error
                }
            }
        }

        amountField.emulateTypingWithKeyboard("12345678")

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
        amountField.value assertEquals "1234.56"
    }

    @Test
    fun `given component with maxvalue reached, when deleting text, then state is restored to idle`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        amountField.emulateTypingWithKeyboard("1234")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "123"
    }

    @Test
    fun `given component with maxvalue reached and entrymode decimal, when deleting text, then state is restored to idle`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            entryMode = AndesAmountFieldEntryMode.DECIMAL,
            maxValue = "1000.00"
        )
        amountField.emulateTypingWithKeyboard("123400")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "123.40"
    }

    @Test
    fun `given component with maxvalue reached, when deleting text, then value is correct`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "100.00"
        )
        amountField.emulateTypingWithKeyboard("101")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "10"
    }

    @Test
    fun `given component with maxvalue reached and entrymode decimal, when deleting text, then value is correct`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            entryMode = AndesAmountFieldEntryMode.DECIMAL,
            maxValue = "100.00"
        )
        amountField.emulateTypingWithKeyboard("10100")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "10.10"
    }

    @Test
    fun `given component with maxvalue reached with decimal separator, when deleting text, then value is correct`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "100.00"
        )
        amountField.emulateTypingWithKeyboard("100.2")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "100."
    }

    @Test
    fun `given component with maxvalue reached with thousand separator, when deleting text, then value is correct`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        amountField.emulateTypingWithKeyboard("1010")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "101"
    }

    @Test
    fun `given component with maxvalue reached with thousand separator and decimal value, when deleting text, then value is correct`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        amountField.emulateTypingWithKeyboard("1000.1")

        amountField.emulateDeleteKeyPressed()

        amountField.state assertEquals AndesAmountFieldState.Idle
        amountField.value assertEquals "1000."
    }

    @Test
    fun `given component with maxvalue set and value in clipboard, when pasting value bigger than maxvalue, then state is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        context.emulateCopyEventToClipboard("1234")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.state assertEquals AndesAmountFieldState.AmountExceeded
    }

    @Test
    fun `given component with maxvalue set and value in clipboard, when pasting value bigger than maxvalue, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        context.emulateCopyEventToClipboard("1234567")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "1234.00"
    }

    @Test
    fun `given component with maxvalue set and value in clipboard, when pasting value with decimals bigger than maxvalue, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        context.emulateCopyEventToClipboard("1234.567")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "1234.00"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value with non number chars smaller than maxvalue, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        context.emulateCopyEventToClipboard("asd123,456qwe")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "123.45"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value with non number chars bigger than max value, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            maxValue = "1000.00"
        )
        context.emulateCopyEventToClipboard("asd1233,456qwe")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "1233.00"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value with non number chars, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(context = context)
        context.emulateCopyEventToClipboard("asd123334,456qwe")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "123334.45"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context,
            numberOfDecimals = 3
        )
        context.emulateCopyEventToClipboard("123.456,789")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "123456.789"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value without numbers, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context
        )
        context.emulateCopyEventToClipboard("asdqwe")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "0.00"
    }

    @Test
    fun `given component with value in clipboard, when pasting a value without numbers and a decimal separator, then value is set correctly`() {
        amountField = AndesAmountFieldSimple(
            context = context
        )
        context.emulateCopyEventToClipboard("asdqwe,")

        amountField.getInternalEditTextComponent().emulatePasteEvent()

        amountField.value assertEquals "0.00"
    }

    @Test
    @Config(qualifiers = "w640dp")
    fun `given component, when setting a value which fits with LARGE size, then size value is correct`() {
        amountField = AndesAmountFieldSimple(context)
        setupActivityForTesting()
        configureTextDimensionUtilsLargeText(context)

        amountField.value = "1300.00"

        amountField.size assertEquals AndesAmountFieldSize.LARGE
    }

    @Test
    @Config(qualifiers = "w640dp")
    fun `given component, when setting a value which does not fit with LARGE size, then size value is correct`() {
        amountField = AndesAmountFieldSimple(context)
        setupActivityForTesting()
        configureTextDimensionUtilsMediumText(context)

        amountField.value = "130000.45"

        amountField.size assertEquals AndesAmountFieldSize.MEDIUM
    }

    @Test
    @Config(qualifiers = "w640dp")
    fun `given component, when setting a value which does not fit with MEDIUM size, then size value is correct`() {
        amountField = AndesAmountFieldSimple(context)
        setupActivityForTesting()
        configureTextDimensionUtilsSmallText(context)

        amountField.value = "130000000.45"

        amountField.size assertEquals AndesAmountFieldSize.SMALL
    }

    @Test
    @Config(qualifiers = "w640dp")
    fun `given component, when setting a value which does not fit with SMALL size, then size value is correct`() {
        amountField = AndesAmountFieldSimple(context)
        setupActivityForTesting()
        configureTextDimensionUtilsExtraSmallText(context)

        amountField.value = "1300000000000.45"

        amountField.size assertEquals AndesAmountFieldSize.EXTRA_SMALL
    }

    private fun provideTextChangeCallback(): OnTextChangeListener {
        return spy(
            object : OnTextChangeListener {
                override fun onTextChanged(newText: String?) {
                    // no-op
                }
            }
        )
    }

    private fun provideOnPasteCallback(): OnTextPasteListener {
        return spy(
            object : OnTextPasteListener {
                override fun onTextPasted(pastedText: String?) {
                    // no-op
                }
            }
        )
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(amountField)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

}
