package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.regex.Matcher
import java.util.regex.Pattern

fun validateColor(color: String): Boolean {
    val colorPattern: Pattern = Pattern.compile("#([0-9A-F]{6})")
    val m: Matcher = colorPattern.matcher(color.toUpperCase())
    return m.matches()
}

/**
 * Validates whether all settings field are enabled or not.
 * @param fields Array of fields to validate.
 */
fun Context.settingsEnabled(fields: Map<String, Float>): Boolean {
    return fields.keys.fold(true) { acc, setting ->
        acc && Settings.Global.getFloat(contentResolver, setting, fields[setting] ?: 1.0f) != 0.0f
    }
}
