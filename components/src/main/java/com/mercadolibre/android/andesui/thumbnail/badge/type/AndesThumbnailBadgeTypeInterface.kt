package com.mercadolibre.android.andesui.thumbnail.badge.type

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

/**
 * Defines all types related properties that an AndesThumbnail needs to be drawn properly.
 * Those properties change depending on the style of the thumbnail.
 */
internal interface AndesThumbnailBadgeTypeInterface {
    fun getThumbnailType(): AndesThumbnailType
    fun getTintColor(context: Context, color: Int): ColorStateList?
    fun badgeVisibility(): Int
}

internal object AndesIconThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.ICON
    override fun getTintColor(context: Context, color: Int): ColorStateList =
        ColorStateList.valueOf(color)
    override fun badgeVisibility(): Int = View.VISIBLE
}

internal object AndesImageCircleThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.IMAGE_CIRCLE
    override fun getTintColor(context: Context, color: Int): ColorStateList? = null
    override fun badgeVisibility(): Int = View.VISIBLE
}

internal object AndesFeedbackIconThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.ICON
    override fun getTintColor(context: Context, color: Int): ColorStateList =
        ColorStateList.valueOf(color)
    override fun badgeVisibility(): Int = View.GONE
}

internal object AndesTextThumbnailBadgeType : AndesThumbnailBadgeTypeInterface {
    override fun getThumbnailType() = AndesThumbnailType.ICON
    override fun getTintColor(context: Context, color: Int): ColorStateList =
        ColorStateList.valueOf(color)
    override fun badgeVisibility(): Int = View.VISIBLE
}
