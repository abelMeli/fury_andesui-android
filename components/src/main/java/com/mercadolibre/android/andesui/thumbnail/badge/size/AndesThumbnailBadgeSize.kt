package com.mercadolibre.android.andesui.thumbnail.badge.size

import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import java.util.Locale

/**
 * Utility class that does two things: Defines the possible sizes an [AndesThumbnailBadge] can take
 * when [AndeThumbnailBadgeComponent] type is 'Pill' or 'Icon Pill'.
 *
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * @see com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
 */
enum class AndesThumbnailBadgePillSize {
    SIZE_80,
    SIZE_72,
    SIZE_64,
    SIZE_56,
    SIZE_48,
    SIZE_40;

    companion object {
        /**
         * Retrieves a [AndesThumbnailBadgePillSize] from the given enum value in string.
         */
        fun fromString(value: String): AndesThumbnailBadgePillSize = valueOf(value.uppercase(Locale.ROOT))
    }

    internal val size get() = getAndesThumbnailSize()

    private fun getAndesThumbnailSize(): AndesThumbnailSize {
        return when (this) {
            SIZE_80 -> AndesThumbnailSize.SIZE_80
            SIZE_72 -> AndesThumbnailSize.SIZE_72
            SIZE_64 -> AndesThumbnailSize.SIZE_64
            SIZE_56 -> AndesThumbnailSize.SIZE_56
            SIZE_48 -> AndesThumbnailSize.SIZE_48
            SIZE_40 -> AndesThumbnailSize.SIZE_40
        }
    }
}

/**
 * Utility class that does two things: Defines the possible sizes an [AndesThumbnailBadge] can take
 * when [AndeThumbnailBadgeComponent] type is 'Dot'.
 *
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * @property size Possible sizes that an [AndesThumbnailBadge] may take.
 * @see com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
 */
enum class AndesThumbnailBadgeDotSize {
    SIZE_32,
    SIZE_24;

    companion object {
        /**
         * Retrieves a [AndesThumbnailBadgeDotSize] from the given enum value in string.
         */
        fun fromString(value: String): AndesThumbnailBadgeDotSize = valueOf(value.uppercase(Locale.ROOT))
    }

    internal val size get() = getAndesThumbnailSize()

    private fun getAndesThumbnailSize(): AndesThumbnailSize {
        return when (this) {
            SIZE_32 -> AndesThumbnailSize.SIZE_32
            SIZE_24 -> AndesThumbnailSize.SIZE_24
        }
    }
}
