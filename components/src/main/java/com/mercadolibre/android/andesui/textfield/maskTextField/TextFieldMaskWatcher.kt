package com.mercadolibre.android.andesui.textfield.maskTextField

import android.text.Editable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import java.math.BigDecimal

class TextFieldMaskWatcher(private var mask: String = "", private var textChange: OnTextChange?) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        textChange?.onChange(charSequence.toString())
    }

    override fun afterTextChanged(editable: Editable) {
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true

        val editableLength = editable.length
        if (mask.isNotBlank() && editableLength < mask.length && editableLength > 0) {
            val filters = editable.filters
            editable.filters = EMPTY_FILTERS
            if (mask[editableLength] != GENERIC_CHAR) {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != GENERIC_CHAR) {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
            editable.filters = filters
        }

        isRunning = false
    }

    /**
     * Format text with mask
     * @param text without mask
     */
    fun getTextWithMask(text: String?): String {

        val editable = SpannableStringBuilder("")

        text?.let {
            if (mask.isEmpty()) {
                return text.toString()
            }

            for (char in text) {
                editable.append(char)
                val editableLength = editable.length
                if (mask.isNotBlank() && editableLength < mask.length) {
                    if (mask[editableLength] != GENERIC_CHAR) {
                        editable.append(mask[editableLength])
                    } else if (mask[editableLength - 1] != GENERIC_CHAR) {
                        editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
                    }
                }
            }
        }
        return editable.toString()
    }

    fun setMask(mask: String) {
        this.mask = mask
    }

    fun cleanMask(text: String): String {
        val maskChars = mask.replace(GENERIC_CHAR.toString(), "")
        var textWithoutMask = text
        maskChars.forEach { char ->
            textWithoutMask = textWithoutMask.replace(char.toString(), "")
        }
        return textWithoutMask
    }

    fun getLengthWithoutMask(): Int {
        return if (mask.isNotEmpty()) {
            mask.length - mask.replace(GENERIC_CHAR.toString(), "").length
        } else {
            BigDecimal.ZERO.toInt()
        }
    }

    fun getMaxLength(): Int {
        return mask.length
    }

    interface OnTextChange {
        fun onChange(text: String)
    }

    companion object {
        const val GENERIC_CHAR = '#'
        private val EMPTY_FILTERS = arrayOf<InputFilter>()
    }
}
