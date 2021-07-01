package com.mercadolibre.android.andesui.switchandes.type

/**
 * Class with the different type options for the AndesSwitch
 */
enum class AndesSwitchType {
    ENABLED,
    DISABLED;

    internal val type get() = getAndesSwitchType()

    private fun getAndesSwitchType(): AndesSwitchTypeInterface {
        return when (this) {
            ENABLED -> AndesSwitchTypeEnabled
            DISABLED -> AndesSwitchTypeDisabled
        }
    }
}
