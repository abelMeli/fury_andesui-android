package com.mercadolibre.android.andesui.badge.factory

import android.content.Context
import android.graphics.drawable.Drawable

internal data class AndesBadgeIconPillConfiguration(
    val icon: Drawable?
)

internal object AndesBadgeIconPillConfigurationFactory {

    fun create(context: Context, andesBadgeIconPillAttrs: AndesBadgeIconPillAttrs): AndesBadgeIconPillConfiguration {
        with(andesBadgeIconPillAttrs) {
            val badgeTypeInterface = andesBadgeType.iconType.type
            val badgeHierarchyInterface = andesBadgeHierarchy.hierarchy
            return AndesBadgeIconPillConfiguration(
                icon = badgeTypeInterface.icon(
                    context,
                    andesBadgeSize,
                    badgeHierarchyInterface.backgroundColor(badgeTypeInterface).colorInt(context)
                )
            )
        }
    }
}
