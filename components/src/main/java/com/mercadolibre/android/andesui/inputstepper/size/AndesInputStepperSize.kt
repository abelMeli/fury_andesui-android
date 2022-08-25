package com.mercadolibre.android.andesui.inputstepper.size

import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.factory.AndesInputStepperConfigurator
import com.mercadolibre.android.andesui.progress.size.AndesProgressSize

/**
 * Contains all size variations for [AndesProgressSize]
 * Internally contains all params for each visual configuration
 * @param textSizeDimen: Defines the textSize in the pointer
 * @param heightDimen: Defines the height of the inputStepper box
 * @param actionSizeDimen: Defines the size of the actions add and remove buttons
 * @param progressSize: Defines the size of progress at status loading
 * @param plusIconRes: Defines the src drawable of the action add
 * @param removeIconRes: Defines the src drawable of the action remove
 */
sealed class AndesInputStepperSize(
    @DimenRes private val textSizeDimen: Int,
    @DimenRes private val heightDimen: Int,
    @DimenRes private val actionSizeDimen: Int,
    private val progressSize: AndesProgressSize,
    @DrawableRes private val plusIconRes: Int,
    @DrawableRes private val removeIconRes: Int,
) : AndesInputStepperConfigurator {
    object Large : AndesInputStepperSize(
        textSizeDimen = R.dimen.andes_text_size_inputstepper_large,
        heightDimen = R.dimen.andes_inputstepper_height_large,
        actionSizeDimen = R.dimen.andes_inputstepper_action_size_large,
        progressSize = AndesProgressSize.MEDIUM,
        plusIconRes = R.drawable.andes_ui_sumar_20,
        removeIconRes = R.drawable.andes_ui_restar_20
    )

    object Small : AndesInputStepperSize(
        textSizeDimen = R.dimen.andes_text_size_inputstepper_small,
        heightDimen = R.dimen.andes_inputstepper_height_small,
        actionSizeDimen = R.dimen.andes_inputstepper_action_size_small,
        progressSize = AndesProgressSize.SMALL,
        plusIconRes = R.drawable.andes_ui_sumar_16,
        removeIconRes = R.drawable.andes_ui_restar_16
    )

    override fun applyConfig(binding: AndesInputstepperBinding) {
        setupHeight(binding)
        setupTextComponent(binding)
        setupPlusAction(binding)
        setupRemoveAction(binding)
        setupProgressSize(binding)
    }

    private fun setupHeight(binding: AndesInputstepperBinding) {
        binding.andesInputstepperContainer.apply {
            val heightPx = context.resources.getDimension(heightDimen)
            minHeight = heightPx.toInt()
            maxHeight = heightPx.toInt()
        }
    }

    private fun setupTextComponent(binding: AndesInputstepperBinding) =
        binding.andesInputstepperPointer.apply {
            val size = context.resources.getDimension(textSizeDimen)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }

    private fun setupPlusAction(binding: AndesInputstepperBinding) =
        binding.andesInputstepperActionAdd.apply {
            val size = context.resources.getDimension(actionSizeDimen)
            val icon = ResourcesCompat.getDrawable(context.resources, plusIconRes, context.theme)
            layoutParams = layoutParams.apply {
                height = size.toInt()
                width = size.toInt()
            }
            setImageDrawable(icon)
        }

    private fun setupRemoveAction(binding: AndesInputstepperBinding) =
        binding.andesInputstepperActionRemove.apply {
            val size = context.resources.getDimension(actionSizeDimen)
            val icon = ResourcesCompat.getDrawable(context.resources, removeIconRes, context.theme)
            layoutParams = layoutParams.apply {
                height = size.toInt()
                width = size.toInt()
            }
            setImageDrawable(icon)
        }

    private fun setupProgressSize(binding: AndesInputstepperBinding) =
        binding.andesInputstepperProgress.apply {
            size = progressSize
        }
}
