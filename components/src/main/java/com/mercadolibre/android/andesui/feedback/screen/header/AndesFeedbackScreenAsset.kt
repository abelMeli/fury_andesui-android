package com.mercadolibre.android.andesui.feedback.screen.header

import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

sealed class AndesFeedbackScreenAsset(internal val asset: AndesFeedbackScreenAssetInterface) {
    data class Thumbnail(val image: Drawable, val badgeType: AndesThumbnailBadgeType) :
        AndesFeedbackScreenAsset(AndesThumbnailFeedbackScreenAsset(image, badgeType))

    // Illustration(image, size)
}
