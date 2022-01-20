package com.mercadolibre.android.andesui.slider.steps

internal interface AndesSliderStepsInterface {
    fun stepSize(min: Float, max: Float): Float
    fun isTickVisible(): Boolean
}

internal object AndesSliderStepsNone : AndesSliderStepsInterface {
    override fun stepSize(min: Float, max: Float): Float = 1F
    override fun isTickVisible(): Boolean = false
}

internal class AndesSliderStepsCustom(private val numberSteps: Int) : AndesSliderStepsInterface {
    override fun stepSize(min: Float, max: Float): Float {
        return (max - min) / (numberSteps - 1)
    }

    override fun isTickVisible(): Boolean = true
}
