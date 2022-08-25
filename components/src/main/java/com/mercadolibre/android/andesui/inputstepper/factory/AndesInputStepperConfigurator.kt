package com.mercadolibre.android.andesui.inputstepper.factory

import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepper


/**
 * Defines a protocol for apply visual configurations on [AndesInputStepper]
 */
internal interface AndesInputStepperConfigurator {
    /**
     * Method for apply configurations on the view of [AndesInputStepper]
     * @param binding this is an referenced param, all of changes in each
     * component affects the visual state
     */
    fun applyConfig(binding: AndesInputstepperBinding)
}