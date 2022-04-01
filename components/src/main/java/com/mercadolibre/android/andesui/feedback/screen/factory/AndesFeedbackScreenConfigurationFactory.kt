package com.mercadolibre.android.andesui.feedback.screen.factory

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenActions
import com.mercadolibre.android.andesui.feedback.screen.actions.AndesFeedbackScreenButton
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenAsset
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenHeader
import com.mercadolibre.android.andesui.feedback.screen.header.AndesFeedbackScreenText
import com.mercadolibre.android.andesui.feedback.screen.header.view.AndesFeedbackScreenHeaderView
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenType
import com.mercadolibre.android.andesui.feedback.screen.type.AndesFeedbackScreenTypeInterface
import com.mercadolibre.android.andesui.thumbnail.badge.type.AndesThumbnailBadgeType

internal data class AndesFeedbackScreenConfiguration(
    val body: AndesFeedbackBodyConfiguration,
    @ColorInt val background: Int,
    val headerVerticalBias: Float,
    val close: AndesFeedbackCloseConfiguration,
    val feedbackButton: AndesFeedbackButtonConfiguration,
    val feedbackText: AndesFeedbackScreenText,
    val typeInterface: AndesFeedbackScreenTypeInterface,
    val headerView: View,
    val gradientVisibility: Int,
    val headerTopMargin: Int,
    val statusBarColor: Int?,
    val actions: AndesFeedbackScreenActions?,
    val type: AndesFeedbackScreenType,
    val header: AndesFeedbackScreenHeader,
    val buttonGroup: AndesFeedbackButtonGroupConfiguration
)

internal data class AndesFeedbackCloseConfiguration(
    val visibility: Int,
    val onClick: View.OnClickListener?,
    @ColorInt val tintColor: Int
)

internal data class AndesFeedbackBodyConfiguration(
    val view: View?,
    val visibility: Int,
    val layoutParams: ViewGroup.LayoutParams
)

internal data class AndesFeedbackButtonConfiguration(
    val text: String?,
    val visibility: Int,
    val onClick: View.OnClickListener?,
    val hierarchy: AndesButtonHierarchy
)

internal data class AndesFeedbackButtonGroupConfiguration(
    val andesButtonGroup: AndesButtonGroup?,
    val visibility: Int
)

internal object AndesFeedbackScreenConfigurationFactory {

    private const val TOP_BIAS = 0f
    private const val MIDDLE_BIAS = 0.5f

    fun create(
        context: Context,
        body: View?,
        actions: AndesFeedbackScreenActions?,
        type: AndesFeedbackScreenType,
        header: AndesFeedbackScreenHeader
    ): AndesFeedbackScreenConfiguration {
        val hasBody = body != null
        val feedbackType = type.type
        return AndesFeedbackScreenConfiguration(
            body = resolveBodyConfiguration(body, hasBody),
            background = resolveBackground(context, hasBody, feedbackType),
            headerVerticalBias = resolveHeaderVerticalBias(hasBody),
            close = resolveCloseConfiguration(
                context,
                feedbackType,
                actions?.closeCallback,
                hasBody
            ),
            feedbackButton = resolveFeedbackButtonConfig(feedbackType, actions?.button),
            typeInterface = feedbackType,
            feedbackText = header.feedbackText,
            headerView = resolveHeader(context, feedbackType, hasBody, header),
            gradientVisibility = feedbackType.getGradientVisibility(hasBody),
            headerTopMargin = resolveHeaderTopMargin(context, feedbackType, hasBody),
            statusBarColor = resolveStatusBarColor(context, feedbackType),
            actions = actions,
            type = type,
            header = header,
            buttonGroup = resolveFeedbackButtonGroup(actions?.buttonGroup)
        )
    }

    private fun resolveHeaderTopMargin(
        context: Context,
        feedbackType: AndesFeedbackScreenTypeInterface,
        hasBody: Boolean
    ): Int {
        return feedbackType.getHeaderTopMargin(context, hasBody)
    }

    private fun resolveStatusBarColor(
        context: Context,
        feedbackType: AndesFeedbackScreenTypeInterface
    ): Int? {
        return feedbackType.getStatusBarColor(context)
    }

    private fun resolveHeader(
        context: Context,
        feedbackType: AndesFeedbackScreenTypeInterface,
        hasBody: Boolean,
        andesFeedbackScreenHeader: AndesFeedbackScreenHeader
    ): View {
        return feedbackType.getHeaderView(context, hasBody).apply {
            (this as? AndesFeedbackScreenHeaderView)?.let {
                val color = feedbackType.feedbackColor
                it.setupTextComponents(andesFeedbackScreenHeader.feedbackText, color, hasBody)
                it.setupAssetComponent(
                    resolveFeedbackThumbnail(
                        context,
                        andesFeedbackScreenHeader.feedbackImage,
                        feedbackType,
                        hasBody
                    ), feedbackType,
                    hasBody
                )
            }
        }
    }

    internal fun resolveFeedbackThumbnail(
        context: Context,
        feedbackImage: AndesFeedbackScreenAsset?,
        feedbackType: AndesFeedbackScreenTypeInterface,
        hasBody: Boolean
    ) = feedbackImage ?: AndesFeedbackScreenAsset.Thumbnail(
        image = feedbackType.getDefaultAsset(context, hasBody)!!,
        badgeType = AndesThumbnailBadgeType.FeedbackIcon
    )

    private fun resolveFeedbackButtonConfig(
        type: AndesFeedbackScreenTypeInterface,
        feedbackButton: AndesFeedbackScreenButton?
    ) =
        AndesFeedbackButtonConfiguration(
            text = feedbackButton?.text,
            hierarchy = type.getButtonHierarchy(),
            visibility = View.VISIBLE.takeIf { feedbackButton != null } ?: View.GONE,
            onClick = feedbackButton?.onClick
        )

    private fun resolveFeedbackButtonGroup(feedbackButtonGroup: AndesButtonGroup?) =
        AndesFeedbackButtonGroupConfiguration(
            feedbackButtonGroup,
            View.VISIBLE.takeIf { feedbackButtonGroup != null } ?: View.GONE
        )

    private fun resolveBodyConfiguration(body: View?, hasBody: Boolean) =
        AndesFeedbackBodyConfiguration(
            view = body,
            visibility = View.VISIBLE.takeIf { hasBody } ?: View.GONE,
            layoutParams = ViewGroup.LayoutParams(
                body?.layoutParams?.width?.takeIf { it != WRAP_CONTENT } ?: MATCH_PARENT,
                body?.layoutParams?.height ?: WRAP_CONTENT
            )
        )

    private fun resolveCloseConfiguration(
        context: Context,
        type: AndesFeedbackScreenTypeInterface,
        feedbackOnClose: View.OnClickListener?,
        hasBody: Boolean
    ) = AndesFeedbackCloseConfiguration(
        visibility = View.VISIBLE.takeIf { feedbackOnClose != null } ?: View.GONE,
        onClick = feedbackOnClose,
        tintColor = type.getCloseTintColor(context, hasBody)
    )

    private fun resolveHeaderVerticalBias(hasBody: Boolean) =
        TOP_BIAS.takeIf { hasBody } ?: MIDDLE_BIAS

    private fun resolveBackground(
        context: Context,
        hasBody: Boolean,
        feedbackType: AndesFeedbackScreenTypeInterface
    ) = ContextCompat.getColor(
        context,
        feedbackType.getBackgroundColor(hasBody)
    )
}
