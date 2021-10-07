package com.mercadolibre.android.andesui.utils

import android.os.Build
import java.util.regex.Matcher
import java.util.regex.Pattern

fun validateColor(color: String): Boolean {
    val colorPattern: Pattern = Pattern.compile("#([0-9A-F]{6})")
    val m: Matcher = colorPattern.matcher(color.toUpperCase())
    return m.matches()
}

fun doWhenGreaterThanApi(apiLevel: Int, actions: () -> Unit) {
    if (Build.VERSION.SDK_INT >= apiLevel) {
        actions()
    }
}
