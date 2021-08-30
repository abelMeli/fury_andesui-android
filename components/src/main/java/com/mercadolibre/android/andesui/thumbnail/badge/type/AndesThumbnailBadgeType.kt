package com.mercadolibre.android.andesui.thumbnail.badge.type


/**
 * Utility class that does two things: Defines the possible types an [AndesThumbnailBadge] thumbnail can take.
 *
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 */
sealed class AndesThumbnailBadgeType {
    /**
     * Will tint the [AndesThumbnail] and set a fit_content scale type to the image.
     */
    object Icon : AndesThumbnailBadgeType()

    /**
     * Will not tint the [AndesThumbnail] and will set a center_crop scale type to the image.
     */
    object ImageCircle : AndesThumbnailBadgeType()

    companion object {
        /**
         * Retrieves a [AndesThumbnailBadgeType] from the given enum value in string.
         */
        fun fromString(value: String): AndesThumbnailBadgeType {
            return when (value) {
                Icon.javaClass.simpleName -> Icon
                else -> ImageCircle
            }
        }
    }

    internal val type get() = getAndesThumbnailBadgeType()

    private fun getAndesThumbnailBadgeType(): AndesThumbnailBadgeTypeInterface {
        return when (this) {
            Icon -> AndesIconThumbnailBadgeType
            ImageCircle -> AndesImageCircleThumbnailBadgeType
        }
    }
}
