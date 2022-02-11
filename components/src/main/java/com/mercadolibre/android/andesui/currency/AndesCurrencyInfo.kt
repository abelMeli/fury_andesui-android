package com.mercadolibre.android.andesui.currency

import androidx.annotation.DrawableRes

data class AndesCurrencyInfo(
    val symbol: String,
    val decimalPlaces: Int,
    val singularDescription: Int?,
    val pluralDescription: Int?,
    val decimalSingularDescription: Int?,
    val decimalPluralDescription: Int?,
    @DrawableRes
    val icon: Int?,
    val isCrypto: Boolean
)
