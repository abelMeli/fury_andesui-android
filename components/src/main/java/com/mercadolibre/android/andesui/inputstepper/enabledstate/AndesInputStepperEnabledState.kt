package com.mercadolibre.android.andesui.inputstepper.enabledstate

import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperConfigurator
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepper

/**
 * Contains all of the operational states of the [AndesInputStepper]
 * these states are internal and only works when the state is Enabled
 * Internally manage the enabled status for its actions
 * @param addButtonEnabled: Defines the enabled status of add action
 * @param removeButtonEnabled: Defines the enabled status of remove action
 */
sealed class AndesInputStepperEnabledState(
    private val addButtonEnabled: Boolean,
    private val removeButtonEnabled: Boolean,
) : AndesInputStepperConfigurator {

    object MaxSelected : AndesInputStepperEnabledState(
        addButtonEnabled = false,
        removeButtonEnabled = true,
    )

    object MinSelected : AndesInputStepperEnabledState(
        addButtonEnabled = true,
        removeButtonEnabled = false,
    )

    object InRange : AndesInputStepperEnabledState(
        addButtonEnabled = true,
        removeButtonEnabled = true,
    )

    override fun applyConfig(binding: AndesInputstepperBinding) {
        setupActionRemove(binding)
        setupActionAdd(binding)
    }

    private fun setupActionRemove(binding: AndesInputstepperBinding) {
        binding.andesInputstepperActionRemove.isEnabled = removeButtonEnabled
    }

    private fun setupActionAdd(binding: AndesInputstepperBinding) {
        binding.andesInputstepperActionAdd.isEnabled = addButtonEnabled
    }

}