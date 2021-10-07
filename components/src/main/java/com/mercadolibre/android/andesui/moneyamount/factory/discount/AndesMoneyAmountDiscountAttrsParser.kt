package com.mercadolibre.android.andesui.moneyamount.factory.discount

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize

/**
 * The data class that contains the public components of the money amount discount.
 */
internal data class AndesMoneyAmountDiscountAttrs(
    val andesMoneyDiscount: Int,
    val andesMoneyAmountSize: AndesMoneyAmountSize
)

/**
 * This object parse the attribute set and return an instance of AndesMoneyAmountDiscountAttrs to be used by AndesMoneyAmountDiscount
 */
@Suppress("ComplexMethod")
internal object AndesMoneyAmountDiscountAttrsParser {

    private const val DEFAULT_SIZE = 1005
    private const val DEFAULT_DISCOUNT = 0

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

    fun parse(context: Context, attr: AttributeSet?): AndesMoneyAmountDiscountAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesMoneyAmountDiscount)
        return AndesMoneyAmountDiscountAttrs(
                andesMoneyDiscount = typedArray.getInt(R.styleable.AndesMoneyAmountDiscount_andesMoneyDiscount, DEFAULT_DISCOUNT),
                andesMoneyAmountSize = resolveSize(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun resolveSize(typedArray: TypedArray): AndesMoneyAmountSize =
        when (typedArray.getInt(R.styleable.AndesMoneyAmountDiscount_andesMoneyAmountDiscountSize, DEFAULT_SIZE)) {
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
}
