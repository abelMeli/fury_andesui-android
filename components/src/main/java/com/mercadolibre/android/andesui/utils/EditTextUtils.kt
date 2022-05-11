package com.mercadolibre.android.andesui.utils

import android.widget.EditText

internal fun EditText.inputLength(default: Int = 0): Int {
    return text?.length ?: default
}
