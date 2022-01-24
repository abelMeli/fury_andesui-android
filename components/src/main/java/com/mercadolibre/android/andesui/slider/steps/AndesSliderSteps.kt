package com.mercadolibre.android.andesui.slider.steps

/**
 * Utility class that defines the two possible step styles an [AndesSlider] can take: None, Custom.
 */
sealed class AndesSliderSteps(internal val type: AndesSliderStepsInterface) {
    /**
     * Slider without steps. All values can be selected.
     */
    object None : AndesSliderSteps(AndesSliderStepsNone)

    /**
     * Slider will be divided in [numberOfSteps] and only can be range selected
     */
    class Custom(numberOfSteps: Int = 3) : AndesSliderSteps(AndesSliderStepsCustom(numberOfSteps)) {

        init {
            if (numberOfSteps < MIN_STEP || numberOfSteps > MAX_STEP) {
                throw IllegalArgumentException("Value $numberOfSteps must be between $MIN_STEP and $MAX_STEP steps")
            }
        }

        private companion object {
            private const val MIN_STEP = 3
            private const val MAX_STEP = 11
        }
    }
}
