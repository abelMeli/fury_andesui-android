package com.mercadolibre.android.andesui.textfield.content

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.button.size.AndesButtonSize
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.color.toColor
import com.mercadolibre.android.andesui.icons.IconProvider
import com.mercadolibre.android.andesui.progress.AndesProgressIndicatorIndeterminate
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldStateInterface
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.buildColoredAndesBitmapDrawable

/**
 * Defines all style related properties that an [AndesTextfield] needs to be drawn properly.
 * Those properties change depending on the style of the message.
 *
 */
internal sealed class AndesTextfieldContentInterface {
    abstract fun component(context: Context): View
    abstract fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int
    abstract fun rightMargin(context: Context): Int
    abstract fun topMargin(context: Context): Int
    abstract fun verticalBias(context: Context): Float
    open fun setContentDescription(context: Context, description: String?) = Unit
}

internal object AndesSuffixTextfieldContent : AndesTextfieldContentInterface() {
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int {
        return context.resources.getDimension(R.dimen.andes_textfield_suffix_left_margin).toInt()
    }

    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_suffix_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS

    override fun component(context: Context): TextView {
        val suffix = TextView(context)
        suffix.setTextColor(R.color.andes_text_color_secondary.toColor(context))
        suffix.text = context.getString(R.string.andes_suffix_hint)
        suffix.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        suffix.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.andes_textfield_edittext_textSize))
        return suffix
    }
}

internal object AndesPrefixTextfieldContent : AndesTextfieldContentInterface() {
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int = state.leftMargin(context)

    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_prefix_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context) : Float = Const.MIDDLE_BIAS
    override fun component(context: Context): TextView {
        val prefix = TextView(context)
        prefix.setTextColor(R.color.andes_text_color_secondary.toColor(context))
        prefix.text = context.getString(R.string.andes_prefix_hint)
        prefix.typeface = context.getFontOrDefault(R.font.andes_font_regular)
        prefix.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.andes_textfield_edittext_textSize))
        return prefix
    }
}

internal object AndesIconTextfieldContent : AndesTextfieldContentInterface() {
    private const val ICON_CONTENT_DESCRIPTION = "icon"

    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int = state.leftMargin(context)
    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_icon_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context):Float = Const.MIDDLE_BIAS
    override fun component(context: Context): SimpleDraweeView {
        val icon = SimpleDraweeView(context)
        icon.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_placeholder_imagen_24") as BitmapDrawable,
                context,
                color = R.color.andes_gray_900.toAndesColor()))
        icon.contentDescription = ICON_CONTENT_DESCRIPTION
        return icon
    }
}

internal object AndesTooltipTextfieldContent : AndesTextfieldContentInterface() {
    private var contentDescription: String? = null
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int = state.leftMargin(context)
    override fun rightMargin(context: Context): Int =
        context.resources.getDimension(R.dimen.andes_textfield_tooltip_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS
    override fun component(context: Context): View {
        val tooltip = SimpleDraweeView(context)
        tooltip.setImageDrawable(
            buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_helper_24") as BitmapDrawable,
                context,
                color = R.color.andes_accent_color.toAndesColor()
            )
        )
        tooltip.contentDescription = contentDescription
        return tooltip
    }

    override fun setContentDescription(context: Context, description: String?) {
        contentDescription = description ?: run {
            context.getString(R.string.andes_textfield_tooltip_content_description)
        }
    }
}

internal object AndesValidatedTextfieldContent : AndesTextfieldContentInterface() {
    private const val VALIDATED_CONTENT_DESCRIPTION = "validated"

    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int = state.leftMargin(context)
    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_validated_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS
    override fun component(context: Context): SimpleDraweeView {
        val validated = SimpleDraweeView(context)
        validated.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_feedback_success_24") as BitmapDrawable,
                context,
                color = R.color.andes_green_500.toAndesColor())
        )
        validated.contentDescription = VALIDATED_CONTENT_DESCRIPTION
        return validated
    }
}

internal object AndesClearTextfieldContent : AndesTextfieldContentInterface() {

    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int {
        return context.resources.getDimension(R.dimen.andes_textfield_clear_left_margin).toInt()
    }

    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_clear_right_margin).toInt()
    override fun topMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_clear_top_margin).toInt()
    override fun verticalBias(context: Context): Float= Const.TOP_BIAS
    override fun component(context: Context): SimpleDraweeView {
        val clear = SimpleDraweeView(context)
        clear.setImageDrawable(buildColoredAndesBitmapDrawable(
                IconProvider(context).loadIcon("andes_ui_close_24") as BitmapDrawable,
                context,
                color = R.color.andes_gray_550.toAndesColor())
        )
        clear.contentDescription = context.getString(R.string.andes_textfield_right_content_clear)
        return clear
    }
}

internal object AndesActionTextfieldContent : AndesTextfieldContentInterface() {
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int {
        return context.resources.getDimension(R.dimen.andes_textfield_action_left_margin).toInt()
    }

    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_action_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS
    override fun component(context: Context): AndesButton {
        return AndesButton(context, AndesButtonSize.MEDIUM, AndesButtonHierarchy.TRANSPARENT)
    }
}

internal object AndesIndeterminateTextfieldContent : AndesTextfieldContentInterface() {
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int = state.leftMargin(context)
    override fun rightMargin(context: Context): Int = context.resources.getDimension(R.dimen.andes_textfield_indeterminate_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS
    override fun component(context: Context): View {
        return AndesProgressIndicatorIndeterminate(context)
    }
}

internal object AndesCheckboxTextfieldContent : AndesTextfieldContentInterface() {
    override fun leftMargin(context: Context, state: AndesTextfieldStateInterface): Int =
            context.resources.getDimension(R.dimen.andes_textfield_checkbox_left_margin).toInt()
    override fun rightMargin(context: Context): Int =
            context.resources.getDimension(R.dimen.andes_textfield_checkbox_right_margin).toInt()
    override fun topMargin(context: Context): Int = Const.NO_MARGIN
    override fun verticalBias(context: Context): Float= Const.MIDDLE_BIAS
    override fun component(context: Context): AndesCheckbox {
        return AndesCheckbox(context, "", AndesCheckboxAlign.RIGHT)
    }
}
internal object Const{
    const val NO_MARGIN = 0
    const val TOP_BIAS = 0.0f
    const val MIDDLE_BIAS = 0.5f
}


