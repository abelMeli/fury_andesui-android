package com.mercadolibre.android.andesui.amountfield.utils

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.mercadolibre.android.andesui.utils.AmountUtils
import java.math.BigDecimal
import kotlin.math.pow

internal class AndesAmountFieldDecimalFormatter(
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
    private var filters = arrayOf<InputFilter>()
    private val formatter = AmountUtils.getAmountFormatter(numberOfDecimals, decimalSeparator)
    private val divider by lazy {
        val bigDecimalVal = BIG_DECIMAL_BASE.pow(numberOfDecimals)
        BigDecimal(bigDecimalVal.toInt())
    }

    override fun beforeTextChanged(oldText: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(newText: CharSequence, start: Int, before: Int, count: Int) {
        // no-op
    }

    override fun afterTextChanged(editable: Editable) {
        if (isRunning) {
            return
        }

        open(editable)

        verifyIfBlocked(editable)

        if (editable.isEmpty()) {
            close(editable)
        } else {
            manageInput(editable)
        }

        close(editable)
    }


    private fun verifyIfBlocked(editable: Editable) {
        if (isBlocked && !isDeleting) {
            editable.denyWriting()
        }
    }

    private fun manageInput(editable: Editable) {
        val shouldReformat = !verifyIfValueIsZero(editable)

        if (shouldReformat) {
            val parsedValueInBigDecimal = formatValueToBigDecimal(editable)

            checkIfValueIsExceeded(parsedValueInBigDecimal)
            addNewValue(editable, parsedValueInBigDecimal)
        } else {
            editable.clear()
        }
    }

    private fun verifyIfValueIsZero(editable: Editable): Boolean {
        var zeroValue = "0$decimalSeparator"
        repeat(numberOfDecimals - 1) {
            zeroValue += ZERO
        }

        return editable.toString() == zeroValue
    }

    private fun formatValueToBigDecimal(editable: Editable): BigDecimal {
        val cleanString =
            editable.toString()
                .replace("[$,.]".toRegex(), EMPTY_STRING)
                .takeIf { it.isNotEmpty() } ?: ZERO
        return BigDecimal(cleanString)
            .setScale(numberOfDecimals, BigDecimal.ROUND_FLOOR)
            .divide(divider, BigDecimal.ROUND_FLOOR)
    }

    private fun addNewValue(editable: Editable, newValue: BigDecimal) {
        editable.clear()
        editable.append(formatter.format(newValue))
    }

    /**
     * Only available when there is a valid max digits value. If the new input is bigger than
     * the max value, a callback is sent to the main class to handle the new state
     */
    private fun checkIfValueIsExceeded(value: BigDecimal) {
        maxValue?.let {
            isExceeded = value > BigDecimal(it)
        }
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
        private const val BIG_DECIMAL_BASE = 10f
        private const val EMPTY_STRING = ""
        private const val ZERO = "0"
    }
}

/**
 * Call when we need to dispose the char entered by the user.
 * Example: the user entered a decimal separator and the component already had one.
 */
private fun Editable.denyWriting() {
    replace(length - 1, length, "")
}
