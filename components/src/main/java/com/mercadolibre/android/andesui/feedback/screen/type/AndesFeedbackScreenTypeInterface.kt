package com.mercadolibre.android.andesui.feedback.screen.type

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.feedback.screen.color.AndesFeedbackScreenColor
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenCongratsHeaderView
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenSimpleHeaderView
import com.mercadolibre.android.andesui.thumbnail.badge.size.AndesThumbnailBadgePillSize

internal interface AndesFeedbackScreenTypeInterface {
    val feedbackColor: AndesFeedbackBadgeIconType

    fun getThumbnailBadgeSize(hasBody: Boolean): AndesThumbnailBadgePillSize

    fun getHeaderView(context: Context, hasBody: Boolean): View

    fun getGradientVisibility(hasBody: Boolean): Int

    fun getCloseTintColor(context: Context, hasBody: Boolean): Int

    fun getHeaderTopMargin(context: Context, hasBody: Boolean): Int

    fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable?

    fun getStatusBarColor(context: Context): Int? = null

    fun getButtonHierarchy() = AndesButtonHierarchy.LOUD

    fun getBackgroundColor(hasBody: Boolean) =
        R.color.andes_bg_color_secondary.takeIf { hasBody } ?: R.color.andes_bg_color_white
}

internal class AndesSimpleFeedbackScreenType(override val feedbackColor: AndesFeedbackBadgeIconType) :
    AndesFeedbackScreenTypeInterface {

    override fun getThumbnailBadgeSize(hasBody: Boolean) = AndesThumbnailBadgePillSize.SIZE_64

    override fun getHeaderView(context: Context, hasBody: Boolean) =
        AndesFeedbackScreenSimpleHeaderView(context)

    override fun getGradientVisibility(hasBody: Boolean) = View.GONE

    override fun getCloseTintColor(context: Context, hasBody: Boolean) =
        ContextCompat.getColor(context, R.color.andes_gray_550)

    override fun getHeaderTopMargin(context: Context, hasBody: Boolean) =
        context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)

    override fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable? {
        return feedbackColor.type.type.feedbackIcon(context, true)
    }
}

internal object AndesCongratsFeedbackScreenType :
    AndesFeedbackScreenTypeInterface {
    override val feedbackColor = AndesFeedbackScreenColor.GREEN.color

    override fun getThumbnailBadgeSize(hasBody: Boolean): AndesThumbnailBadgePillSize {
        return if (hasBody)
            AndesThumbnailBadgePillSize.SIZE_56
        else
            AndesThumbnailBadgePillSize.SIZE_64
    }

    override fun getHeaderView(context: Context, hasBody: Boolean): View {
        return AndesFeedbackScreenCongratsHeaderView(context).takeIf { hasBody }
            ?: AndesFeedbackScreenSimpleHeaderView(context)
    }

    override fun getGradientVisibility(hasBody: Boolean) =
        View.VISIBLE.takeIf { hasBody } ?: View.GONE

    override fun getCloseTintColor(context: Context, hasBody: Boolean): Int {
        return if (hasBody) {
            ContextCompat.getColor(context, R.color.andes_white)
        } else {
            ContextCompat.getColor(context, R.color.andes_gray_550)
        }
    }

    override fun getHeaderTopMargin(context: Context, hasBody: Boolean): Int {
        return if (hasBody) {
            context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_congrats_header_margin_top)
        } else {
            context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)
        }
    }

    override fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable? {
        return feedbackColor.type.type.feedbackIcon(context, !hasBody)
    }

    override fun getStatusBarColor(context: Context) =
        ContextCompat.getColor(context, R.color.andes_green_500)
}

internal class AndesErrorFeedbackScreenType(
    private val feedbackErrorCode: String?
) :
    AndesFeedbackScreenTypeInterface {
    override val feedbackColor = AndesFeedbackBadgeIconType.NEUTRAL

    override fun getThumbnailBadgeSize(hasBody: Boolean) = AndesThumbnailBadgePillSize.SIZE_64

    override fun getHeaderView(context: Context, hasBody: Boolean) =
        AndesFeedbackScreenSimpleHeaderView(context).apply {
            setErrorCodeConfiguration(feedbackErrorCode)
        }

    override fun getBackgroundColor(hasBody: Boolean): Int {
        return R.color.andes_bg_color_secondary
    }

    override fun getGradientVisibility(hasBody: Boolean) = View.GONE

    override fun getCloseTintColor(context: Context, hasBody: Boolean) =
        ContextCompat.getColor(context, R.color.andes_gray_550)

    override fun getHeaderTopMargin(context: Context, hasBody: Boolean) =
        context.resources.getDimensionPixelSize(R.dimen.andes_feedbackscreen_simple_header_margin_top)

    override fun getDefaultAsset(context: Context, hasBody: Boolean): Drawable? {
        return feedbackColor.type.type.feedbackIcon(context, true)
    }

    override fun getButtonHierarchy(): AndesButtonHierarchy {
        return AndesButtonHierarchy.TRANSPARENT
    }
}
