package com.mercadolibre.android.andesui.badge.type

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.color.toAndesColor

/**
 * Defines all types related properties that an [AndesBadge] needs to be drawn properly.
 * Those properties change depending on the style of the badge.
 *
 */
internal interface AndesBadgeTypeInterface {

    /**
     * Returns a [AndesColor] that contains the primary color data for the badge.
     *
     * @return a [AndesColor] that contains the primary color data for the badge.
     */
    fun primaryColor(): AndesColor

    /**
     * Returns a [AndesColor] that contains the secondary color data for the badge.
     *
     * @return a [AndesColor] that contains the secondary color data for the badge.
     */
    fun secondaryColor(): AndesColor
}

internal class AndesNeutralBadgeType : AndesBadgeTypeInterface {
    override fun primaryColor() = R.color.andes_gray_550_solid.toAndesColor()
    override fun secondaryColor() = R.color.andes_gray_070_solid.toAndesColor()
}

internal class AndesHighlightBadgeType : AndesBadgeTypeInterface {
    override fun primaryColor() = R.color.andes_accent_color_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_accent_color_100.toAndesColor()
}

internal class AndesSuccessBadgeType : AndesBadgeTypeInterface {
    override fun primaryColor() = R.color.andes_green_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_green_100.toAndesColor()
}

internal class AndesWarningBadgeType : AndesBadgeTypeInterface {
    override fun primaryColor() = R.color.andes_orange_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_orange_100.toAndesColor()
}

internal class AndesErrorBadgeType : AndesBadgeTypeInterface {
    override fun primaryColor() = R.color.andes_red_500.toAndesColor()
    override fun secondaryColor() = R.color.andes_red_100.toAndesColor()
}
