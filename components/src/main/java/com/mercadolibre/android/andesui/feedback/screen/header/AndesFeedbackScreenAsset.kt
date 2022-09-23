package com.mercadolibre.android.andesui.feedback.screen.header

import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

sealed class AndesFeedbackScreenAsset(internal val asset: AndesFeedbackScreenAssetInterface) {
    /**
     * This class receives a drawable and a badgeType to return a thumbnail of type AndesFeedbackScreenAsset.
     *
     * @param image Drawable.
     * @param badgeType AndesThumbnailBadgeType
     */
    data class Thumbnail(val image: Drawable, val badgeType: AndesThumbnailBadgeType) :
        AndesFeedbackScreenAsset(AndesThumbnailFeedbackScreenAsset(image, badgeType))
    /**
     * This class receives a drawable and a size to return a image of type AndesFeedbackScreenAsset.
     *
     * @param image Drawable.
     * @param size AndesFeedbackScreenIllustrationSize.
     */
    data class Illustration(val image: Drawable, val size: AndesFeedbackScreenIllustrationSize) :
        AndesFeedbackScreenAsset(AndesIllustrationFeedbackScreenAsset(image, size))

    /**
     * This class receives a string to return a thumbnail with text of type AndesFeedbackScreenAsset.
     *
     * @param text type String.
     */
    data class TextThumbnail(val text: String) :
        AndesFeedbackScreenAsset(AndesThumbnailTextFeedbackScreenAsset(text))
}
