package com.mercadolibre.android.andesui.feedback.screen.header

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.icontype.AndesBadgeIconType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenTypeInterface
import com.mercadolibre.android.andesui.thumbnail.AndesThumbnailBadge
import com.mercadolibre.android.andesui.thumbnail.badge.component.AndesThumbnailBadgeComponent
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

internal interface AndesFeedbackScreenAssetInterface {
    fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View
}

internal class AndesThumbnailFeedbackScreenAsset(
    private val image: Drawable,
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
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(
                    AssetsConstants.NO_MARGIN,
                    AssetsConstants.NO_MARGIN,
                    AssetsConstants.NO_MARGIN,
                    context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_thumbnail_margin_bottom))
            }
        }
    }
}

internal class AndesIllustrationFeedbackScreenAsset(
    private val image: Drawable,
    private val size: AndesFeedbackScreenIllustrationSize
) : AndesFeedbackScreenAssetInterface {

    override fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View {
        return ImageView(context).apply {
            val resources = context.resources
            layoutParams = FrameLayout.LayoutParams(
                resources.getDimensionPixelSize(size.width),
                resources.getDimensionPixelSize(size.height)
            ).apply {
                setMargins(
                    AssetsConstants.NO_MARGIN,
                    resolveIllustrationTopMargin(resources, hasBody),
                    AssetsConstants.NO_MARGIN,
                    resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_illustration_margin_bottom)
                )
            }
            setImageDrawable(image)
        }
    }

    private fun resolveIllustrationTopMargin(resources: Resources, hasBody: Boolean): Int =
        resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_illustration_margin_top).takeIf {
            hasBody
        } ?: AssetsConstants.NO_MARGIN
}

internal class AndesThumbnailTextFeedbackScreenAsset(
    private val text: String
) : AndesFeedbackScreenAssetInterface {
    override fun getView(context: Context, type: AndesFeedbackScreenTypeInterface, hasBody: Boolean): View {
        return AndesThumbnailBadge(
            context,
            AndesThumbnailBadgeComponent.IconPill(
                AndesBadgeIconType.HIGHLIGHT
            ),
            AndesThumbnailBadgeType.Text,
            text

        ).apply {
            badgeComponent = AndesThumbnailBadgeComponent.FeedbackIconPill(
                type.feedbackColor,
                type.getThumbnailBadgeSize(hasBody)
            )
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(
                    AssetsConstants.NO_MARGIN,
                    AssetsConstants.NO_MARGIN,
                    AssetsConstants.NO_MARGIN,
                    context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_thumbnail_margin_bottom))
            }
        }
    }
}

/**
 * object to store class constants.
 */
object AssetsConstants {
    /**
     * Declares a margin of 0dp.
     */
    const val NO_MARGIN = 0
}