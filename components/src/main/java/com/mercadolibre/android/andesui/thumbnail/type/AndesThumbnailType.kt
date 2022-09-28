package com.mercadolibre.android.andesui.thumbnail.type

import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import java.util.Locale

/**
 * Utility class that does two things: Defines the possible styles an [AndesThumbnail] can take because it's an enum,
 * as you can see.
 * But as a bonus it gives you the proper implementation so you don't have to make any mapping.
 *
 * You ask me with, let's say 'COLOR', and then I'll give you a proper implementation of that style.
 *
 * @property type Possible styles that an [AndesThumbnail] may take.
*/
@Deprecated("Use AndesThumbnailAssetType")
enum class AndesThumbnailType {
    ICON,
    IMAGE_CIRCLE,
    IMAGE_SQUARE;

    companion object {
        /**
         * Retrieves a [AndesThumbnailType] from the given enum value in string.
         */
        fun fromString(value: String): AndesThumbnailType = valueOf(value.uppercase(Locale.ROOT))
    }

    internal val type get() = getAndesThumbnailType()

    private fun getAndesThumbnailType(): AndesThumbnailTypeInterface {
        return when (this) {
            ICON -> AndesIconThumbnailType()
            IMAGE_CIRCLE -> AndesImageCircleThumbnailType()
            IMAGE_SQUARE -> AndesImageSquareThumbnailType()
        }
    }

    internal fun toShape(): AndesThumbnailShape {
        return when (this) {
            ICON -> AndesThumbnailShape.Circle
            IMAGE_CIRCLE -> AndesThumbnailShape.Circle
            IMAGE_SQUARE -> AndesThumbnailShape.Square
        }
    }

    internal fun toAssetType(drawable: Drawable): AndesThumbnailAssetType {
        return when (this) {
            ICON -> AndesThumbnailAssetType.Icon(drawable)
            IMAGE_CIRCLE -> AndesThumbnailAssetType.Image(drawable)
            IMAGE_SQUARE -> AndesThumbnailAssetType.Image(drawable)
        }
    }
}
