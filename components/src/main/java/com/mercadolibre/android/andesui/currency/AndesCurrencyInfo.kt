package com.mercadolibre.android.andesui.currency

data class AndesCurrencyInfo(
    val symbol: String,
    val decimalPlaces: Int,
    val singularDescription: Int,
    val pluralDescription: Int,
    val decimalSingularDescription: Int,
    val decimalPluralDescription: Int
)
