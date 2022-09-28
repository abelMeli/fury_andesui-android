package com.mercadolibre.android.andesui.slider.utils

internal object AndesSliderUtils {

    fun getStepFrom(min: Float): Int {
        return if (min == 0f) {
            0
        } else {
            1
        }
    }

    fun getCurrentStep(index: Int, min: Float, jumpSteps: Float): Float {
        return if (min == 0f) {
            index * jumpSteps
        } else {
            min + index * jumpSteps
        }
    }
}
