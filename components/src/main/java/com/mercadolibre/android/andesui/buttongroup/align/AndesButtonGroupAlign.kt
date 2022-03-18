package com.mercadolibre.android.andesui.buttongroup.align

import java.util.Locale

/**
 * Enum class that defines the possible alignments that an [AndesButtonGroup] can take.
 * But as a bonus it gives you the proper implementation so you don't have to do any mapping.
 *
 * @property align Possible alignments that an [AndesButtonGroup] can take.
 */
enum class AndesButtonGroupAlign(internal val align: AndesButtonGroupAlignInterface) {
    LEFT(AndesButtonGroupLeft),
    RIGHT(AndesButtonGroupRight),
    CENTER(AndesButtonGroupCenter);

    companion object {
        fun fromString(value: String): AndesButtonGroupAlign = valueOf(value.toUpperCase(Locale.ROOT))
    }
}
