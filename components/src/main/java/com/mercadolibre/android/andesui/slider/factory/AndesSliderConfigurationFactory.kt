package com.mercadolibre.android.andesui.slider.factory

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.slider.utils.AndesSliderUtils.getCurrentStep
import com.mercadolibre.android.andesui.slider.utils.AndesSliderUtils.getStepFrom
import kotlin.math.roundToInt

internal data class AndesSliderConfiguration(
    val text: String?,
    val state: AndesSliderState,
    val min: Float,
    val max: Float,
    val value: Float,
    val type: AndesSliderType,
    val rightComponent: View?,
    val leftComponent: View?,
    val jumpSteps: Float,
    val isTickVisible: Boolean
)

internal object AndesSliderConfigurationFactory {
    fun create(andesSliderAttrs: AndesSliderAttrs, context: Context): AndesSliderConfiguration {
        return with(andesSliderAttrs) {
            val sliderType = andesSliderType.type
            val steps = andesSliderSteps.type
            val jumpSteps = steps.stepSize(andesSliderMin, andesSliderMax)

            AndesSliderConfiguration(
                text = andesSliderTitle,
                state = andesSliderState,
                min = andesSliderMin,
                max = andesSliderMax,
                value = resolveValue(andesSliderValue, andesSliderMin, jumpSteps, andesSliderSteps),
                type = andesSliderType,
                rightComponent = sliderType.rightComponent(context),
                leftComponent = sliderType.leftComponent(context),
                isTickVisible = steps.isTickVisible(),
                jumpSteps = jumpSteps
            )
        }
    }

    private fun resolveValue(value: Float, min: Float, jumpSteps: Float, steps: AndesSliderSteps): Float {
        return when (steps) {
            AndesSliderSteps.None -> if (value < min) min else value
            is AndesSliderSteps.Custom -> getValueFromCustom(value, min, jumpSteps, steps)
        }
    }

    private fun getValueFromCustom(value: Float, min: Float, jumpSteps: Float, steps: AndesSliderSteps.Custom): Float {
        var result = min

        for (i in getStepFrom(min)..steps.type.getSteps()) {
            val step = getCurrentStep(i, min, jumpSteps)
            if (step.roundToInt() == value.roundToInt()) {
                result = step
            }
        }
        return result
    }
}
