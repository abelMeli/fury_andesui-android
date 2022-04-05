package com.mercadolibre.android.andesui.amountfield.utils

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ActionMode
import androidx.appcompat.widget.AppCompatEditText
import com.mercadolibre.android.andesui.utils.AnimationsUtils.jump
import com.mercadolibre.android.andesui.utils.getTextInClipboard

internal class AndesAmountFieldEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    /**
     * Value needed to detect the paste event from the main class
     */
    internal var onTextPasteCallback: OnTextPasteCallback? = null

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            relocateCursorAtTheEnd()
            jump()
        }
    }

    /**
     * Method that triggers every time the user moves the cursor
     */
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        val thereIsNoSelection = selEnd - selStart == 0
        when {
            thereIsNoSelection && selStart != text?.length ?: 0 -> {
                relocateCursorAtTheEnd()
            }
            else -> super.onSelectionChanged(selStart, selEnd)
        }
    }

    /**
     * Method needed to keep the cursor locked at the end of the field
     */
    private fun relocateCursorAtTheEnd() {
        setSelection(text?.length ?: 0)
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        if (id == android.R.id.paste) {
            val newText = getTextInClipboard()
            onTextPasteCallback?.onTextPaste(newText ?: "")
            return true
        }
        return super.onTextContextMenuItem(id)
    }
}

internal interface OnTextPasteCallback {
    fun onTextPaste(pastedText: CharSequence)
}
