package com.mercadolibre.android.andesui.utils

internal fun Double.isInt(): Boolean {
    return this.rem(1) == 0.0
}
