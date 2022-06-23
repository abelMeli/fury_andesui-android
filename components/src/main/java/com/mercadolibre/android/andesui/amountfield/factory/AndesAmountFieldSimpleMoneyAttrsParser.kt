package com.mercadolibre.android.andesui.amountfield.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency

internal data class AndesAmountFieldSimpleMoneyAttrs(
    val andesAmountFieldState: AndesAmountFieldState,
    val andesAmountFieldEntryMode: AndesAmountFieldEntryMode?,
    val andesAmountFieldEntryType: AndesAmountFieldEntryType,
    val andesAmountFieldSize: AndesAmountFieldSize,
    val andesAmountFieldCurrency: AndesMoneyAmountCurrency,
    val andesAmountFieldShowCurrencyAsIsoValue: Boolean,
    val andesAmountFieldCountry: AndesCountry,
    val andesAmountFieldNumberOfDecimals: Int?,
    val andesAmountFieldInitialValue: String?,
    val andesAmountFieldHelperText: CharSequence?,
    val andesAmountFieldExceededHelperText: CharSequence?,
    val andesAmountFieldSuffixText: CharSequence?,
    val andesAmountFieldSuffixA11yText: String?,
    val andesAmountFieldMaxValue: String?,
    val andesAmountFieldIsEditable: Boolean,
)

internal object AndesAmountFieldSimpleMoneyAttrsParser {

    private const val STATE_IDLE = 1000
    private const val STATE_ERROR = 1001

    private const val ENTRY_MODE_INT = 2000
    private const val ENTRY_MODE_DECIMAL = 2001

    private const val CURRENCY_BRL = 4000
    private const val CURRENCY_UYU = 4001
    private const val CURRENCY_CLP = 4002
    private const val CURRENCY_CLF = 4003
    private const val CURRENCY_MXN = 4004
    private const val CURRENCY_DOP = 4005
    private const val CURRENCY_PAB = 4006
    private const val CURRENCY_COP = 4007
    private const val CURRENCY_VEF = 4008
    private const val CURRENCY_EUR = 4009
    private const val CURRENCY_PEN = 4010
    private const val CURRENCY_CRC = 4011
    private const val CURRENCY_ARS = 4012
    private const val CURRENCY_USD = 4013
    private const val CURRENCY_BOB = 4014
    private const val CURRENCY_GTQ = 4015
    private const val CURRENCY_PYG = 4016
    private const val CURRENCY_HNL = 4017
    private const val CURRENCY_NIO = 4018
    private const val CURRENCY_CUC = 4019
    private const val CURRENCY_VES = 4020
    private const val CURRENCY_BTC = 4021
    private const val CURRENCY_ETH = 4022
    private const val CURRENCY_MCN = 4023
    private const val CURRENCY_USDP = 4024

    private const val COUNTRY_AR = 5000
    private const val COUNTRY_BR = 5001
    private const val COUNTRY_CL = 5002
    private const val COUNTRY_CO = 5003
    private const val COUNTRY_MX = 5004
    private const val COUNTRY_CR = 5005
    private const val COUNTRY_PE = 5006
    private const val COUNTRY_EC = 5007
    private const val COUNTRY_PA = 5008
    private const val COUNTRY_DO = 5009
    private const val COUNTRY_UY = 5010
    private const val COUNTRY_VE = 5011
    private const val COUNTRY_BO = 5012
    private const val COUNTRY_PY = 5013
    private const val COUNTRY_GT = 5014
    private const val COUNTRY_HN = 5015
    private const val COUNTRY_NI = 5016
    private const val COUNTRY_SV = 5017
    private const val COUNTRY_PR = 5018
    private const val COUNTRY_CU = 5019

    private const val VALUE_NOT_SELECTED = -1

    private const val ENTRY_TYPE_MONEY = 7000
    private const val ENTRY_TYPE_PERCENTAGE = 7001

    fun parse(context: Context, attrs: AttributeSet?): AndesAmountFieldSimpleMoneyAttrs {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AndesAmountFieldSimpleMoney)

        val state = when (
            typedArray.getInt(
                R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldState,
                VALUE_NOT_SELECTED
            )
        ) {
            STATE_IDLE -> AndesAmountFieldState.Idle
            STATE_ERROR -> AndesAmountFieldState.Error
            else -> AndesAmountFieldState.Idle
        }

        val entryMode = when (
            typedArray.getInt(
                R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldEntryMode,
                VALUE_NOT_SELECTED
            )
        ) {
            ENTRY_MODE_DECIMAL -> AndesAmountFieldEntryMode.DECIMAL
            ENTRY_MODE_INT -> AndesAmountFieldEntryMode.INT
            else -> null
        }

        val entryType = when (
            typedArray.getInt(
                R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldEntryType,
                VALUE_NOT_SELECTED
            )
        ) {
            ENTRY_TYPE_MONEY -> AndesAmountFieldEntryType.MONEY
            ENTRY_TYPE_PERCENTAGE -> AndesAmountFieldEntryType.PERCENTAGE
            else -> AndesAmountFieldEntryType.MONEY
        }

        val currency = when (
            typedArray.getInt(
                R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldCurrency,
                VALUE_NOT_SELECTED
            )
        ) {
            CURRENCY_BRL -> AndesMoneyAmountCurrency.BRL
            CURRENCY_UYU -> AndesMoneyAmountCurrency.UYU
            CURRENCY_CLP -> AndesMoneyAmountCurrency.CLP
            CURRENCY_CLF -> AndesMoneyAmountCurrency.CLF
            CURRENCY_MXN -> AndesMoneyAmountCurrency.MXN
            CURRENCY_DOP -> AndesMoneyAmountCurrency.DOP
            CURRENCY_PAB -> AndesMoneyAmountCurrency.PAB
            CURRENCY_COP -> AndesMoneyAmountCurrency.COP
            CURRENCY_VEF -> AndesMoneyAmountCurrency.VEF
            CURRENCY_EUR -> AndesMoneyAmountCurrency.EUR
            CURRENCY_PEN -> AndesMoneyAmountCurrency.PEN
            CURRENCY_CRC -> AndesMoneyAmountCurrency.CRC
            CURRENCY_ARS -> AndesMoneyAmountCurrency.ARS
            CURRENCY_USD -> AndesMoneyAmountCurrency.USD
            CURRENCY_BOB -> AndesMoneyAmountCurrency.BOB
            CURRENCY_GTQ -> AndesMoneyAmountCurrency.GTQ
            CURRENCY_PYG -> AndesMoneyAmountCurrency.PYG
            CURRENCY_HNL -> AndesMoneyAmountCurrency.HNL
            CURRENCY_NIO -> AndesMoneyAmountCurrency.NIO
            CURRENCY_CUC -> AndesMoneyAmountCurrency.CUC
            CURRENCY_VES -> AndesMoneyAmountCurrency.VES
            CURRENCY_BTC -> AndesMoneyAmountCurrency.BTC
            CURRENCY_ETH -> AndesMoneyAmountCurrency.ETH
            CURRENCY_MCN -> AndesMoneyAmountCurrency.MCN
            CURRENCY_USDP -> AndesMoneyAmountCurrency.USDP
            else -> AndesMoneyAmountCurrency.ARS
        }

        val country = when (
            typedArray.getInt(
                R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldCountry,
                VALUE_NOT_SELECTED
            )
        ) {
            COUNTRY_AR -> AndesCountry.AR
            COUNTRY_BR -> AndesCountry.BR
            COUNTRY_CL -> AndesCountry.CL
            COUNTRY_CO -> AndesCountry.CO
            COUNTRY_MX -> AndesCountry.MX
            COUNTRY_CR -> AndesCountry.CR
            COUNTRY_PE -> AndesCountry.PE
            COUNTRY_EC -> AndesCountry.EC
            COUNTRY_PA -> AndesCountry.PA
            COUNTRY_DO -> AndesCountry.DO
            COUNTRY_UY -> AndesCountry.UY
            COUNTRY_VE -> AndesCountry.VE
            COUNTRY_BO -> AndesCountry.BO
            COUNTRY_PY -> AndesCountry.PY
            COUNTRY_GT -> AndesCountry.GT
            COUNTRY_HN -> AndesCountry.HN
            COUNTRY_NI -> AndesCountry.NI
            COUNTRY_SV -> AndesCountry.SV
            COUNTRY_PR -> AndesCountry.PR
            COUNTRY_CU -> AndesCountry.CU
            else -> AndesCountry.AR
        }

        val showCurrencyAsIsoValue = typedArray.getBoolean(
            R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldShowCurrencyAsIsoValue,
            false
        )

        /**
         * Since the typedArray always returns a non-null value, we need to differentiate the "0"
         * value from the "null" value. Thus, we assign the "non-used parameter" (needed as null value)
         * to the [NUMBER_OF_DECIMALS_NOT_SPECIFIED] constant to be able to filter when the user does
         * not set this attribute.
         */
        val numberOfDecimalsFromXml = typedArray.getInt(
            R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldNumberOfDecimals,
            VALUE_NOT_SELECTED
        )

        val numberOfDecimals = when (numberOfDecimalsFromXml) {
            VALUE_NOT_SELECTED -> null
            else -> numberOfDecimalsFromXml
        }

        val initialValue =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldInitialValue)

        val helperText =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldHelperText)

        val exceededHelperText =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldExceededHelperText)

        val suffixText =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldSuffixText)

        val suffixA11yText =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldSuffixA11yText)

        val maxValue =
            typedArray.getString(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldMaxValue)

        val isEditable =
            typedArray.getBoolean(R.styleable.AndesAmountFieldSimpleMoney_andesAmountFieldIsEditable, true)

        return AndesAmountFieldSimpleMoneyAttrs(
            andesAmountFieldState = state,
            andesAmountFieldEntryMode = entryMode,
            andesAmountFieldEntryType = entryType,
            andesAmountFieldSize = AndesAmountFieldSize.LARGE,
            andesAmountFieldCurrency = currency,
            andesAmountFieldShowCurrencyAsIsoValue = showCurrencyAsIsoValue,
            andesAmountFieldCountry = country,
            andesAmountFieldNumberOfDecimals = numberOfDecimals,
            andesAmountFieldInitialValue = initialValue,
            andesAmountFieldHelperText = helperText,
            andesAmountFieldExceededHelperText = exceededHelperText,
            andesAmountFieldSuffixText = suffixText,
            andesAmountFieldSuffixA11yText = suffixA11yText,
            andesAmountFieldMaxValue = maxValue,
            andesAmountFieldIsEditable = isEditable
        ).also {
            typedArray.recycle()
        }
    }
}
