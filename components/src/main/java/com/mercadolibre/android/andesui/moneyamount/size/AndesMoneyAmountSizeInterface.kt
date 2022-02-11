package com.mercadolibre.android.andesui.moneyamount.size

import android.content.Context
import com.mercadolibre.android.andesui.R

internal interface AndesMoneyAmountSizeInterface {
    fun textSize(context: Context): Float
    fun superScriptSize(context: Context): Float
    fun iconSize(context: Context): Float
    fun discountIconSize(context: Context): Float
    fun iconPadding(context: Context): Float
    fun suffixPadding(context: Context): Float
    fun suffixSize(context: Context): Float
}

internal object AndesMoneyAmountSize12 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_12)
    override fun superScriptSize(context: Context) = 0F
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_9)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_16)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_3)
    override fun suffixPadding(context: Context) = 0F
    override fun suffixSize(context: Context) = 0F
}

internal object AndesMoneyAmountSize14 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_14)
    override fun superScriptSize(context: Context) = 0F
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_11)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_16)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_3)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_2)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_9)
}

internal object AndesMoneyAmountSize16 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_16)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_13)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_16)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_3)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_3)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_10)
}

internal object AndesMoneyAmountSize18 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_18)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_15)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_20)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_4)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_3)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_12)
}

internal object AndesMoneyAmountSize20 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_20)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_17)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_20)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_4)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_4)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_13)
}

internal object AndesMoneyAmountSize24 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_24)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_12)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_20)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_24)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_5)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_6)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_16)
}

internal object AndesMoneyAmountSize28 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_28)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_14)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_22)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_28)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_5)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_7)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_18)
}

internal object AndesMoneyAmountSize32 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_32)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_16)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_24)
    override fun discountIconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_32)
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_5)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_7)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_21)
}

internal object AndesMoneyAmountSize36 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_36)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_18)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_27)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_6)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_8)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_24)
}

internal object AndesMoneyAmountSize40 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_40)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_20)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_28)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_7)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_10)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_26)
}

internal object AndesMoneyAmountSize44 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_44)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_22)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_32)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_8)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_11)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_28)
}

internal object AndesMoneyAmountSize48 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_48)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_24)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_34)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_8)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_12)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_32)
}

internal object AndesMoneyAmountSize52 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_52)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_26)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_38)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_10)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_13)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_34)
}

internal object AndesMoneyAmountSize56 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_56)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_28)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_41)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_10)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_14)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_36)
}

internal object AndesMoneyAmountSize60 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_60)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_30)
    override fun iconSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_logo_size_43)
    override fun discountIconSize(context: Context) = 0F
    override fun iconPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_10)
    override fun suffixPadding(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_padding_15)
    override fun suffixSize(context: Context) = context.resources.getDimension(R.dimen.andes_money_amount_suffix_size_38)
}
