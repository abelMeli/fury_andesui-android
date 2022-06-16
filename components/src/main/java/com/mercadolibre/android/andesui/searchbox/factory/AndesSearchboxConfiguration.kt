package com.mercadolibre.android.andesui.searchbox.factory

import android.content.Context
import android.graphics.Typeface
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.typeface.getFontOrDefault

internal data class AndesSearchboxConfiguration(
        val placeHolderText: String? = null,
        val typeface: Typeface
)

internal object AndesSearchboxConfigurationFactory {
    fun create(context: Context, andesSearchboxAttrs: AndesSearchboxAttrs): AndesSearchboxConfiguration {
        return with(andesSearchboxAttrs) {
            AndesSearchboxConfiguration(
                    placeHolderText = placeholder,
                    typeface = resolveTypeface(context)
            )
        }
    }

    private fun resolveTypeface(context: Context) = context.getFontOrDefault(R.font.andes_font_regular)
}
