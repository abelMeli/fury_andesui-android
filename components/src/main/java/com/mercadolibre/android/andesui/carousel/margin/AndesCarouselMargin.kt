package com.mercadolibre.android.andesui.carousel.margin

/**
 * Utility class that does two things: Defines the possible styles an [AndesCarousel] can take
 * because it's an enum, as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * @property padding Possible styles that an [AndesCarousel] may take.
 */
enum class AndesCarouselMargin {
    NONE,
    DEFAULT,
    XSMALL;

    companion object {
        fun fromString(value: String): AndesCarouselMargin = valueOf(value.toUpperCase())
    }

    internal val margin get() = getAndesCarouselMargin()

    private fun getAndesCarouselMargin(): AndesCarouselMarginInterface {
        return when (this) {
            NONE -> AndesCarouselMarginNone
            DEFAULT -> AndesCarouselMarginDefault
            XSMALL -> AndesCarouselMarginXSmall
        }
    }
}
