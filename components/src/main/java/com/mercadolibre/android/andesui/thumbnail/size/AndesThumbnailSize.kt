package com.mercadolibre.android.andesui.thumbnail.size

import java.util.Locale

/**
 * Utility class that does two things: Defines the possible sizes an [AndesThumbnail] can take because it's an enum,
 * as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'SMALL', and then I'll give you a proper implementation of that size.
 *
 * @property size Possible sizes that an [AndesThumbnail] may take.
 */
enum class AndesThumbnailSize {
    SIZE_24,
    SIZE_32,
    SIZE_40,
    SIZE_48,
    SIZE_56,
    SIZE_64,
    SIZE_72,
    SIZE_80;

    companion object {
        fun fromString(value: String): AndesThumbnailSize = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val size get() = getAndesBadgeSize()

    private fun getAndesBadgeSize(): AndesThumbnailSizeInterface {
        return when (this) {
            SIZE_24 -> Andes24ThumbnailSize()
            SIZE_32 -> Andes32ThumbnailSize()
            SIZE_40 -> Andes40ThumbnailSize()
            SIZE_48 -> Andes48ThumbnailSize()
            SIZE_56 -> Andes56ThumbnailSize()
            SIZE_64 -> Andes64ThumbnailSize()
            SIZE_72 -> Andes72ThumbnailSize()
            SIZE_80 -> Andes80ThumbnailSize()
        }
    }
}
