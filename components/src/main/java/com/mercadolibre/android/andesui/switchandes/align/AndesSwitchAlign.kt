package com.mercadolibre.android.andesui.switchandes.align

/**
 * Class with the different align options for the AndesSwitch
 */
enum class AndesSwitchAlign {
    LEFT,
    RIGHT;

    internal val align get() = getAndesSwitchAlign()

    private fun getAndesSwitchAlign(): AndesSwitchAlignInterface {
        return when (this) {
            LEFT -> AndesSwitchLeft
            RIGHT -> AndesSwitchRight
        }
    }
}
