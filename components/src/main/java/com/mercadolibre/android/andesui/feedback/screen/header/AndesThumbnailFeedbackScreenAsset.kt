package com.mercadolibre.android.andesui.feedback.screen.header

import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

internal interface AndesFeedbackScreenAssetInterface {
    val image: Drawable
    val thumbnailBadgeType: AndesThumbnailBadgeType
}

internal class AndesThumbnailFeedbackScreenAsset(
    override val image: Drawable,
    override val thumbnailBadgeType: AndesThumbnailBadgeType
) : AndesFeedbackScreenAssetInterface
