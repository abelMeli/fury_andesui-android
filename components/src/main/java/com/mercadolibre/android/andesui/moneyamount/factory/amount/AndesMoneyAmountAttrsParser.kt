package com.mercadolibre.android.andesui.moneyamount.factory.amount

import android.content.Context
import android.content.res.TypedArray
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType

/**
 * The data class that contains the public components of the money amount.
 */
internal data class AndesMoneyAmountAttrs(
    val andesMoneyAmount: Double,
    val andesShowZerosDecimal: Boolean,
    val andesMoneyAmountSize: AndesMoneyAmountSize,
    val andesMoneyAmountType: AndesMoneyAmountType,
    val andesMoneyAmountDecimalsStyle: AndesMoneyAmountDecimalsStyle,
    val andesMoneyAmountCurrency: AndesMoneyAmountCurrency,
    val andesMoneyAmountCountry: AndesCountry,
    val andesShowIcon: Boolean,
    val andesSuffix: SpannableStringBuilder? = null,
    val andesSuffixAccessibility: String? = null,
    val andesTextColor: AndesColor? = null,
    val andesSemiBold: Boolean = false
)

/**
 * This object parse the attribute set and return an instance of AndesMoneyAmountAttrs to be used by AndesMoneyAmount
 */
@Suppress("ComplexMethod")
internal object AndesMoneyAmountAttrsParser {

    private const val DEFAULT_SIZE = 1005
    private const val DEFAULT_TYPE = 2000
    private const val DEFAULT_STYLE = 3001
    private const val DEFAULT_CURRENCY = 4014
    private const val NO_COUNTRY = -1

    private const val ANDES_MONEY_AMOUNT_SIZE_12 = 1000
    private const val ANDES_MONEY_AMOUNT_SIZE_14 = 1001
    private const val ANDES_MONEY_AMOUNT_SIZE_16 = 1002
    private const val ANDES_MONEY_AMOUNT_SIZE_18 = 1003
    private const val ANDES_MONEY_AMOUNT_SIZE_20 = 1004
    private const val ANDES_MONEY_AMOUNT_SIZE_24 = 1005
    private const val ANDES_MONEY_AMOUNT_SIZE_28 = 1006
    private const val ANDES_MONEY_AMOUNT_SIZE_32 = 1007
    private const val ANDES_MONEY_AMOUNT_SIZE_36 = 1008
    private const val ANDES_MONEY_AMOUNT_SIZE_40 = 1009
    private const val ANDES_MONEY_AMOUNT_SIZE_44 = 1010
    private const val ANDES_MONEY_AMOUNT_SIZE_48 = 1011
    private const val ANDES_MONEY_AMOUNT_SIZE_52 = 1012
    private const val ANDES_MONEY_AMOUNT_SIZE_56 = 1013
    private const val ANDES_MONEY_AMOUNT_SIZE_60 = 1014

    private const val ANDES_MONEY_AMOUNT_TYPE_POSITIVE = 2000
    private const val ANDES_MONEY_AMOUNT_TYPE_NEGATIVE = 2001
    private const val ANDES_MONEY_AMOUNT_TYPE_PREVIOUS = 2002

    private const val ANDES_MONEY_AMOUNT_STYLE_NONE = 3000
    private const val ANDES_MONEY_AMOUNT_STYLE_NORMAL = 3001
    private const val ANDES_MONEY_AMOUNT_STYLE_SUPERSCRIPT = 3002

    private const val ANDES_MONEY_AMOUNT_CURRENCY_BRL = 4001
    private const val ANDES_MONEY_AMOUNT_CURRENCY_UYU = 4002
    private const val ANDES_MONEY_AMOUNT_CURRENCY_CLP = 4003
    private const val ANDES_MONEY_AMOUNT_CURRENCY_CLF = 4004
    private const val ANDES_MONEY_AMOUNT_CURRENCY_MXN = 4006
    private const val ANDES_MONEY_AMOUNT_CURRENCY_DOP = 4007
    private const val ANDES_MONEY_AMOUNT_CURRENCY_PAB = 4008
    private const val ANDES_MONEY_AMOUNT_CURRENCY_COP = 4009
    private const val ANDES_MONEY_AMOUNT_CURRENCY_VEF = 4010
    private const val ANDES_MONEY_AMOUNT_CURRENCY_EUR = 4011
    private const val ANDES_MONEY_AMOUNT_CURRENCY_PEN = 4012
    private const val ANDES_MONEY_AMOUNT_CURRENCY_CRC = 4013
    private const val ANDES_MONEY_AMOUNT_CURRENCY_ARS = 4014
    private const val ANDES_MONEY_AMOUNT_CURRENCY_USD = 4015
    private const val ANDES_MONEY_AMOUNT_CURRENCY_BOB = 4016
    private const val ANDES_MONEY_AMOUNT_CURRENCY_GTQ = 4017
    private const val ANDES_MONEY_AMOUNT_CURRENCY_PYG = 4018
    private const val ANDES_MONEY_AMOUNT_CURRENCY_HNL = 4019
    private const val ANDES_MONEY_AMOUNT_CURRENCY_NIO = 4020
    private const val ANDES_MONEY_AMOUNT_CURRENCY_CUC = 4021
    private const val ANDES_MONEY_AMOUNT_CURRENCY_VES = 4022
    private const val ANDES_MONEY_AMOUNT_CURRENCY_BTC = 4023
    private const val ANDES_MONEY_AMOUNT_CURRENCY_ETH = 4024
    private const val ANDES_MONEY_AMOUNT_CURRENCY_MCN = 4025
    private const val ANDES_MONEY_AMOUNT_CURRENCY_USDP = 4026

    private const val ANDES_MONEY_AMOUNT_COUNTRY_AR = 5001
    private const val ANDES_MONEY_AMOUNT_COUNTRY_BR = 5002
    private const val ANDES_MONEY_AMOUNT_COUNTRY_CL = 5003
    private const val ANDES_MONEY_AMOUNT_COUNTRY_CO = 5004
    private const val ANDES_MONEY_AMOUNT_COUNTRY_MX = 5006
    private const val ANDES_MONEY_AMOUNT_COUNTRY_CR = 5007
    private const val ANDES_MONEY_AMOUNT_COUNTRY_PE = 5008
    private const val ANDES_MONEY_AMOUNT_COUNTRY_EC = 5009
    private const val ANDES_MONEY_AMOUNT_COUNTRY_PA = 5010
    private const val ANDES_MONEY_AMOUNT_COUNTRY_DO = 5011
    private const val ANDES_MONEY_AMOUNT_COUNTRY_UY = 5012
    private const val ANDES_MONEY_AMOUNT_COUNTRY_VE = 5013
    private const val ANDES_MONEY_AMOUNT_COUNTRY_BO = 5014
    private const val ANDES_MONEY_AMOUNT_COUNTRY_PY = 5015
    private const val ANDES_MONEY_AMOUNT_COUNTRY_GT = 5016
    private const val ANDES_MONEY_AMOUNT_COUNTRY_HN = 5017
    private const val ANDES_MONEY_AMOUNT_COUNTRY_NI = 5018
    private const val ANDES_MONEY_AMOUNT_COUNTRY_SV = 5019
    private const val ANDES_MONEY_AMOUNT_COUNTRY_PR = 5020
    private const val ANDES_MONEY_AMOUNT_COUNTRY_CU = 5021

    fun parse(context: Context, attr: AttributeSet?): AndesMoneyAmountAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesMoneyAmount)
        return AndesMoneyAmountAttrs(
            andesMoneyAmount = typedArray.getFloat(R.styleable.AndesMoneyAmount_andesMoneyAmount, 0F).toDouble(),
            andesMoneyAmountSize = resolveSize(typedArray),
            andesMoneyAmountType = resolveType(typedArray),
            andesMoneyAmountDecimalsStyle = resolveStyle(typedArray),
            andesMoneyAmountCurrency = resolveCurrency(typedArray),
            andesMoneyAmountCountry = resolveCountry(typedArray),
            andesShowZerosDecimal = typedArray.getBoolean(R.styleable.AndesMoneyAmount_andesShowZerosDecimal, false),
            andesShowIcon = typedArray.getBoolean(R.styleable.AndesMoneyAmount_andesShowIcon, false),
            andesSemiBold = typedArray.getBoolean(R.styleable.AndesMoneyAmount_andesSemibold, false),
        ).also { typedArray.recycle() }
    }

    private fun resolveType(typedArray: TypedArray): AndesMoneyAmountType =
        when (typedArray.getInt(R.styleable.AndesMoneyAmount_andesMoneyAmountType, DEFAULT_TYPE)) {
            ANDES_MONEY_AMOUNT_TYPE_POSITIVE -> AndesMoneyAmountType.POSITIVE
            ANDES_MONEY_AMOUNT_TYPE_NEGATIVE -> AndesMoneyAmountType.NEGATIVE
            ANDES_MONEY_AMOUNT_TYPE_PREVIOUS -> AndesMoneyAmountType.PREVIOUS
            else -> AndesMoneyAmountType.POSITIVE
        }

    private fun resolveSize(typedArray: TypedArray): AndesMoneyAmountSize =
        when (typedArray.getInt(R.styleable.AndesMoneyAmount_andesMoneyAmountSize, DEFAULT_SIZE)) {
            ANDES_MONEY_AMOUNT_SIZE_12 -> AndesMoneyAmountSize.SIZE_12
            ANDES_MONEY_AMOUNT_SIZE_14 -> AndesMoneyAmountSize.SIZE_14
            ANDES_MONEY_AMOUNT_SIZE_16 -> AndesMoneyAmountSize.SIZE_16
            ANDES_MONEY_AMOUNT_SIZE_18 -> AndesMoneyAmountSize.SIZE_18
            ANDES_MONEY_AMOUNT_SIZE_20 -> AndesMoneyAmountSize.SIZE_20
            ANDES_MONEY_AMOUNT_SIZE_24 -> AndesMoneyAmountSize.SIZE_24
            ANDES_MONEY_AMOUNT_SIZE_28 -> AndesMoneyAmountSize.SIZE_28
            ANDES_MONEY_AMOUNT_SIZE_32 -> AndesMoneyAmountSize.SIZE_32
            ANDES_MONEY_AMOUNT_SIZE_36 -> AndesMoneyAmountSize.SIZE_36
            ANDES_MONEY_AMOUNT_SIZE_40 -> AndesMoneyAmountSize.SIZE_40
            ANDES_MONEY_AMOUNT_SIZE_44 -> AndesMoneyAmountSize.SIZE_44
            ANDES_MONEY_AMOUNT_SIZE_48 -> AndesMoneyAmountSize.SIZE_48
            ANDES_MONEY_AMOUNT_SIZE_52 -> AndesMoneyAmountSize.SIZE_52
            ANDES_MONEY_AMOUNT_SIZE_56 -> AndesMoneyAmountSize.SIZE_56
            ANDES_MONEY_AMOUNT_SIZE_60 -> AndesMoneyAmountSize.SIZE_60
            else -> AndesMoneyAmountSize.SIZE_24
        }

    private fun resolveCurrency(typedArray: TypedArray): AndesMoneyAmountCurrency =
        when (typedArray.getInt(R.styleable.AndesMoneyAmount_andesMoneyAmountCurrency, DEFAULT_CURRENCY)) {
            ANDES_MONEY_AMOUNT_CURRENCY_BRL -> AndesMoneyAmountCurrency.BRL
            ANDES_MONEY_AMOUNT_CURRENCY_UYU -> AndesMoneyAmountCurrency.UYU
            ANDES_MONEY_AMOUNT_CURRENCY_CLP -> AndesMoneyAmountCurrency.CLP
            ANDES_MONEY_AMOUNT_CURRENCY_CLF -> AndesMoneyAmountCurrency.CLF
            ANDES_MONEY_AMOUNT_CURRENCY_MXN -> AndesMoneyAmountCurrency.MXN
            ANDES_MONEY_AMOUNT_CURRENCY_DOP -> AndesMoneyAmountCurrency.DOP
            ANDES_MONEY_AMOUNT_CURRENCY_PAB -> AndesMoneyAmountCurrency.PAB
            ANDES_MONEY_AMOUNT_CURRENCY_COP -> AndesMoneyAmountCurrency.COP
            ANDES_MONEY_AMOUNT_CURRENCY_VEF -> AndesMoneyAmountCurrency.VEF
            ANDES_MONEY_AMOUNT_CURRENCY_EUR -> AndesMoneyAmountCurrency.EUR
            ANDES_MONEY_AMOUNT_CURRENCY_PEN -> AndesMoneyAmountCurrency.PEN
            ANDES_MONEY_AMOUNT_CURRENCY_CRC -> AndesMoneyAmountCurrency.CRC
            ANDES_MONEY_AMOUNT_CURRENCY_ARS -> AndesMoneyAmountCurrency.ARS
            ANDES_MONEY_AMOUNT_CURRENCY_USD -> AndesMoneyAmountCurrency.USD
            ANDES_MONEY_AMOUNT_CURRENCY_BOB -> AndesMoneyAmountCurrency.BOB
            ANDES_MONEY_AMOUNT_CURRENCY_GTQ -> AndesMoneyAmountCurrency.GTQ
            ANDES_MONEY_AMOUNT_CURRENCY_PYG -> AndesMoneyAmountCurrency.PYG
            ANDES_MONEY_AMOUNT_CURRENCY_HNL -> AndesMoneyAmountCurrency.HNL
            ANDES_MONEY_AMOUNT_CURRENCY_NIO -> AndesMoneyAmountCurrency.NIO
            ANDES_MONEY_AMOUNT_CURRENCY_CUC -> AndesMoneyAmountCurrency.CUC
            ANDES_MONEY_AMOUNT_CURRENCY_VES -> AndesMoneyAmountCurrency.VES
            ANDES_MONEY_AMOUNT_CURRENCY_BTC -> AndesMoneyAmountCurrency.BTC
            ANDES_MONEY_AMOUNT_CURRENCY_ETH -> AndesMoneyAmountCurrency.ETH
            ANDES_MONEY_AMOUNT_CURRENCY_MCN -> AndesMoneyAmountCurrency.MCN
            ANDES_MONEY_AMOUNT_CURRENCY_USDP -> AndesMoneyAmountCurrency.USDP
            else -> AndesMoneyAmountCurrency.ARS
        }

    private fun resolveCountry(typedArray: TypedArray): AndesCountry =
        when (typedArray.getInt(R.styleable.AndesMoneyAmount_andesMoneyAmountCountry, NO_COUNTRY)) {
            ANDES_MONEY_AMOUNT_COUNTRY_AR -> AndesCountry.AR
            ANDES_MONEY_AMOUNT_COUNTRY_BR -> AndesCountry.BR
            ANDES_MONEY_AMOUNT_COUNTRY_CL -> AndesCountry.CL
            ANDES_MONEY_AMOUNT_COUNTRY_CO -> AndesCountry.CO
            ANDES_MONEY_AMOUNT_COUNTRY_MX -> AndesCountry.MX
            ANDES_MONEY_AMOUNT_COUNTRY_CR -> AndesCountry.CR
            ANDES_MONEY_AMOUNT_COUNTRY_PE -> AndesCountry.PE
            ANDES_MONEY_AMOUNT_COUNTRY_EC -> AndesCountry.EC
            ANDES_MONEY_AMOUNT_COUNTRY_PA -> AndesCountry.PA
            ANDES_MONEY_AMOUNT_COUNTRY_DO -> AndesCountry.DO
            ANDES_MONEY_AMOUNT_COUNTRY_UY -> AndesCountry.UY
            ANDES_MONEY_AMOUNT_COUNTRY_VE -> AndesCountry.VE
            ANDES_MONEY_AMOUNT_COUNTRY_BO -> AndesCountry.BO
            ANDES_MONEY_AMOUNT_COUNTRY_PY -> AndesCountry.PY
            ANDES_MONEY_AMOUNT_COUNTRY_GT -> AndesCountry.GT
            ANDES_MONEY_AMOUNT_COUNTRY_HN -> AndesCountry.HN
            ANDES_MONEY_AMOUNT_COUNTRY_NI -> AndesCountry.NI
            ANDES_MONEY_AMOUNT_COUNTRY_SV -> AndesCountry.SV
            ANDES_MONEY_AMOUNT_COUNTRY_PR -> AndesCountry.PR
            ANDES_MONEY_AMOUNT_COUNTRY_CU -> AndesCountry.CU
            else -> AndesCurrencyHelper.currentCountry
        }

    private fun resolveStyle(typedArray: TypedArray): AndesMoneyAmountDecimalsStyle =
        when (typedArray.getInt(R.styleable.AndesMoneyAmount_andesMoneyAmountStyle, DEFAULT_STYLE)) {
            ANDES_MONEY_AMOUNT_STYLE_NONE -> AndesMoneyAmountDecimalsStyle.NONE
            ANDES_MONEY_AMOUNT_STYLE_NORMAL -> AndesMoneyAmountDecimalsStyle.NORMAL
            ANDES_MONEY_AMOUNT_STYLE_SUPERSCRIPT -> AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
            else -> AndesMoneyAmountDecimalsStyle.NORMAL
        }
}
