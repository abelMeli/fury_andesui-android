package com.mercadolibre.android.andesui.moneyamount.factory.combo

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency

/**
 * The data class that contains the public components of the money amount discount.
 */
internal data class AndesMoneyAmountComboAttrs(
    val andesMoneyAmountCurrency: AndesMoneyAmountCurrency,
    val andesMoneyAmountCountry: AndesCountry,
    val andesMoneyAmount: Double,
    val andesMoneyPreviousAmount: Double = 0.0,
    val andesMoneyDiscount: Int = 0,
    val andesMoneyAmountSize: AndesMoneyAmountComboSize = AndesMoneyAmountComboSize.SIZE_24
)

/**
 * This object parse the attribute set and return an instance of AndesMoneyAmountDiscountAttrs to be used by AndesMoneyAmountDiscount
 */
@Suppress("ComplexMethod")
internal object AndesMoneyAmountComboAttrsParser {

    private const val DEFAULT_SIZE = 1000
    private const val DEFAULT_CURRENCY = 4014
    private const val NO_COUNTRY = -1

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

    private const val ANDES_MONEY_AMOUNT_SIZE_24 = 1000
    private const val ANDES_MONEY_AMOUNT_SIZE_36 = 1001

    fun parse(context: Context, attr: AttributeSet?): AndesMoneyAmountComboAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesMoneyAmountCombo)
        return AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = resolveCurrency(typedArray),
                andesMoneyAmountCountry = resolveCountry(typedArray),
                andesMoneyAmount = typedArray.getFloat(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboAmount, 0F).toDouble(),
                andesMoneyPreviousAmount = typedArray.getFloat(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboPreviousAmount, 0F).toDouble(),
                andesMoneyDiscount = typedArray.getInt(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboDiscount, 0),
                andesMoneyAmountSize = resolveSize(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun resolveCurrency(typedArray: TypedArray): AndesMoneyAmountCurrency =
        when (typedArray.getInt(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboCurrency, DEFAULT_CURRENCY)) {
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
            else -> AndesMoneyAmountCurrency.ARS
        }

    private fun resolveCountry(typedArray: TypedArray): AndesCountry =
        when (typedArray.getInt(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboCountry, NO_COUNTRY)) {
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

    private fun resolveSize(typedArray: TypedArray): AndesMoneyAmountComboSize =
        when (typedArray.getInt(R.styleable.AndesMoneyAmountCombo_andesMoneyAmountComboSize, DEFAULT_SIZE)) {
            ANDES_MONEY_AMOUNT_SIZE_24 -> AndesMoneyAmountComboSize.SIZE_24
            ANDES_MONEY_AMOUNT_SIZE_36 -> AndesMoneyAmountComboSize.SIZE_36
            else -> AndesMoneyAmountComboSize.SIZE_24
        }
}
