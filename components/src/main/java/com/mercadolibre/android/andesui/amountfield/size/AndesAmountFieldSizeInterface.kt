package com.mercadolibre.android.andesui.amountfield.size

import android.content.Context
import com.mercadolibre.android.andesui.R

internal interface AndesAmountFieldSizeInterface {

    fun currencySize(context: Context): Float
    fun amountSize(context: Context): Float
    fun suffixSize(context: Context): Float
    fun horizontalMargin(context: Context): Int
}

internal object AndesAmountFieldSizeLarge : AndesAmountFieldSizeInterface {

    override fun currencySize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_large_currency)

    override fun amountSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_large_amount)

    override fun suffixSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_large_suffix)

    override fun horizontalMargin(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_large_horizontal_margin)
}

internal object AndesAmountFieldSizeMedium : AndesAmountFieldSizeInterface {

    override fun currencySize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_medium_currency)

    override fun amountSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_medium_amount)

    override fun suffixSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_medium_suffix)

    override fun horizontalMargin(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_medium_horizontal_margin)
}

internal object AndesAmountFieldSizeSmall : AndesAmountFieldSizeInterface {

    override fun currencySize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_small_currency)

    override fun amountSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_small_amount)

    override fun suffixSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_small_suffix)

    override fun horizontalMargin(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_small_horizontal_margin)
}

internal object AndesAmountFieldSizeExtraSmall : AndesAmountFieldSizeInterface {

    override fun currencySize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_extra_small_currency)

    override fun amountSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_extra_small_amount)

    override fun suffixSize(context: Context) =
        context.resources.getDimension(R.dimen.andes_amount_field_size_extra_small_suffix)

    override fun horizontalMargin(context: Context) =
        context.resources.getDimensionPixelSize(R.dimen.andes_amount_field_size_extra_small_horizontal_margin)
}
