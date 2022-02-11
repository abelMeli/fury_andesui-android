package com.mercadolibre.android.andesui.progress.size

import java.util.Locale

enum class AndesProgressSize {
    SMALL,
    MEDIUM,
    LARGE,
    XLARGE;

    companion object {
        fun fromString(value: String): AndesProgressSize = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val size get() = getAndesButtonSize()

    private fun getAndesButtonSize(): AndesProgressSizeInterface {
        return when (this) {
            SMALL -> AndesSmallPogressSize()
            MEDIUM -> AndesMediumProgressSize()
            LARGE -> AndesLargeProgressSize()
            XLARGE -> AndesXLargeProgressSize()
        }
    }
}
