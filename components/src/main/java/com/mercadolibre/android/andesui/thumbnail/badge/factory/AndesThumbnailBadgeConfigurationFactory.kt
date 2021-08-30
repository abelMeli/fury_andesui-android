package com.mercadolibre.android.andesui.thumbnail.badge.factory

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponentInterface
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize
import com.mercadolibre.android.andesui.thumbnail.type.AndesThumbnailType

internal data class AndesThumbnailBadgeConfiguration(
    val thumbnailType: AndesThumbnailType,
    val badgeComponent: AndesThumbnailBadgeComponentInterface,
    val badgeColor: Int,
    val image: Drawable,
    val thumbnailTintColor: ColorStateList?,
    val thumbnailSize: AndesThumbnailSize,
    val badgeOutline: Int
)

@Suppress("TooManyFunctions")
internal object AndesThumbnailBadgeConfigurationFactory {
    fun create(
        context: Context,
        andesThumbnailAttrs: AndesThumbnailBadgeAttrs
    ): AndesThumbnailBadgeConfiguration {
        return with(andesThumbnailAttrs) {
            val thumbnailType = thumbnailType.type
            val badgeComponent = badge.badgeComponent
            val badgeColor = resolveBadgeColor(context, badgeComponent)
            AndesThumbnailBadgeConfiguration(
                image = image,
                thumbnailType = thumbnailType.getThumbnailType(),
                thumbnailTintColor = thumbnailType.getTintColor(
                    context,
                    badgeColor
                ),
                badgeComponent = badgeComponent,
                thumbnailSize = badgeComponent.thumbnailSize,
                badgeColor = badgeColor,
                badgeOutline = badgeComponent.getBadgeOutlineSize(context)
            )
        }
    }

    private fun resolveBadgeColor(context: Context, badge: AndesThumbnailBadgeComponentInterface) =
        badge.badgeType.type.primaryColor().colorInt(context)
}
