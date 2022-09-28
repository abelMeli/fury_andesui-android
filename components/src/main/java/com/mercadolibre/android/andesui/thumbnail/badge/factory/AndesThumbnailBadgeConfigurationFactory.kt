package com.mercadolibre.android.andesui.thumbnail.badge.factory

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.thumbnail.assetType.AndesThumbnailAssetType
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponentInterface
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType
import com.mercadolibre.android.andesui.thumbnail.shape.AndesThumbnailShape
import com.mercadolibre.android.andesui.thumbnail.size.AndesThumbnailSize


internal data class AndesThumbnailBadgeConfiguration(
    val badgeComponent: AndesThumbnailBadgeComponentInterface,
    val badgeColor: Int,
    val thumbnailTintColor: ColorStateList?,
    val thumbnailSize: AndesThumbnailSize,
    val badgeOutline: Int,
    val badgeVisibility: Int,
    val assetType: AndesThumbnailAssetType,
    val shape: AndesThumbnailShape,
    val text: String
)

@Suppress("TooManyFunctions")
internal object AndesThumbnailBadgeConfigurationFactory {
    fun create(
        context: Context,
        andesThumbnailAttrs: AndesThumbnailBadgeAttrs
    ): AndesThumbnailBadgeConfiguration {
        return with(andesThumbnailAttrs) {
            val thumbnailTypeInterface = thumbnailType.type
            val badgeComponent = badge.badgeComponent
            val badgeColor = resolveBadgeColor(context, badgeComponent)
            AndesThumbnailBadgeConfiguration(
                thumbnailTintColor = thumbnailTypeInterface.getTintColor(
                    context,
                    badgeColor
                ),
                badgeComponent = badgeComponent,
                thumbnailSize = badgeComponent.thumbnailSize,
                badgeColor = badgeColor,
                badgeOutline = badgeComponent.getBadgeOutlineSize(context),
                badgeVisibility = thumbnailTypeInterface.badgeVisibility(),
                assetType = resolveAssetType(thumbnailType, image, text),
                shape = AndesThumbnailShape.Circle,
                text = text
            )
        }
    }

    private fun resolveBadgeColor(context: Context, badge: AndesThumbnailBadgeComponentInterface) =
        badge.badgeType.type.thumbnailBadgeOutlineColor().colorInt(context)

    private fun resolveAssetType(
        badgeType: AndesThumbnailBadgeType,
        image: Drawable?,
        text: String
    ): AndesThumbnailAssetType {
        return when (badgeType) {
            is AndesThumbnailBadgeType.Icon -> AndesThumbnailAssetType.Icon(
                image
                    ?: throw IllegalArgumentException("The ThumbnailBadge of type Icon requires a image resource.")
            )
            is AndesThumbnailBadgeType.ImageCircle -> AndesThumbnailAssetType.Image(
                image
                    ?: throw IllegalArgumentException("The ThumbnailBadge of type Image requires a image resource.")
            )
            is AndesThumbnailBadgeType.Text -> AndesThumbnailAssetType.Text(text.takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("The ThumbnailBadge of type Text requires a text resource."))
            is AndesThumbnailBadgeType.FeedbackIcon -> AndesThumbnailAssetType.Icon(
                image
                    ?: throw IllegalArgumentException("The ThumbnailBadge of type Feedback Icon requires a image resource.")
            )
        }
    }

}
