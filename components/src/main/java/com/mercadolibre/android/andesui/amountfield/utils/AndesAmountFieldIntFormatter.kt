package com.mercadolibre.android.andesui.amountfield.utils

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.mercadolibre.android.andesui.utils.DOT
import com.mercadolibre.android.andesui.utils.COMMA
import com.mercadolibre.android.andesui.utils.deformatAmount
import com.mercadolibre.android.andesui.utils.hasComma
import com.mercadolibre.android.andesui.utils.hasDot
import com.mercadolibre.android.andesui.utils.isDot
import com.mercadolibre.android.andesui.utils.removeNonNumberChars
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

internal class AndesAmountFieldIntFormatter(
    private val resizingListener: ResizingListener,
    private val amountListener: AmountListener,
    private val decimalSeparator: Char,
    private val numberOfDecimals: Int,
    private val maxValue: String?,
    private val isBlocked: Boolean
) : TextWatcher {

    private var isRunning = false
    private var isDeleting = false
    private var isExceeded = false
    private var hasDecimalSeparator = false
    private var hasThousandSeparators = false
    private var didEnterSeparator = false
    private var filters = arrayOf<InputFilter>()
    private val formatter by lazy {
        if (decimalSeparator.isDot()) {
            DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.ENGLISH))
        } else {
            DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.ITALIAN))
        }
    }

    override fun beforeTextChanged(oldText: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after

        hasDecimalSeparator = if (decimalSeparator.isDot()) {
            oldText.hasDot()
        } else {
            oldText.hasComma()
        }
    }

    override fun onTextChanged(newText: CharSequence, start: Int, before: Int, count: Int) {
        hasThousandSeparators = if (decimalSeparator.isDot()) {
            newText.hasComma()
        } else {
            newText.hasDot()
        }

        didEnterSeparator = if (newText.isNotEmpty()) {
            newText.last() == DOT || newText.last() == COMMA
        } else {
            false
        }
    }

    override fun afterTextChanged(editable: Editable) {
        if (isRunning) {
            return
        }

        open(editable)

        if (isDeleting) {
            reformatEditableForDeletingCase(editable)
        } else {
            reformatEditableForEnteringCase(editable)
        }

        close(editable)
    }

    private fun reformatEditableForDeletingCase(editable: Editable) {
        if (editable.isNotEmpty()) {
            if (hasThousandSeparators && !hasDecimalSeparator) {
                val plainText = editable.removeNonNumberChars().toString().toBigDecimal()
                val formattedText = formatter.format(plainText)
                editable.clear()
                editable.append(formattedText)
            }
        }
    }

    private fun reformatEditableForEnteringCase(editable: Editable) {
        if(isBlocked) {
            editable.denyWriting()
            isExceeded = true
            return
        }

        if (didEnterSeparator) {
            manageDecimalSeparatorInput(editable)
        } else {
            manageNumberInput(editable)
        }
        checkIfValueIsExceeded(editable)
    }

    /**
     * Entered decimal separator. Only allowed when:
     *  -number of decimals is not 0
     *  -the component does not have already a decimal separator
     *  -the input is not the first value (the component was empty and the user entered a separator)
     */
    private fun manageDecimalSeparatorInput(editable: Editable) {
        if (numberOfDecimals == 0) {
            editable.denyWriting()
            return
        }

        if (!hasDecimalSeparator && !isFirstCharEntered(editable)) {
            editable.normalizeDecimalSeparator(decimalSeparator)
        } else {
            editable.denyWriting()
        }
    }

    /**
     * Entered regular number
     */
    private fun manageNumberInput(editable: Editable) {
        if (hasDecimalSeparator) {
            manageNumberInputAfterDecimalSeparator(editable)
        } else {
            manageNumberInputBeforeDecimalSeparator(editable)
        }
    }

    /**
     * Entered regular number after the decimal separator. Check if accepting the char will cause
     * the value to surpass the allowed number of decimals
     */
    private fun manageNumberInputAfterDecimalSeparator(editable: Editable) {
        val textSplitByIntAndDecimal = editable.split(decimalSeparator)
        if (textSplitByIntAndDecimal.size > 1) {
            val decimalPart = textSplitByIntAndDecimal[1]
            if (decimalPart.length > numberOfDecimals) {
                editable.denyWriting()
            }
        }
    }

    /**
     * Entered regular number in a value without decimal separators
     */
    private fun manageNumberInputBeforeDecimalSeparator(editable: Editable) {
        val plainText = editable.deformatAmount(decimalSeparator).toBigDecimal()

        val formattedText = formatter.format(plainText)

        editable.clear()
        editable.append(formattedText)
    }

    /**
     * Only available when there is a valid max digits value. If the new input is bigger than
     * the max value, a callback is sent to the main class to handle the new state
     */
    private fun checkIfValueIsExceeded(editable: Editable) {
        maxValue?.let {
            val newValue = editable.deformatAmount(decimalSeparator).toBigDecimal()
            isExceeded = newValue > it.toBigDecimal()
        }
    }

    private fun isFirstCharEntered(editable: Editable): Boolean {
        return editable.length == 1
    }

    /**
     * Call before working over the [editable] to avoid multiple entrances in the [afterTextChanged] method
     */
    private fun open(editable: Editable) {
        isRunning = true
        filters = editable.filters
        editable.filters = EMPTY_FILTERS
        isExceeded = false
    }

    /**
     * Call after finishing the editable work.
     */
    private fun close(editable: Editable) {
        resizingListener.resizeComponentIfNeeded()
        amountListener.onAmountChanged(isExceeded)
        editable.filters = filters
        isRunning = false
    }

    companion object {
        private val EMPTY_FILTERS = arrayOf<InputFilter>()
    }
}

/**
 * Call when we need to dispose the char entered by the user.
 * Example: the user entered a decimal separator and the component already had one.
 */
private fun Editable.denyWriting() {
    replace(length -1, length, "")
}

/**
 * Call when entering a decimal separator, to verify the displayed separator is the correct according
 * to the selected country
 */
private fun Editable.normalizeDecimalSeparator(decimalSeparator: Char) {
    replace(length -1, length, decimalSeparator.toString())
}
