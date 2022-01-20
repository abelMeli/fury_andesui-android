package com.mercadolibre.android.andesui.slider.type

import android.graphics.drawable.Drawable

/**
 * Utility class that defines the possible left and right content an [AndesSlider] can
 * have: Simple, Icon, Limits.
 */
sealed class AndesSliderType(internal val type: AndesSliderTypeInterface) {
    /**
     * None content to the right and left of the slider.
     */
    object Simple : AndesSliderType(AndesSliderSimple)

    /**
     * Drawable icons to the right and left of the slider.
     */
    class Icon(iconLeft: Drawable? = null, iconRight: Drawable? = null) : AndesSliderType(AndesSliderIcons(iconLeft, iconRight))

    /**
     * String text to the right and left of the slider.
     */
    class Limits(textLeft: String? = null, textRight: String? = null) : AndesSliderType(AndesSliderLimits(textLeft, textRight))
}
