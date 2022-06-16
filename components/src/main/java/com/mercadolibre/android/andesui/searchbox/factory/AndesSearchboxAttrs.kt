package com.mercadolibre.android.andesui.searchbox.factory

import android.content.Context
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R

internal data class AndesSearchboxAttrs(val placeholder: String?)

internal object AndesSearchboxAttrsParser {

    fun parse(context: Context, attr: AttributeSet?): AndesSearchboxAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesSearchbox)

        return AndesSearchboxAttrs(
                placeholder = typedArray.getString(R.styleable.AndesSearchbox_andesSearchboxPlaceholder)
                        ?: context.getString(R.string.andes_searchbox_placeholder_default)
        ).also { typedArray.recycle() }
    }
}
