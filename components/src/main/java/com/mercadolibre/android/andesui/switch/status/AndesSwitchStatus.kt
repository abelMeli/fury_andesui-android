package com.mercadolibre.android.andesui.switch.status

/**
 * Class with the different status options for the AndesSwitch
 */
enum class AndesSwitchStatus {
    CHECKED,
    UNCHECKED;

    internal val status get() = getAndesSwitchStatus()

    private fun getAndesSwitchStatus(): AndesSwitchStatusInterface {
        return when (this) {
            CHECKED -> AndesSwitchStatusChecked
            UNCHECKED -> AndesSwitchStatusUnchecked
        }
    }
}
