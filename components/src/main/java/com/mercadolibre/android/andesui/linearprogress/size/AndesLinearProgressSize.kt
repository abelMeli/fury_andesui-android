package com.mercadolibre.android.andesui.linearprogress.size

import java.util.Locale

enum class AndesLinearProgressSize {
    SMALL,
    LARGE;

    companion object {
        fun fromString(value: String): AndesLinearProgressSize = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val size get() = getLinearProgressSize()

    private fun getLinearProgressSize(): AndesLinearProgressSizeInterface {
        return when (this) {
            SMALL -> AndesSmallLinearProgressSize()
            LARGE -> AndesLargeLinearProgressSize()
        }
    }
}
