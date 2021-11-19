package com.mercadolibre.android.andesui.textview.style

import android.content.Context
import android.graphics.Typeface
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal interface AndesTextViewStyleInterface {
    val weightValue: Int

    fun getTextSize(context: Context): Float
    fun getFont(context: Context): Typeface
    fun getLineHeight(context: Context): Int
}

internal object AndesTextViewStyleTitleXl : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_title_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_title_font_size_xl)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xl)
}

internal object AndesTextViewStyleTitleL : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_title_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_title_font_size_l)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_l)
}

internal object AndesTextViewStyleTitleM : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_title_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_title_font_size_m)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_m)
}

internal object AndesTextViewStyleTitleS : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_title_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_title_font_size_s)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_s)
}

internal object AndesTextViewStyleTitleXs : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_title_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_title_font_size_xs)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_semibold)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_title_line_height_xs)
}

internal object AndesTextViewStyleBodyL : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_body_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_body_font_size_l)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_regular)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_l)
}

internal object AndesTextViewStyleBodyM : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_body_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_body_font_size_m)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_regular)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_m)
}

internal object AndesTextViewStyleBodyS : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_body_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_body_font_size_s)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_regular)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_s)
}

internal object AndesTextViewStyleBodyXs : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_body_font_weight

    override fun getTextSize(context: Context) = context.resources.getDimension(R.dimen.andes_textview_body_font_size_xs)

    override fun getFont(context: Context) = context.getFontOrDefault(R.font.andes_font_regular)

    override fun getLineHeight(context: Context) = context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_xs)
}

/**
 * Takes custom values into a new style.
 *
 * If any of the parameters corresponds to the "NO_SET" values, it will use the [AndesTextViewStyleBodyS]
 * values.
 */
internal data class AndesTextViewStyleCustom(
    private val customFontValue: Int,
    private val customLineHeight: Float,
    private val customTextSize: Float
) : AndesTextViewStyleInterface {
    override val weightValue: Int
        get() = R.dimen.andes_body_font_weight

    override fun getTextSize(context: Context): Float {
        return customTextSize.takeIf { it != NO_TEXT_SIZE_SET }
            ?: context.resources.getDimension(R.dimen.andes_textview_body_font_size_s)
    }

    override fun getFont(context: Context): Typeface {
        return context.getFontOrDefault(customFontValue).takeIf { customFontValue != NO_FONT_SET }
            ?: context.getFontOrDefault(R.font.andes_font_regular)
    }

    override fun getLineHeight(context: Context): Int {
        return customLineHeight.toInt().takeIf { customLineHeight != NO_LINE_HEIGHT_SET }
            ?: context.resources.getDimensionPixelSize(R.dimen.andes_body_line_height_s)
    }

    private companion object {
        private const val NO_TEXT_SIZE_SET = 0F
        private const val NO_FONT_SET = 0
        private const val NO_LINE_HEIGHT_SET = 0F
    }
}
