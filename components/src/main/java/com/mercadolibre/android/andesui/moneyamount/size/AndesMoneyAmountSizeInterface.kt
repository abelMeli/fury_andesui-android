package com.mercadolibre.android.andesui.moneyamount.size

import android.content.Context
import com.mercadolibre.android.andesui.R

internal interface AndesMoneyAmountSizeInterface {
    fun textSize(context: Context): Float
    fun superScriptSize(context: Context): Float
}

internal object AndesMoneyAmountSize12 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_12)
    override fun superScriptSize(context: Context) = 0F
}

internal object AndesMoneyAmountSize14 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_14)
    override fun superScriptSize(context: Context) = 0F
}

internal object AndesMoneyAmountSize16 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_16)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
}

internal object AndesMoneyAmountSize18 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_18)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
}

internal object AndesMoneyAmountSize20 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_20)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_10)
}

internal object AndesMoneyAmountSize24 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_24)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_12)
}

internal object AndesMoneyAmountSize28 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_28)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_14)
}

internal object AndesMoneyAmountSize32 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_32)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_16)
}

internal object AndesMoneyAmountSize36 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_36)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_18)
}

internal object AndesMoneyAmountSize40 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_40)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_20)
}

internal object AndesMoneyAmountSize44 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_44)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_22)
}

internal object AndesMoneyAmountSize48 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_48)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_24)
}

internal object AndesMoneyAmountSize52 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_52)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_26)
}

internal object AndesMoneyAmountSize56 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_56)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_28)
}

internal object AndesMoneyAmountSize60 : AndesMoneyAmountSizeInterface {
    override fun textSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_60)
    override fun superScriptSize(context: Context) = context.resources.getDimension(R.dimen.andes_text_size_30)
}
