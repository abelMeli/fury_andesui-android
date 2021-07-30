package com.mercadolibre.android.andesui.linearprogress.size

enum class AndesLinearProgressSize {
    SMALL,
    LARGE;

    companion object {
        fun fromString(value: String): AndesLinearProgressSize = valueOf(value.toUpperCase())
    }

    internal val size get() = getLinearProgressSize()

    private fun getLinearProgressSize(): AndesLinearProgressSizeInterface {
        return when (this) {
            SMALL -> AndesSmallLinearProgressSize()
            LARGE -> AndesLargeLinearProgressSize()
        }
    }
}
