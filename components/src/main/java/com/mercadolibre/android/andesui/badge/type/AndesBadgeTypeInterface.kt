package com.mercadolibre.android.andesui.badge.type

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.badge.size.AndesBadgePillSize
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.utils.buildColoredCircularShapeWithIconDrawable

/**
 * Defines all types related properties that an [AndesBadge] needs to be drawn properly.
 * Those properties change depending on the style of the badge.
 *
 */
internal interface AndesBadgeTypeInterface {

    /**
     * Returns a [AndesColor] that contains the primary variant color data for the badge.
     *
     * @return a [AndesColor] that contains the primary variant color data for the badge.
     */
    fun primaryVariantColor(): AndesColor

    /**
     * Returns a [AndesColor] that contains the primary color data for the badge.
     *
     * @return a [AndesColor] that contains the primary color data for the badge.
     */
    fun primaryColor(): AndesColor

    /**
     * Returns a [AndesColor] that contains the secondary color data for the badge.
     *
     * @return a [AndesColor] that contains the secondary color data for the badge.
     */
    fun secondaryColor(): AndesColor

    fun icon(context: Context, size: AndesBadgePillSize, backgroundColor: Int): Drawable? = null

    fun feedbackIcon(context: Context, defaultSize: Boolean): Drawable? = null
}

internal class AndesNeutralBadgeType : AndesBadgeTypeInterface {
    override fun primaryVariantColor() = R.color.andes_gray_550_solid.toAndesColor()
    override fun primaryColor() = R.color.andes_gray_550_solid.toAndesColor()
    override fun secondaryColor() = R.color.andes_gray_070_solid.toAndesColor()
}

internal class AndesHighlightBadgeType : AndesBadgeTypeInterface {
    override fun primaryVariantColor() = R.color.andes_accent_color_600.toAndesColor()
    override fun primaryColor() = R.color.andes_accent_color_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_accent_color_100.toAndesColor()
    override fun icon(context: Context, size: AndesBadgePillSize, backgroundColor: Int): Drawable {
        val iconPath = if (size == AndesBadgePillSize.SMALL) ICON_16 else ICON_24
        val icon = IconProvider(context).loadIcon(iconPath) as BitmapDrawable
        return buildColoredCircularShapeWithIconDrawable(
                icon,
                context,
                R.color.andes_white.toAndesColor(),
                backgroundColor,
                size.size.height(context).toInt()
        )
    }

    private companion object {
        const val ICON_16 = "andes_ui_feedback_info_16"
        const val ICON_24 = "andes_ui_feedback_info_24"
    }
}

internal class AndesSuccessBadgeType : AndesBadgeTypeInterface {
    override fun primaryVariantColor() = R.color.andes_green_600.toAndesColor()
    override fun primaryColor() = R.color.andes_green_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_green_100.toAndesColor()
    override fun icon(context: Context, size: AndesBadgePillSize, backgroundColor: Int): Drawable {
        val iconPath = if (size == AndesBadgePillSize.SMALL) ICON_16 else ICON_24
        val icon = IconProvider(context).loadIcon(iconPath) as BitmapDrawable
        return buildColoredCircularShapeWithIconDrawable(
                icon,
                context,
                R.color.andes_white.toAndesColor(),
                backgroundColor,
                size.size.height(context).toInt()
        )
    }

    override fun feedbackIcon(context: Context, defaultSize: Boolean): Drawable? {
        return IconProvider(context).loadIcon(
            R.drawable.andes_ui_feedback_success_40
                .takeIf { defaultSize } ?: R.drawable.andes_ui_feedback_success_32
        )
    }

    private companion object {
        const val ICON_16 = "andes_ui_feedback_success_16"
        const val ICON_24 = "andes_ui_feedback_success_24"
    }
}

internal class AndesWarningBadgeType : AndesBadgeTypeInterface {
    override fun primaryVariantColor() = R.color.andes_orange_600.toAndesColor()
    override fun primaryColor() = R.color.andes_orange_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_orange_100.toAndesColor()
    override fun icon(context: Context, size: AndesBadgePillSize, backgroundColor: Int): Drawable {
        val iconPath = if (size == AndesBadgePillSize.SMALL) ICON_16 else ICON_24
        val icon = IconProvider(context).loadIcon(iconPath) as BitmapDrawable
        return buildColoredCircularShapeWithIconDrawable(
                icon,
                context,
                R.color.andes_white.toAndesColor(),
                backgroundColor,
                size.size.height(context).toInt()
        )
    }

    override fun feedbackIcon(context: Context, defaultSize: Boolean): Drawable? {
        return IconProvider(context).loadIcon(
            R.drawable.andes_ui_feedback_warning_40
                .takeIf { defaultSize } ?: R.drawable.andes_ui_feedback_warning_32
        )
    }

    private companion object {
        const val ICON_16 = "andes_ui_feedback_warning_16"
        const val ICON_24 = "andes_ui_feedback_warning_24"
    }
}

internal class AndesErrorBadgeType : AndesBadgeTypeInterface {
    override fun primaryVariantColor() = R.color.andes_red_600.toAndesColor()
    override fun primaryColor() = R.color.andes_red_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_red_100.toAndesColor()
    override fun icon(context: Context, size: AndesBadgePillSize, backgroundColor: Int): Drawable {
        val iconPath = if (size == AndesBadgePillSize.SMALL) ICON_16 else ICON_24
        val icon = IconProvider(context).loadIcon(iconPath) as BitmapDrawable
        return buildColoredCircularShapeWithIconDrawable(
                icon,
                context,
                R.color.andes_white.toAndesColor(),
                backgroundColor,
                size.size.height(context).toInt()
        )
    }

    override fun feedbackIcon(context: Context, defaultSize: Boolean): Drawable? {
        return IconProvider(context).loadIcon(
            R.drawable.andes_ui_feedback_error_40
                .takeIf { defaultSize } ?: R.drawable.andes_ui_feedback_error_32
        )
    }

    private companion object {
        const val ICON_16 = "andes_ui_feedback_error_16"
        const val ICON_24 = "andes_ui_feedback_error_24"
    }
}
