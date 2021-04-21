package com.mercadolibre.android.andesui.button.hierarchy

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

/**
 * Useful class that holds all the possible colors for the [AndesButton] background.
 *
 * @property enabledColor
 * @property pressedColor
 * @property focusedColor
 * @property hoveredColor
 * @property disabledColor
 * @property otherColor
 */
internal data class BackgroundColorConfig(
    val enabledColor: AndesColor,
    val pressedColor: AndesColor,
    val focusedColor: AndesColor,
    val hoveredColor: AndesColor,
    val disabledColor: AndesColor,
    val otherColor: AndesColor?
)

/**
 * Returns a [Drawable] ready to be used as the background of an [AndesButton]. Output is based on the received [colorConfig].
 *
 * @param context needed for accessing some resources.
 * @param cornerRadius radius of the button corner. This is needed because it depends on the button size.
 * @param colorConfig config that contains the specification of each required color.
 * @return
 */
internal fun getConfiguredBackground(context: Context, cornerRadius: Float, colorConfig: BackgroundColorConfig): Drawable {
    val buttonShape = RoundRectShape(getOuterRadii(cornerRadius), null, null)
    return StateListDrawable().apply {
        addState(intArrayOf(android.R.attr.state_pressed), createShapeDrawable(context, buttonShape, colorConfig.pressedColor))
        addState(intArrayOf(android.R.attr.state_enabled), createShapeDrawable(context, buttonShape, colorConfig.enabledColor))
        addState(intArrayOf(-android.R.attr.state_enabled), createShapeDrawable(context, buttonShape, colorConfig.disabledColor))
        addState(intArrayOf(android.R.attr.state_hovered), createShapeDrawable(context, buttonShape, colorConfig.hoveredColor))
        addState(intArrayOf(android.R.attr.state_focused), createShapeDrawable(context, buttonShape, colorConfig.focusedColor))
    }
}

private fun getOuterRadii(cornerRadius: Float) =
        floatArrayOf(cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius)

private fun createShapeDrawable(context: Context, shape: Shape, color: AndesColor) =
        ShapeDrawable(shape).apply { paint.color = color.colorInt(context) }

/**
 * Returns the proper [BackgroundColorConfig] for the Loud Hierarchy button.
 *
 */
internal fun createBackgroundColorConfigLoud() =
        BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_500.toAndesColor(),
                pressedColor = R.color.andes_accent_color_700.toAndesColor(),
                focusedColor = R.color.andes_accent_color_300.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = R.color.andes_green_500.toAndesColor())

/**
 * Returns the proper [BackgroundColorConfig] for the Highlight Hierarchy button.
 *
 */
internal fun createBackgroundColorConfigTooltipHighlight() =
        BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_700.toAndesColor(),
                pressedColor = R.color.andes_accent_color_700.toAndesColor(),
                focusedColor = R.color.andes_accent_color_300.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_600.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = R.color.andes_green_500.toAndesColor())

/**
 * Returns the proper [BackgroundColorConfig] for the Quiet Hierarchy button.
 *
 */
internal fun createBackgroundColorConfigQuiet() =
        BackgroundColorConfig(
                enabledColor = R.color.andes_accent_color_150.toAndesColor(),
                pressedColor = R.color.andes_accent_color_300.toAndesColor(),
                focusedColor = R.color.andes_accent_color_150.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_200.toAndesColor(),
                disabledColor = R.color.andes_gray_100.toAndesColor(),
                otherColor = R.color.andes_gray_800.toAndesColor())

/**
 * Returns the proper [BackgroundColorConfig] for the Transparent Hierarchy button.
 *
 */
internal fun createBackgroundColorConfigTransparent() =
        BackgroundColorConfig(
                enabledColor = R.color.andes_transparent.toAndesColor(),
                pressedColor = R.color.andes_accent_color_200.toAndesColor(),
                focusedColor = R.color.andes_transparent.toAndesColor(),
                hoveredColor = R.color.andes_accent_color_100.toAndesColor(),
                disabledColor = R.color.andes_transparent.toAndesColor(),
                otherColor = null)
