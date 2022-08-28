package com.mercadolibre.android.andesui.inputstepper.factory

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus

/**
 * This object parse the attribute set and return an instance of AndesInputStepperAttrs to be used by InputStepper
 */
internal object AndesInputStepperAttrsParser {

    private const val ANDES_INPUTSTEPPER_LARGE = 1000
    private const val ANDES_INPUTSTEPPER_SMALL = 1001

    private const val ANDES_INPUTSTEPPER_IDLE = 2000
    private const val ANDES_INPUTSTEPPER_DISABLED = 2001
    private const val ANDES_INPUTSTEPPER_LOADING = 2002


    fun parse(context: Context, attr: AttributeSet?): AndesInputStepperAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesInputStepper)

        return AndesInputStepperAttrs(
            value = typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperValue, 0),
            maxValue = typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperMaxValue, Int.MAX_VALUE),
            minValue = typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperMinValue, 0),
            step = typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperStep, 1),
            size = parseAndesInputStepperSize(typedArray),
            status = getAndesInputStepperStatus(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun parseAndesInputStepperSize(typedArray: TypedArray): AndesInputStepperSize {
        return when (typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperSize, 0)) {
            ANDES_INPUTSTEPPER_LARGE -> AndesInputStepperSize.Large
            ANDES_INPUTSTEPPER_SMALL -> AndesInputStepperSize.Small
            else -> AndesInputStepperSize.Large
        }
    }

    private fun getAndesInputStepperStatus(typedArray: TypedArray): AndesInputStepperStatus {
        return when (typedArray.getInt(R.styleable.AndesInputStepper_andesInputStepperStatus, 0)) {
            ANDES_INPUTSTEPPER_IDLE -> AndesInputStepperStatus.Enabled
            ANDES_INPUTSTEPPER_DISABLED -> AndesInputStepperStatus.Disabled
            ANDES_INPUTSTEPPER_LOADING -> AndesInputStepperStatus.Loading
            else -> AndesInputStepperStatus.Enabled
        }
    }
}