package com.mercadolibre.android.andesui.inputstepper.factory

import com.mercadolibre.android.andesui.inputstepper.AndesInputStepper
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepperTextDataSource
import com.mercadolibre.android.andesui.inputstepper.enabledstate.AndesInputStepperEnabledState

internal class AndesInputStepperContent(
    val value: Int,
    val state: AndesInputStepperEnabledState,
    val text: CharSequence,
    val mustNotify: Boolean,
)

/**
 * Object used to help in [AndesInputStepper]
 * internal logic. It will make all decisions and calculations to return an [AndesInputStepperContent]
 * that will be used to update the InputStepper.
 */
internal object AndesInputStepperContentFactory {
    fun create(
        currentValue: Int,
        requestedValue: Int,
        maxValue: Int,
        minValue: Int,
        dataSource: AndesInputStepperTextDataSource?,
    ): AndesInputStepperContent {

        val newValue = resolveNewValue(requestedValue, maxValue, minValue)

        return AndesInputStepperContent(
            value = newValue,
            state = resolveNewState(newValue, maxValue, minValue),
            text = resolveText(newValue, dataSource),
            mustNotify = currentValue != newValue
        )
    }

    /**
     * resolves an [AndesInputStepperEnabledState] based in the new value and its limits
     */
    private fun resolveNewState(newValue: Int, maxValue: Int, minValue: Int): AndesInputStepperEnabledState {
        return when {
            newValue <= minValue -> AndesInputStepperEnabledState.MinSelected
            newValue >= maxValue -> AndesInputStepperEnabledState.MaxSelected
            else -> AndesInputStepperEnabledState.InRange
        }
    }

    /**
     * resolves a text based in the new value and its datasource.
     * that will used in the pointer
     */
    private fun resolveText(
        newValue: Int,
        dataSource: AndesInputStepperTextDataSource?,
    ): CharSequence {
        return dataSource?.getText(newValue) ?: newValue.toString()
    }

    /**
     * resolves a new value based in the requested value and input stepper's limits.
     */
    private fun resolveNewValue(requestedValue: Int, maxValue: Int, minValue: Int): Int {
        return when {
            requestedValue <= minValue -> minValue
            requestedValue >= maxValue -> maxValue
            else -> requestedValue
        }
    }
}