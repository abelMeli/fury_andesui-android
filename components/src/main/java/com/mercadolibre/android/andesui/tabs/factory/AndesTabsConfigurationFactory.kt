package com.mercadolibre.android.andesui.tabs.factory

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor
import com.mercadolibre.android.andesui.tabs.type.AndesTabsTypeInterface

internal data class AndesTabsConfiguration(
    val type: AndesTabsTypeInterface,
    val tabTextSize: Float,
    val tabTextColor: ColorStateList,
    val tabIndicatorColor: AndesColor,
    val tabIndicatorHeight: Int,
    val tabIndicatorCornerPx: Int,
    val tabRippleBackground: ColorStateList
)

internal object AndesTabsConfigurationFactory {

    fun create(andesTagChoiceAttrs: AndesTabsAttrs, context: Context): AndesTabsConfiguration {
        return with(andesTagChoiceAttrs) {
            AndesTabsConfiguration(
                type = andesTabsType.type,
                tabTextSize = context.resources.getDimension(R.dimen.andes_tabs_text_size),
                tabTextColor = resolveTextColorState(context),
                tabIndicatorColor = R.color.andes_accent_color_500.toAndesColor(),
                tabIndicatorHeight = context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_height),
                tabIndicatorCornerPx = context.resources.getDimensionPixelSize(R.dimen.andes_tab_indicator_corner),
                tabRippleBackground = resolveRippleBackground(context)
            )
        }
    }

    private fun resolveTextColorState(context: Context) = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected), // selected
            intArrayOf(android.R.attr.state_pressed), // pressed
            intArrayOf() // default
        ),
        intArrayOf(
            ContextCompat.getColor(context, R.color.andes_accent_color), // selected
            ContextCompat.getColor(context, R.color.andes_accent_color), // pressed
            ContextCompat.getColor(context, R.color.andes_text_color_primary) // default
        )
    )

    private fun resolveRippleBackground(context: Context) = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed) // selected
        ),
        intArrayOf(
            ContextCompat.getColor(context, R.color.andes_accent_color_100) // selected
        )
    )
}
