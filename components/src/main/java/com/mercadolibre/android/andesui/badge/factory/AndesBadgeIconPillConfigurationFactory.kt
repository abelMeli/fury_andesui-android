package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.graphics.drawable.Drawable

internal data class AndesBadgeIconPillConfiguration(
    val icon: Drawable?
)

internal object AndesBadgeIconPillConfigurationFactory {

    fun create(context: Context, andesBadgeIconPillAttrs: AndesBadgeIconPillAttrs): AndesBadgeIconPillConfiguration {
        with(andesBadgeIconPillAttrs) {
            return AndesBadgeIconPillConfiguration(
                icon = andesBadgeType.type.icon(
                    context,
                    andesBadgeSize.size.height(context).toInt(),
                    andesBadgeType.type.primaryColor().colorInt(context))
            )
        }
    }
}
