package com.mercadolibre.android.andesui.inputstepper.status

import android.view.View
import androidx.annotation.ColorRes
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperConfigurator
import com.mercadolibre.android.andesui.inputstepper.AndesInputStepper

/**
 * Contains all status of the [AndesInputStepper]
 * Internally contains all params for each visual configuration
 * @param addButtonVisibility defines the visibility of add action
 * @param removeButtonVisibility defines the visibility of remove action
 * @param progressVisibility defines the visibility of progress component
 * @param valueVisibility defines the visibility of text value or pointer
 * @param containerEnabled switch the enabled status of the inputStepper box
 * @param textColorId defines the text color of the pointer based in color id resource
 */
sealed class AndesInputStepperStatus(
    private val addButtonVisibility: Int,
    private val removeButtonVisibility: Int,
    private val progressVisibility: Int,
    private val valueVisibility: Int,
    private val containerEnabled: Boolean,
    @ColorRes private val textColorId: Int,
) : AndesInputStepperConfigurator {

    object Loading : AndesInputStepperStatus(
        addButtonVisibility = View.GONE,
        removeButtonVisibility = View.GONE,
        progressVisibility = View.VISIBLE,
        valueVisibility = View.GONE,
        containerEnabled = true,
        textColorId = R.color.andes_text_color_primary
    )

    object Disabled : AndesInputStepperStatus(
        addButtonVisibility = View.GONE,
        removeButtonVisibility = View.GONE,
        progressVisibility = View.GONE,
        valueVisibility = View.VISIBLE,
        containerEnabled = false,
        textColorId = R.color.andes_text_color_disabled
    )

    object Enabled : AndesInputStepperStatus(
        addButtonVisibility = View.VISIBLE,
        removeButtonVisibility = View.VISIBLE,
        progressVisibility = View.GONE,
        valueVisibility = View.VISIBLE,
        containerEnabled = true,
        textColorId = R.color.andes_text_color_primary
    )

    override fun applyConfig(binding: AndesInputstepperBinding) {
        setupVisibilityComponents(binding)
        setupEnabledComponent(binding)
        setupTextComponentColor(binding)
    }

    private fun setupVisibilityComponents(binding: AndesInputstepperBinding) {
        binding.andesInputstepperPointer.visibility = valueVisibility
        binding.andesInputstepperActionRemove.visibility = removeButtonVisibility
        binding.andesInputstepperActionAdd.visibility = addButtonVisibility
        binding.andesInputstepperProgress.visibility = progressVisibility
    }

    private fun setupEnabledComponent(binding: AndesInputstepperBinding) {
        binding.andesInputstepperContainer.isEnabled = containerEnabled
    }

    private fun setupTextComponentColor(binding: AndesInputstepperBinding) {
        binding.andesInputstepperPointer.apply {
            val color = resources.getColor(textColorId, context.theme)
            setTextColor(color)
        }
    }
}