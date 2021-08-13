package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.graphics.drawable.Drawable
import com.mercadolibre.android.andesui.color.AndesColor

internal data class AndesBadgeIconPillConfiguration(
    val backgroundColor: AndesColor,
    val size: Float,
    val icon: Drawable?
)

internal object AndesBadgeIconPillConfigurationFactory {

    fun create(context: Context, andesBadgeIconPillAttrs: AndesBadgeIconPillAttrs): AndesBadgeIconPillConfiguration {
        with(andesBadgeIconPillAttrs) {
            return AndesBadgeIconPillConfiguration(
                backgroundColor = andesBadgeType.type.primaryColor(),
                size = andesBadgeSize.size.height(context),
                icon = andesBadgeType.type.icon(context, andesBadgeSize)
            )
        }
    }
}
