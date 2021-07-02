package com.mercadolibre.android.andesui.demoapp.utils

import android.content.Context
import android.util.TypedValue

fun Context.getInDp(value: Float): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value, this.resources.displayMetrics).toInt()
}
