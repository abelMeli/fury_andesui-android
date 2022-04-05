package com.mercadolibre.android.andesui.amountfield.size

/**
 * Internal values needed to give the component the proper dimension when the text value is changed.
 */
internal enum class AndesAmountFieldSize(internal val size: AndesAmountFieldSizeInterface) {
    LARGE(AndesAmountFieldSizeLarge),
    MEDIUM(AndesAmountFieldSizeMedium),
    SMALL(AndesAmountFieldSizeSmall),
    EXTRA_SMALL(AndesAmountFieldSizeExtraSmall)
}
