package com.mercadolibre.android.andesui.message.type

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.BackgroundColorConfig
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.message.hierarchy.AndesMessageHierarchyInterface
import com.mercadolibre.android.andesui.utils.buildColoredCircularShapeWithIconDrawable

internal sealed class AndesMessageTypeInterface {

    abstract fun primaryColor(): AndesColor

    abstract fun secondaryColor(): AndesColor

    fun pipeColor(): AndesColor = primaryColor()

    abstract fun icon(context: Context, hierarchy: AndesMessageHierarchyInterface): Drawable?

    abstract fun primaryActionColorConfig(): BackgroundColorConfig

    abstract fun secondaryActionColorConfig(): BackgroundColorConfig

    abstract fun linkActionColorConfig(): BackgroundColorConfig
}

internal object AndesNeutralMessageType : AndesMessageTypeInterface() {
    override fun primaryColor() = R.color.andes_accent_color_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_accent_color_600.toAndesColor()
    override fun icon(context: Context, hierarchy: AndesMessageHierarchyInterface) = buildColoredCircularShapeWithIconDrawable(
            IconProvider(context).loadIcon("andes_ui_feedback_info_16") as BitmapDrawable,
            context,
            R.color.andes_white.toAndesColor(),
            hierarchy.iconBackgroundColor(this).colorInt(context),
            context.resources.getDimension(R.dimen.andes_message_icon_diameter).toInt()
    )

    override fun primaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_600.toAndesColor(),
                pressedColor = R.color.andes_accent_color_800.toAndesColor(),
                focusedColor = R.color.andes_accent_color_400.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun secondaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_500.toAndesColor(),
                pressedColor = R.color.andes_accent_color_700.toAndesColor(),
                focusedColor = R.color.andes_accent_color_300.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun linkActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null)
    }
}

internal object AndesSuccessMessageType : AndesMessageTypeInterface() {
    override fun primaryColor() = R.color.andes_green_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_green_600.toAndesColor()
    override fun icon(context: Context, hierarchy: AndesMessageHierarchyInterface) = buildColoredCircularShapeWithIconDrawable(
            IconProvider(context).loadIcon("andes_ui_feedback_success_16") as BitmapDrawable,
            context,
            R.color.andes_white.toAndesColor(),
            hierarchy.iconBackgroundColor(this).colorInt(context),
            context.resources.getDimension(R.dimen.andes_message_icon_diameter).toInt()
    )

    override fun primaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_green_600.toAndesColor(),
                pressedColor = R.color.andes_green_800.toAndesColor(),
                focusedColor = R.color.andes_green_400.toAndesColor(),
                hoveredColor = R.color.andes_green_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun secondaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_green_500.toAndesColor(),
                pressedColor = R.color.andes_green_700.toAndesColor(),
                focusedColor = R.color.andes_green_300.toAndesColor(),
                hoveredColor = R.color.andes_green_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun linkActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null)
    }
}

internal object AndesWarningMessageType : AndesMessageTypeInterface() {
    override fun primaryColor() = R.color.andes_orange_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_orange_600.toAndesColor()
    override fun icon(context: Context, hierarchy: AndesMessageHierarchyInterface) = buildColoredCircularShapeWithIconDrawable(
            IconProvider(context).loadIcon("andes_ui_feedback_warning_16") as BitmapDrawable,
            context,
            R.color.andes_white.toAndesColor(),
            hierarchy.iconBackgroundColor(this).colorInt(context),
            context.resources.getDimension(R.dimen.andes_message_icon_diameter).toInt()
    )

    override fun primaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_orange_600.toAndesColor(),
                pressedColor = R.color.andes_orange_800.toAndesColor(),
                focusedColor = R.color.andes_orange_400.toAndesColor(),
                hoveredColor = R.color.andes_orange_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun secondaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_orange_500.toAndesColor(),
                pressedColor = R.color.andes_orange_700.toAndesColor(),
                focusedColor = R.color.andes_orange_300.toAndesColor(),
                hoveredColor = R.color.andes_orange_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun linkActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null)
    }
}

internal object AndesErrorMessageType : AndesMessageTypeInterface() {
    override fun primaryColor() = R.color.andes_red_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_red_600.toAndesColor()
    override fun icon(context: Context, hierarchy: AndesMessageHierarchyInterface) = buildColoredCircularShapeWithIconDrawable(
            IconProvider(context).loadIcon("andes_ui_feedback_warning_16") as BitmapDrawable,
            context,
            R.color.andes_white.toAndesColor(),
            hierarchy.iconBackgroundColor(this).colorInt(context),
            context.resources.getDimension(R.dimen.andes_message_icon_diameter).toInt()
    )

    override fun primaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_red_600.toAndesColor(),
                pressedColor = R.color.andes_red_800.toAndesColor(),
                focusedColor = R.color.andes_red_400.toAndesColor(),
                hoveredColor = R.color.andes_red_700.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun secondaryActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_red_500.toAndesColor(),
                pressedColor = R.color.andes_red_700.toAndesColor(),
                focusedColor = R.color.andes_red_300.toAndesColor(),
                hoveredColor = R.color.andes_red_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = null)
    }

    override fun linkActionColorConfig(): BackgroundColorConfig {
        return BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_transparent.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_transparent.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null)
    }
}
