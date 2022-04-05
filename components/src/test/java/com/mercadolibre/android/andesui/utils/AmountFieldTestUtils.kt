package com.mercadolibre.android.andesui.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.EditText
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.amountfield.size.AndesAmountFieldSize
import com.mercadolibre.android.andesui.amountfield.utils.AndesAmountFieldEditText
import com.mercadolibre.android.andesui.amountfield.utils.TextDimensionUtils
import io.mockk.every
import io.mockk.mockkObject

/**
 * Adds the passed text to the clipboard.
 */
internal fun Context.emulateCopyEventToClipboard(textToCopy: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val data = ClipData.newPlainText("label", textToCopy)
    clipboard.setPrimaryClip(data)
}

/**
 * Calls paste event over the caller edittext. If needed, call after setting a text into the clipboard
 * using the [emulateCopyEventToClipboard] method.
 */
internal fun EditText.emulatePasteEvent() {
    val actionPaste = android.R.id.paste
    onTextContextMenuItem(actionPaste)
}

internal fun AndesAmountFieldSimple.getInternalEditTextComponent(): AndesAmountFieldEditText {
    return findViewById(R.id.amount_field_edit_text)
}

internal fun EditText.emulateDeleteKeyPressed() {
    if (text.isNotEmpty()) {
        text.replace(text.length -1, text.length, "")
    }
}

internal fun EditText.emulateTypingWithKeyboard(valueToEnter: String) {
    valueToEnter.forEach { char ->
        append(char.toString())
    }
}

internal fun AndesAmountFieldSimple.emulateTypingWithKeyboard(valueToEnter: String) {
    getInternalEditTextComponent().emulateTypingWithKeyboard(valueToEnter)
}

internal fun AndesAmountFieldSimple.emulateDeleteKeyPressed() {
    getInternalEditTextComponent().emulateDeleteKeyPressed()
}

internal fun configureTextDimensionUtilsLargeText(context: Context) {
    mockkObject(TextDimensionUtils)
    every { TextDimensionUtils.getStringWidth(context, any(), any()) } answers {
        when (secondArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                300
            }
            AndesAmountFieldSize.MEDIUM -> {
                300
            }
            AndesAmountFieldSize.SMALL -> {
                300
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                300
            }
        }
    }
    every { TextDimensionUtils.getNonEditableViewsTotalWidth(context, any(), any(), any()) } answers {
        when (lastArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                150
            }
            AndesAmountFieldSize.MEDIUM -> {
                150
            }
            AndesAmountFieldSize.SMALL -> {
                150
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                150
            }
        }
    }
}

internal fun configureTextDimensionUtilsMediumText(context: Context) {
    mockkObject(TextDimensionUtils)
    every { TextDimensionUtils.getStringWidth(context, any(), any()) } answers {
        when (secondArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                500
            }
            AndesAmountFieldSize.MEDIUM -> {
                400
            }
            AndesAmountFieldSize.SMALL -> {
                400
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                400
            }
        }
    }
    every { TextDimensionUtils.getNonEditableViewsTotalWidth(context, any(), any(), any()) } answers {
        when (lastArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                150
            }
            AndesAmountFieldSize.MEDIUM -> {
                100
            }
            AndesAmountFieldSize.SMALL -> {
                100
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                100
            }
        }
    }
}

internal fun configureTextDimensionUtilsSmallText(context: Context) {
    mockkObject(TextDimensionUtils)
    every { TextDimensionUtils.getStringWidth(context, any(), any()) } answers {
        when (secondArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                700
            }
            AndesAmountFieldSize.MEDIUM -> {
                500
            }
            AndesAmountFieldSize.SMALL -> {
                400
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                400
            }
        }
    }
    every { TextDimensionUtils.getNonEditableViewsTotalWidth(context, any(), any(), any()) } answers {
        when (lastArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                225
            }
            AndesAmountFieldSize.MEDIUM -> {
                150
            }
            AndesAmountFieldSize.SMALL -> {
                100
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                100
            }
        }
    }
}

internal fun configureTextDimensionUtilsExtraSmallText(context: Context) {
    mockkObject(TextDimensionUtils)
    every { TextDimensionUtils.getStringWidth(context, any(), any()) } answers {
        when (secondArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                900
            }
            AndesAmountFieldSize.MEDIUM -> {
                700
            }
            AndesAmountFieldSize.SMALL -> {
                500
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                400
            }
        }
    }
    every { TextDimensionUtils.getNonEditableViewsTotalWidth(context, any(), any(), any()) } answers {
        when (lastArg<AndesAmountFieldSize>()) {
            AndesAmountFieldSize.LARGE -> {
                300
            }
            AndesAmountFieldSize.MEDIUM -> {
                225
            }
            AndesAmountFieldSize.SMALL -> {
                150
            }
            AndesAmountFieldSize.EXTRA_SMALL -> {
                100
            }
        }
    }
}
