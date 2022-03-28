package com.mercadolibre.android.andesui.feedback.screen.header

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenTypeInterface
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

internal interface AndesFeedbackScreenAssetInterface {
    val image: Drawable
    fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View
}

internal class AndesThumbnailFeedbackScreenAsset(
    override val image: Drawable,
    private val thumbnailBadgeType: AndesThumbnailBadgeType
) : AndesFeedbackScreenAssetInterface {
    override fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View {
        return AndesThumbnailBadge(
            context,
            image,
            AndesThumbnailBadgeComponent.IconPill(
                AndesBadgeIconType.HIGHLIGHT
            )
        ).apply {
            image = this@AndesThumbnailFeedbackScreenAsset.image
            thumbnailType = thumbnailBadgeType
            badgeComponent = AndesThumbnailBadgeComponent.FeedbackIconPill(
                type.feedbackColor,
                type.getThumbnailBadgeSize(hasBody)
            )
        }
    }
}

internal class AndesIllustrationFeedbackScreenAsset(
    override val image: Drawable,
    private val size: AndesFeedbackScreenIllustrationSize
) : AndesFeedbackScreenAssetInterface {
    override fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View {
        return ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                context.resources.getDimensionPixelSize(size.width),
                context.resources.getDimensionPixelSize(size.height)
            )
            setImageDrawable(image)
        }
    }

}