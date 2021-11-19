package com.mercadolibre.android.andesui.textview.color

/**
 * Class with the different color values the [com.mercadolibre.android.andesui.textview.AndesTextView]
 * can take.
 */
sealed class AndesTextViewColor(internal val color: AndesTextViewColorInterface) {
    object Primary : AndesTextViewColor(AndesTextViewColorPrimary)
    object Secondary : AndesTextViewColor(AndesTextViewColorSecondary)
    object Disabled : AndesTextViewColor(AndesTextViewColorDisabled)
    object Inverted : AndesTextViewColor(AndesTextViewColorInverted)
    object Negative : AndesTextViewColor(AndesTextViewColorNegative)
    object Caution : AndesTextViewColor(AndesTextViewColorCaution)
    object Positive : AndesTextViewColor(AndesTextViewColorPositive)
    internal data class Link(val isInverted: Boolean = false) : AndesTextViewColor(AndesTextViewColorLink(isInverted))
    internal data class Custom(val customColorInt: Int) : AndesTextViewColor(AndesTextViewColorCustom(customColorInt))
}
