package com.mercadolibre.android.andesui.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.openKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}
