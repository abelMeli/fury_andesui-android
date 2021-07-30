package com.mercadolibre.android.andesui.textfield.state

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.textfield.links.AndesTextfieldLinks
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.buildColoredCircularShapeWithIconDrawable

/**
 * Defines all style related properties that an [AndesTextfield] needs to be drawn properly.
 * Those properties change depending on the style of the message.
 *
 */
internal sealed class AndesTextfieldStateInterface {
    abstract fun backgroundColor(context: Context): Drawable
    abstract fun icon(context: Context): Drawable?
    abstract fun placeholderColor(): AndesColor
    abstract fun labelColor(): AndesColor
    abstract fun helpersColor(): AndesColor
    abstract fun typeFace(context: Context): Typeface
    abstract fun helper(helper: String?): String?
    abstract fun helperLinks(helperLinks: AndesTextfieldLinks?): AndesTextfieldLinks?
    abstract fun leftMargin(context: Context): Int
}

internal object AndesIdleTextfieldState : AndesTextfieldStateInterface() {
    override fun labelColor(): AndesColor = R.color.andes_text_color_primary.toAndesColor()
    override fun placeholderColor(): AndesColor = R.color.andes_gray_200.toAndesColor()
    override fun helpersColor(): AndesColor = R.color.andes_text_color_secondary.toAndesColor()
    override fun typeFace(context: Context): Typeface = context.getFontOrDefault(R.font.andes_font_regular)
    override fun helper(helper: String?): String? = helper
    override fun helperLinks(helperLinks: AndesTextfieldLinks?): AndesTextfieldLinks? = helperLinks
    override fun leftMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_indeterminate_left_margin).toInt()

    override fun backgroundColor(context: Context): Drawable {
        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_focused), createGradientDrawable(
                context,
                context.resources.getDimension(R.dimen.andes_textfield_focused_stroke).toInt(),
                R.color.andes_accent_color_500.toColor(context),
                R.color.andes_bg_color_white.toColor(context)
            ))
            addState(
                intArrayOf(android.R.attr.state_enabled),
                createGradientDrawable(
                    context,
                    context.resources.getDimension(R.dimen.andes_textfield_simple_stroke).toInt(),
                    R.color.andes_gray_200.toColor(context),
                    R.color.andes_bg_color_white.toColor(context)
                )
            )
            addState(
                intArrayOf(-android.R.attr.state_enabled),
                createGradientDrawableWithDash(
                    context,
                    context.resources.getDimension(R.dimen.andes_textfield_simple_stroke).toInt(),
                    R.color.andes_gray_200.toColor(context),
                    context.resources.getDimension(R.dimen.andes_textfield_dash)
                )
            )
        }
    }

    override fun icon(context: Context): Drawable? = null
}

internal object AndesErrorTextfieldState : AndesTextfieldStateInterface() {
    override fun labelColor(): AndesColor = R.color.andes_red_500.toAndesColor()
    override fun placeholderColor(): AndesColor = R.color.andes_gray_200.toAndesColor()
    override fun helpersColor(): AndesColor = R.color.andes_red_500.toAndesColor()
    override fun typeFace(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)
    override fun helper(helper: String?): String? = helper
    override fun helperLinks(helperLinks: AndesTextfieldLinks?): AndesTextfieldLinks? = helperLinks
    override fun leftMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_indeterminate_left_margin).toInt()

    override fun backgroundColor(context: Context): Drawable {
        return StateListDrawable().apply {
            addState(
                intArrayOf(android.R.attr.state_focused),
                createGradientDrawable(
                    context,
                    context.resources.getDimension(R.dimen.andes_textfield_focused_stroke).toInt(),
                    R.color.andes_red_500.toColor(context),
                    R.color.andes_bg_color_white.toColor(context)
                )
            )
            addState(
                intArrayOf(android.R.attr.state_enabled),
                createGradientDrawable(
                    context,
                    context.resources.getDimension(R.dimen.andes_textfield_simple_stroke).toInt(),
                    R.color.andes_red_500.toColor(context),
                    R.color.andes_bg_color_white.toColor(context)
                )
            )
        }
    }

    override fun icon(context: Context): Drawable? {
        return buildColoredCircularShapeWithIconDrawable(
                IconProvider(context).loadIcon("andes_ui_feedback_warning_16") as BitmapDrawable,
                context,
                R.color.andes_white.toAndesColor(),
                R.color.andes_red_500.toColor(context),
                context.resources.getDimension(R.dimen.andes_textfield_icon_diameter).toInt())
    }
}

internal object AndesDisabledTextfieldState : AndesTextfieldStateInterface() {
    override fun labelColor(): AndesColor = R.color.andes_gray_250.toAndesColor()
    override fun placeholderColor(): AndesColor = R.color.andes_gray_250.toAndesColor()
    override fun helpersColor(): AndesColor = R.color.andes_gray_250.toAndesColor()
    override fun typeFace(context: Context): Typeface = context.getFontOrDefault(R.font.andes_font_regular)
    override fun helper(helper: String?): String? = helper
    override fun helperLinks(helperLinks: AndesTextfieldLinks?): AndesTextfieldLinks? = null
    override fun leftMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_indeterminate_left_margin).toInt()

    override fun backgroundColor(context: Context): Drawable {
        return createGradientDrawableWithDash(
            context,
            context.resources.getDimension(R.dimen.andes_textfield_simple_stroke).toInt(),
            R.color.andes_gray_200.toColor(context),
            context.resources.getDimension(R.dimen.andes_textfield_dash)
        )
    }

    override fun icon(context: Context): Drawable? = null
}

internal object AndesReadonlyTextfieldState : AndesTextfieldStateInterface() {
    override fun labelColor(): AndesColor = R.color.andes_text_color_secondary.toAndesColor()
    override fun placeholderColor(): AndesColor = R.color.andes_text_color_primary.toAndesColor()
    override fun helpersColor(): AndesColor = R.color.andes_gray_250.toAndesColor()
    override fun typeFace(context: Context): Typeface = context.getFontOrDefault(R.font.andes_font_regular)
    override fun helper(helper: String?): String? = null
    override fun helperLinks(helperLinks: AndesTextfieldLinks?): AndesTextfieldLinks? = null
    override fun leftMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_label_paddingLeft).toInt()

    override fun backgroundColor(context: Context): Drawable {
        return createGradientDrawable(context, 0, 0, R.color.andes_transparent.toColor(context))
    }

    override fun icon(context: Context): Drawable? = null
}

private fun createGradientDrawable(context: Context, stroke: Int, strokeColor: Int, backgrondColor: Int?): Drawable {
    val drawable = GradientDrawable()
    drawable.cornerRadius = context.resources.getDimension(R.dimen.andes_button_border_radius_large)
    drawable.setStroke(stroke, strokeColor)
    if (backgrondColor != null) {
        drawable.setColor(backgrondColor)
    }
    return drawable
}

private fun createGradientDrawableWithDash(context: Context, stroke: Int, color: Int, dash: Float): Drawable {
    val drawable = GradientDrawable()
    drawable.cornerRadius = context.resources.getDimension(R.dimen.andes_button_border_radius_large)
    drawable.setStroke(stroke, R.color.andes_gray_250.toColor(context), dash, dash)
    drawable.setColor(R.color.andes_gray_040.toColor(context))
    return drawable
}
