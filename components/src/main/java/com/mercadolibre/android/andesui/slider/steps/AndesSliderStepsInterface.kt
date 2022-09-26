package com.mercadolibre.android.andesui.slider.steps

internal interface AndesSliderStepsInterface {
    fun stepSize(min: Float, max: Float): Float
    fun isTickVisible(): Boolean
    fun getSteps(): Int
}

internal object AndesSliderStepsNone : AndesSliderStepsInterface {
    override fun stepSize(min: Float, max: Float): Float = 1F
    override fun isTickVisible(): Boolean = false
    override fun getSteps(): Int = 0
}

internal class AndesSliderStepsCustom(private val numberSteps: Int) : AndesSliderStepsInterface {
    override fun stepSize(min: Float, max: Float): Float {
        return (max - min) / (numberSteps - 1)
    }

    override fun isTickVisible(): Boolean = true

    override fun getSteps(): Int = numberSteps
}
