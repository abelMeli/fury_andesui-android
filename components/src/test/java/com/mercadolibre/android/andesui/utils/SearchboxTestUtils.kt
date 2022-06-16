package com.mercadolibre.android.andesui.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox

internal fun AndesSearchbox.getInternalEditTextComponent(): EditText {
    return findViewById(R.id.andes_searchbox_edittext)
}

internal fun EditText.emulateSearchKeyPressed() {
    onEditorAction(EditorInfo.IME_ACTION_SEARCH)
}

internal fun AndesSearchbox.emulateTypingWithKeyboard(valueToEnter: String) {
    getInternalEditTextComponent().emulateTypingWithKeyboard(valueToEnter)
}

internal fun AndesSearchbox.emulateSearchKeyPressed() {
    getInternalEditTextComponent().emulateSearchKeyPressed()
}