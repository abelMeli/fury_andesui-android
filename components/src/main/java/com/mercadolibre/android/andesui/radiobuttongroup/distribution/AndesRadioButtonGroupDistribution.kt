package com.mercadolibre.android.andesui.radiobuttongroup.distribution

import java.util.Locale

enum class AndesRadioButtonGroupDistribution {
    VERTICAL,
    HORIZONTAL;

    companion object {
        fun fromString(value: String): AndesRadioButtonGroupDistribution = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesRadioButtonGroupAlign()

    private fun getAndesRadioButtonGroupAlign(): AndesRadioButtonGroupDistributionInterface {
        return when (this) {
            VERTICAL -> AndesRadioButtonGroupVertical
            HORIZONTAL -> AndesRadioButtonGroupHorizontal
        }
    }
}
