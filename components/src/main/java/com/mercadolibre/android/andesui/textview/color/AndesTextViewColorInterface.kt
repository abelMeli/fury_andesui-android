package com.mercadolibre.android.andesui.textview.color

import android.content.Context
import androidx.annotation.ColorInt
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.toColor

internal interface AndesTextViewColorInterface {
    fun getColor(context: Context): Int
}

internal object AndesTextViewColorPrimary : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_primary.toColor(context)
}

internal object AndesTextViewColorSecondary : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_secondary.toColor(context)
}

internal object AndesTextViewColorDisabled : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_disabled.toColor(context)
}

internal object AndesTextViewColorInverted : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_white.toColor(context)
}

internal object AndesTextViewColorNegative : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_negative.toColor(context)
}

internal object AndesTextViewColorCaution : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_caution.toColor(context)
}

internal object AndesTextViewColorPositive : AndesTextViewColorInterface {
    override fun getColor(context: Context) = R.color.andes_text_color_positive.toColor(context)
}

internal class AndesTextViewColorLink(private val isInverted: Boolean) : AndesTextViewColorInterface {
    override fun getColor(context: Context): Int {
        return if (isInverted) {
            R.color.andes_text_color_white_link.toColor(context)
        } else {
            R.color.andes_accent_color.toColor(context)
        }
    }
}

internal class AndesTextViewColorCustom(@ColorInt private val color: Int) : AndesTextViewColorInterface {
    override fun getColor(context: Context): Int {
        return color.takeIf { it != NO_COLOR_SET }
            ?: R.color.andes_text_color_primary.toColor(context)
    }

    private companion object {
        private const val NO_COLOR_SET = 0
    }
}
