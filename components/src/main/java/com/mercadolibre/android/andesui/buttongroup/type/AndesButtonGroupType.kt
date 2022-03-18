package com.mercadolibre.android.andesui.buttongroup.type

import com.mercadolibre.android.andesui.buttongroup.align.AndesButtonGroupAlign

/**
 * Class with the different values of types that the [AndesButtonGroup]can take.
 * This sealed class has the values for the Fullwidth and Responsive types
 * with the necessary instructions to be applied to the Constraint.
 */
sealed class AndesButtonGroupType(internal val type: AndesButtonGroupTypeInterface) {
    object FullWidth : AndesButtonGroupType(AndesButtonGroupFullWidth)
    data class Responsive(val align: AndesButtonGroupAlign) : AndesButtonGroupType(AndesButtonGroupResponsive(align))
}
