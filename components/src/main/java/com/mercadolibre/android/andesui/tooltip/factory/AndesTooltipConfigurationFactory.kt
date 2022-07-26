package com.mercadolibre.android.andesui.tooltip.factory

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.hierarchy.BackgroundColorConfig
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipAction
import com.mercadolibre.android.andesui.tooltip.actions.AndesTooltipLinkAction
import com.mercadolibre.android.andesui.tooltip.factory.Constants.NO_A11Y_ACTION
import com.mercadolibre.android.andesui.tooltip.location.AndesTooltipLocation
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipSize
import com.mercadolibre.android.andesui.tooltip.style.AndesTooltipStyle
import com.mercadolibre.android.andesui.utils.getAccessibilityManager

internal typealias A11yAction = () -> Unit

internal data class AndesTooltipConfiguration(
    val backgroundColor: AndesColor,
    val textColor: AndesColor,
    val titleText: String? = null,
    val titleTypeface: Typeface?,
    val titleTextSize: Float?,
    val titleMaxWidth: Int,
    val bodyText: String,
    val bodyTypeface: Typeface?,
    val bodyTextSize: Float?,
    val bodyMaxWidth: Int,
    val isDismissible: Boolean,
    val dismissibleIcon: Drawable?,
    val primaryAction: AndesTooltipAction?,
    val primaryActionBackgroundColor: BackgroundColorConfig?,
    val primaryActionTextColor: AndesColor?,
    val secondaryAction: AndesTooltipAction?,
    val secondaryActionBackgroundColor: BackgroundColorConfig?,
    val secondaryActionTextColor: AndesColor?,
    val linkAction: AndesTooltipLinkAction?,
    val linkActionTypeface: Typeface?,
    val linkActionBackgroundColor: BackgroundColorConfig?,
    val linkActionTextColor: AndesColor?,
    val linkActionIsUnderlined: Boolean,
    val tooltipLocation: AndesTooltipLocation,
    val isDynamicWidth: Boolean,
    val isFocusableAndTouchable: Boolean,
    val triggerA11yAction: (View, A11yAction) -> Int
)

@Suppress("TooManyFunctions")
internal object AndesTooltipConfigurationFactory {

    fun create(context: Context, andesMessageAttrs: AndesTooltipAttrs): AndesTooltipConfiguration {
        val isA11yEnabled = context.getAccessibilityManager().isEnabled
        return with(andesMessageAttrs) {
            AndesTooltipConfiguration(
                backgroundColor = resolveBackgroundColor(style),
                    textColor = resolveTextColor(style),
                    titleText = title,
                    titleTypeface = resolveTitleTypeface(style, context),
                    titleTextSize = resolveTitleSize(context),
                    titleMaxWidth = resolveTitleMaxWidth(context, andesTooltipSize, isDismissible),
                    bodyText = body,
                    bodyTypeface = resolveBodyTypeface(style, context),
                    bodyTextSize = resolveBodySize(context),
                    bodyMaxWidth = resolveBodyMaxWidth(context, andesTooltipSize),
                    isDismissible = isDismissible,
                    dismissibleIcon = resolveDismissibleIcon(style, context),
                    primaryAction = mainAction,
                    primaryActionBackgroundColor = mainAction?.hierarchy?.let { resolvePrimaryActionBackgroundColor(style, it) },
                    primaryActionTextColor = mainAction?.hierarchy?.let { resolvePrimaryActionTextColor(style, it) },
                    secondaryAction = secondaryAction,
                    secondaryActionBackgroundColor = secondaryAction?.hierarchy?.let { resolveSecondaryActionBackgroundColor(style, it) },
                    secondaryActionTextColor = secondaryAction?.hierarchy?.let { resolveSecondaryActionTextColor(style, it) },
                    linkAction = linkAction,
                    linkActionTypeface = resolveLinkActionTypeface(style, context),
                    linkActionBackgroundColor = resolveLinkActionBackgroundColor(style),
                    linkActionTextColor = resolveLinkActionTextColor(style),
                    linkActionIsUnderlined = resolveBodyLinkIsUnderlined(style),
                    tooltipLocation = tooltipLocation,
                    isDynamicWidth = mainAction == null,
                    isFocusableAndTouchable = resolveIsFocusableAndTouchable(shouldGainA11yFocus, isA11yEnabled),
                    triggerA11yAction = resolveTriggerA11yAction(context, shouldGainA11yFocus)
            )
        }
    }

    private fun resolveTriggerA11yAction(context: Context, shouldGainA11yFocus: Boolean) = { trigger: View, action: A11yAction ->
        var actionId = NO_A11Y_ACTION
        if (!shouldGainA11yFocus) {
            actionId = ViewCompat.addAccessibilityAction(
                trigger,
                context.resources.getString(R.string.andes_tooltip_action_more_info)
            ) { _, _ ->
                action()
                true
            }
        }
        actionId
    }

    private fun resolveIsFocusableAndTouchable(shouldGainA11yFocus: Boolean, isA11yEnabled: Boolean): Boolean {
        if (!isA11yEnabled) return true
        return shouldGainA11yFocus
    }

    private fun resolveBackgroundColor(style: AndesTooltipStyle) = style.type.backgroundColor()
    private fun resolveTextColor(style: AndesTooltipStyle) = style.type.textColor()
    private fun resolveTitleSize(context: Context) = context.resources.getDimension(R.dimen.andes_message_title)
    private fun resolveTitleMaxWidth(context: Context, andesTooltipSize: AndesTooltipSize, dismissible: Boolean) =
        andesTooltipSize.type.titleMaxWidth(context, dismissible)
    private fun resolveBodySize(context: Context) = context.resources.getDimension(R.dimen.andes_message_body)
    private fun resolveTitleTypeface(style: AndesTooltipStyle, context: Context) = style.type.titleTypeface(context)
    private fun resolveBodyTypeface(style: AndesTooltipStyle, context: Context) = style.type.bodyTypeface(context)
    private fun resolveBodyMaxWidth(context: Context, andesTooltipSize: AndesTooltipSize) =
        andesTooltipSize.type.bodyContentMaxWidth(context)
    private fun resolveDismissibleIcon(style: AndesTooltipStyle, context: Context) = style.type.dismissibleIcon(context)

    private fun resolvePrimaryActionBackgroundColor(style: AndesTooltipStyle, buttonHierarchy: AndesButtonHierarchy) =
            style.type.primaryActionColorConfig(buttonHierarchy)

    private fun resolvePrimaryActionTextColor(style: AndesTooltipStyle, buttonHierarchy: AndesButtonHierarchy) =
            style.type.primaryActionTextColor(buttonHierarchy)

    private fun resolveSecondaryActionBackgroundColor(style: AndesTooltipStyle, buttonHierarchy: AndesButtonHierarchy) =
            style.type.secondaryActionColorConfig(buttonHierarchy)

    private fun resolveSecondaryActionTextColor(style: AndesTooltipStyle, buttonHierarchy: AndesButtonHierarchy) =
            style.type.secondaryActionTextColor(buttonHierarchy)

    private fun resolveLinkActionTypeface(style: AndesTooltipStyle, context: Context) =
            style.type.linkActionTypeface(context)

    private fun resolveLinkActionBackgroundColor(style: AndesTooltipStyle) =
            style.type.linkActionColorConfig()

    private fun resolveLinkActionTextColor(style: AndesTooltipStyle) =
            style.type.linkActionTextColor()

    private fun resolveBodyLinkIsUnderlined(style: AndesTooltipStyle) =
            style.type.isLinkUnderlined()
}
