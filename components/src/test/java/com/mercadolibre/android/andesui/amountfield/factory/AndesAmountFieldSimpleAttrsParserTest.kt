package com.mercadolibre.android.andesui.amountfield.factory

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.buildAttributeSet
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesAmountFieldSimpleAttrsParserTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesAmountFieldSimpleMoneyAttrs

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `parse with nothing set in the xml`() {
        val attrSet = buildAttributeSet { }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.ARS
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.AR
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `currency ARS, showIso true, country AR, state IDLE, entryMode INT, entryType MONEY, decimals 3, initialValue 1000,00, helper, suffix, suffix for a11y, maxValue 10000,00`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4012")
            addAttribute(R.attr.andesAmountFieldShowCurrencyAsIsoValue, "true")
            addAttribute(R.attr.andesAmountFieldCountry, "5000")
            addAttribute(R.attr.andesAmountFieldState, "1000")
            addAttribute(R.attr.andesAmountFieldEntryMode, "2000")
            addAttribute(R.attr.andesAmountFieldEntryType, "7000")
            addAttribute(R.attr.andesAmountFieldNumberOfDecimals, "3")
            addAttribute(R.attr.andesAmountFieldInitialValue, "1000.00")
            addAttribute(R.attr.andesAmountFieldHelperText, "helper")
            addAttribute(R.attr.andesAmountFieldSuffixText, "suffix")
            addAttribute(R.attr.andesAmountFieldSuffixA11yText, "suffix for a11y")
            addAttribute(R.attr.andesAmountFieldMaxValue, "10000.00")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertEquals AndesAmountFieldEntryMode.INT
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.ARS
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals true
        attrs.andesAmountFieldCountry assertEquals AndesCountry.AR
        attrs.andesAmountFieldNumberOfDecimals assertEquals 3
        attrs.andesAmountFieldInitialValue assertEquals "1000.00"
        attrs.andesAmountFieldHelperText assertEquals "helper"
        attrs.andesAmountFieldSuffixText assertEquals "suffix"
        attrs.andesAmountFieldSuffixA11yText assertEquals "suffix for a11y"
        attrs.andesAmountFieldMaxValue assertEquals "10000.00"
    }

    @Test
    fun `currency BRL, showIso not set, country BR, state ERROR, entryMode DECIMAL, entryType PERCENTAGE, decimals null, initialValue null, helper null, suffix null, suffix for a11y null, maxValue null`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4000")
            addAttribute(R.attr.andesAmountFieldCountry, "5001")
            addAttribute(R.attr.andesAmountFieldState, "1001")
            addAttribute(R.attr.andesAmountFieldEntryMode, "2001")
            addAttribute(R.attr.andesAmountFieldEntryType, "7001")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Error
        attrs.andesAmountFieldEntryMode assertEquals AndesAmountFieldEntryMode.DECIMAL
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.PERCENTAGE
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.BRL
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.BR
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency UYU, country CL`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4001")
            addAttribute(R.attr.andesAmountFieldCountry, "5002")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.UYU
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CL
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency CLP, country CO`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4002")
            addAttribute(R.attr.andesAmountFieldCountry, "5003")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.CLP
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CO
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency CLF, country MX`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4003")
            addAttribute(R.attr.andesAmountFieldCountry, "5004")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.CLF
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.MX
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency MXN, country CR`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4004")
            addAttribute(R.attr.andesAmountFieldCountry, "5005")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.MXN
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CR
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency DOP, country PE`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4005")
            addAttribute(R.attr.andesAmountFieldCountry, "5006")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.DOP
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.PE
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency PAB, country EC`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4006")
            addAttribute(R.attr.andesAmountFieldCountry, "5007")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.PAB
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.EC
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency COP, country PA`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4007")
            addAttribute(R.attr.andesAmountFieldCountry, "5008")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.COP
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.PA
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency VEF, country DO`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4008")
            addAttribute(R.attr.andesAmountFieldCountry, "5009")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.VEF
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.DO
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency EUR, country UY`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4009")
            addAttribute(R.attr.andesAmountFieldCountry, "5010")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.EUR
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.UY
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency PEN, country VE`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4010")
            addAttribute(R.attr.andesAmountFieldCountry, "5011")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.PEN
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.VE
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency CRC, country BO`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4011")
            addAttribute(R.attr.andesAmountFieldCountry, "5012")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.CRC
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.BO
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency ARS, country PY`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4012")
            addAttribute(R.attr.andesAmountFieldCountry, "5013")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.ARS
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.PY
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency USD, country GT`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4013")
            addAttribute(R.attr.andesAmountFieldCountry, "5014")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.USD
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.GT
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency BOB, country HN`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4014")
            addAttribute(R.attr.andesAmountFieldCountry, "5015")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.BOB
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.HN
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency GTQ, country NI`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4015")
            addAttribute(R.attr.andesAmountFieldCountry, "5016")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.GTQ
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.NI
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency PYG, country SV`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4016")
            addAttribute(R.attr.andesAmountFieldCountry, "5017")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.PYG
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.SV
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency HNL, country PR`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4017")
            addAttribute(R.attr.andesAmountFieldCountry, "5018")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.HNL
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.PR
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency NIO, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4018")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.NIO
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency CUC, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4019")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.CUC
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency VES, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4020")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.VES
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency BTC, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4021")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.BTC
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency ETH, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4022")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.ETH
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency MCN, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4023")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.MCN
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }

    @Test
    fun `only setting currency USDP, country CU`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesAmountFieldCurrency, "4024")
            addAttribute(R.attr.andesAmountFieldCountry, "5019")
        }

        attrs = AndesAmountFieldSimpleMoneyAttrsParser.parse(context, attrSet)

        attrs.andesAmountFieldState assertEquals AndesAmountFieldState.Idle
        attrs.andesAmountFieldEntryMode assertIsNull true
        attrs.andesAmountFieldEntryType assertEquals AndesAmountFieldEntryType.MONEY
        attrs.andesAmountFieldSize assertEquals AndesAmountFieldSize.LARGE
        attrs.andesAmountFieldCurrency assertEquals AndesMoneyAmountCurrency.USDP
        attrs.andesAmountFieldShowCurrencyAsIsoValue assertEquals false
        attrs.andesAmountFieldCountry assertEquals AndesCountry.CU
        attrs.andesAmountFieldNumberOfDecimals assertIsNull true
        attrs.andesAmountFieldInitialValue assertIsNull true
        attrs.andesAmountFieldHelperText assertIsNull true
        attrs.andesAmountFieldSuffixText assertIsNull true
        attrs.andesAmountFieldSuffixA11yText assertIsNull true
        attrs.andesAmountFieldMaxValue assertIsNull true
    }
}
