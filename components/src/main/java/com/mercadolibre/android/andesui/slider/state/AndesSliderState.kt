package com.mercadolibre.android.andesui.slider.state

/**
 * Utility class that defines the two possible states an [AndesSlider] can take IDLE,DISABLED.
 *
 * You ask me with, let's say 'IDLE', and then I'll give you a proper implementation of that state.
 */
enum class AndesSliderState(internal val type: AndesSliderStateInterface) {
    IDLE(AndesSliderStateIdle),
    DISABLED(AndesSliderStateDisabled);
}
