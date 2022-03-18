package com.mercadolibre.android.andesui.buttongroup.margin

import java.util.Locale

/**
 * Enum class that defines the possible margin that an [AndesButtonGroup] can take.
 * But as a bonus it gives you the proper implementation so you don't have to do any mapping.
 *
 * @property margin Possible margin that an [AndesButtonGroup] can take.
 */
internal enum class AndesButtonGroupMargin(internal val margin: AndesButtonGroupMarginInterface) {
    HORIZONTAL(AndesButtonGroupMarginHorizontal),
    VERTICAL(AndesButtonGroupMarginVertical),
    AUTO(AndesButtonGroupMarginAuto),
    MIXED(AndesButtonGroupMarginMixed);

    companion object {
        fun fromString(value: String): AndesButtonGroupMargin = valueOf(value.toUpperCase(Locale.ROOT))
    }
}
