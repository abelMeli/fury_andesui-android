package com.mercadolibre.android.andesui.badge.hierarchy

import com.mercadolibre.android.andesui.badge.type.AndesBadgeTypeInterface
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.badge.AndesBadgeIconPill

/**
 * Defines all style related properties that an [AndesBadgeIconPill] needs to be drawn properly.
 * Those properties change depending on the style of the badge.
 *
 */
internal interface AndesBadgeIconHierarchyInterface {
    /**
     * Returns a [AndesColor] that contains the color data for the message background.
     *
     * @param type is the selected type of badge.
     * @return a [AndesColor] that contains the color data for the badge background.
     */
    fun backgroundColor(type: AndesBadgeTypeInterface): AndesColor
}

internal class AndesLoudBadgeIconHierarchy : AndesBadgeIconHierarchyInterface {
    override fun backgroundColor(type: AndesBadgeTypeInterface) = type.primaryColor()
}

internal class AndesSecondaryBadgeIconHierarchy : AndesBadgeIconHierarchyInterface {
    override fun backgroundColor(type: AndesBadgeTypeInterface) = type.primaryVariantColor()
}
