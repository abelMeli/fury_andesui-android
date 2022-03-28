package com.mercadolibre.android.andesui.thumbnail.badge.component

import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.badge.type.AndesBadgeType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgeDotSize
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize

/**
 * Utility class that defines the possible badges an [AndesThumbnailBadge] can take.
 *
 * @see com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
 */
sealed class AndesThumbnailBadgeComponent(internal val color: AndesBadgeType) {
    /**
     * Will draw an [AndesBadgePill] over the thumbnail with the given parameters. Pill will always have
     * 'LOUD' and 'STANDARD' configuration
     *
     * @param color Represents badge, outline and thumbnail tint color.
     * @param text Content of the badge.
     * @param textStyleDefault Sets the text in uppercase if true.
     * @param thumbnailSize Represents thumbnail size. Depending on the thumbnail size the pill will
     * adapt its size to improve the thumbnail visualization.
     *
     * @see com.mercadolibre.android.andesui.badge.AndesBadgePill
     */
    class Pill(
        color: AndesBadgeIconType,
        val text: String? = null,
        val textStyleDefault: Boolean = true,
        val thumbnailSize: AndesThumbnailBadgePillSize = AndesThumbnailBadgePillSize.SIZE_64
    ) : AndesThumbnailBadgeComponent(color.iconType)

    /**
     * Will draw an [AndesBadgeDot] over the thumbnail with the given parameters.
     *
     * @param color Represents badge, outline and thumbnail tint color.
     * @param thumbnailSize Represents thumbnail size.
     *
     * @see com.mercadolibre.android.andesui.badge.AndesBadgeDot
     */
    class Dot(
        color: AndesBadgeIconType,
        val thumbnailSize: AndesThumbnailBadgeDotSize = AndesThumbnailBadgeDotSize.SIZE_24
    ) : AndesThumbnailBadgeComponent(color.iconType)

    /**
     * Will draw an [AndesBadgeIconPill] over the thumbnail with the given parameters.
     *
     * @param color Represents badge, outline and thumbnail tint color.
     * @param thumbnailSize Represents thumbnail size.
     *
     * @see com.mercadolibre.android.andesui.badge.AndesBadgeIconPill
     */
    class IconPill(
        color: AndesBadgeIconType,
        val thumbnailSize: AndesThumbnailBadgePillSize = AndesThumbnailBadgePillSize.SIZE_64
    ) : AndesThumbnailBadgeComponent(color.iconType)

    /**
     * Will draw an [AndesBadgeIconPill] over the thumbnail with the given parameters.
     *
     * @param feedbackColor Represents badge, outline and thumbnail tint color.
     * @param thumbnailSize Represents thumbnail size.
     *
     * @see com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackBadgeIconType
     */
    internal class FeedbackIconPill(
        val feedbackColor: AndesFeedbackBadgeIconType,
        val thumbnailSize: AndesThumbnailBadgePillSize = AndesThumbnailBadgePillSize.SIZE_64
    ) : AndesThumbnailBadgeComponent(feedbackColor.type)

    internal val badgeComponent get() = getAndesThumbnailBadgeType()

    private fun getAndesThumbnailBadgeType(): AndesThumbnailBadgeComponentInterface {
        return when (this) {
            is Pill -> AndesPillThumbnailBadgeComponent(
                text,
                textStyleDefault,
                color,
                thumbnailSize.size
            )
            is Dot -> AndesDotThumbnailBadgeComponent(color, thumbnailSize.size)
            is IconPill -> AndesIconPillThumbnailBadgeComponent(color, thumbnailSize.size)
            is FeedbackIconPill -> AndesFeedbackIconPillThumbnailBadgeComponent(
                feedbackColor.type,
                thumbnailSize.size
            )
        }
    }
}
