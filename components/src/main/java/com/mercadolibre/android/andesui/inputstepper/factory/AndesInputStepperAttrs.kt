package com.mercadolibre.android.andesui.inputstepper.factory

import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus

/**
 * The data class that contains the properties of the inputStepper.
 */
internal data class AndesInputStepperAttrs(
    val size: AndesInputStepperSize,
    val status: AndesInputStepperStatus,
    val value: Int,
    val maxValue: Int,
    val minValue: Int,
    val step: Int
)