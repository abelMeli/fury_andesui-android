package com.mercadolibre.android.andesui.slider.factory

import android.content.Context
import android.view.View
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.type.AndesSliderType

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

            AndesSliderConfiguration(
                    text = andesSliderTitle,
                    state = andesSliderState,
                    min = andesSliderMin,
                    max = andesSliderMax,
                    value = andesSliderValue,
                    type = andesSliderType,
                    rightComponent = sliderType.rightComponent(context),
                    leftComponent = sliderType.leftComponent(context),
                    isTickVisible = steps.isTickVisible(),
                    jumpSteps = steps.stepSize(andesSliderMin, andesSliderMax)
            )
        }
    }
}
