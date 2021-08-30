package com.mercadolibre.android.andesui.thumbnail.badge.type

import android.content.Context
import android.content.res.ColorStateList
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

/**
 * Defines all types related properties that an [AndesThumbnail] needs to be drawn properly.
 * Those properties change depending on the style of the thumbnail.
 */
internal interface AndesThumbnailBadgeTypeInterface {
    fun getThumbnailType(): AndesThumbnailType
    fun getTintColor(context: Context, color: Int): ColorStateList?
}

internal object AndesIconThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.ICON
    override fun getTintColor(context: Context, color: Int): ColorStateList? =
        ColorStateList.valueOf(color)
}

internal object AndesImageCircleThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.IMAGE_CIRCLE
    override fun getTintColor(context: Context, color: Int): ColorStateList? = null
}
