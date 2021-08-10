package com.mercadolibre.android.andesui.tabs.factory

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.tabs.type.AndesTabsType

/**
 * The data class that contains the public components of the tabs.
 */
internal data class AndesTabsAttrs(
    val andesTabsType: AndesTabsType
)

/**
 * This object parse the attribute set and return an instance of AndesTabsAttrs
 */
internal object AndesTabsAttrsParser {

    private const val ANDES_TAB_TYPE_FULL_WIDTH = "1000"
    private const val ANDES_TAB_TYPE_LEFT_ALIGN = "1001"

    fun parse(context: Context, attr: AttributeSet?): AndesTabsAttrs {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.AndesTabs)
        return AndesTabsAttrs(
            andesTabsType = parseAndesTabsType(typedArray)
        ).also { typedArray.recycle() }
    }

    private fun parseAndesTabsType(typedArray: TypedArray): AndesTabsType {
        return when (typedArray.getString(R.styleable.AndesTabs_andesTabsType)) {
            ANDES_TAB_TYPE_FULL_WIDTH -> AndesTabsType.FullWidth
            ANDES_TAB_TYPE_LEFT_ALIGN -> AndesTabsType.LeftAlign()
            else -> AndesTabsType.FullWidth
        }
    }
}
