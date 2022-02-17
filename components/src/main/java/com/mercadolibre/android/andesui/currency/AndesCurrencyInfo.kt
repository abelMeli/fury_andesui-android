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
    internal var icon: Int?,
    val isCrypto: Boolean
) {
    init {
        this.icon = icon.takeIf { it != 0 }
    }
}
