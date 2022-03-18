package com.mercadolibre.android.andesui.buttongroup.distribution

import java.util.Locale

/**
 * Enum class that defines the possible distributions that buttons in an [AndesButtonGroup] can take.
 * But as a bonus it gives you the proper implementation so you don't have to do any mapping.
 *
 *
 * @property distribution Possible distributions that buttons can take, in an [AndesButtonGroup].
 */
enum class AndesButtonGroupDistribution(internal val distribution: AndesButtonGroupDistributionInterface){
    VERTICAL(AndesButtonGroupDistributionVertical),
    HORIZONTAL(AndesButtonGroupDistributionHorizontal),
    AUTO(AndesButtonGroupDistributionAuto),
    MIXED(AndesButtonGroupDistributionMixed);

    companion object {
        fun fromString(value: String): AndesButtonGroupDistribution = valueOf(value.toUpperCase(
            Locale.ROOT))
    }
}
