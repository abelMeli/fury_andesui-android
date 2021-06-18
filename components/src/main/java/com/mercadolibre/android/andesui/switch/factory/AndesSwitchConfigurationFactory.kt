package com.mercadolibre.android.andesui.switch.factory

import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType

internal data class AndesSwitchConfiguration(
    val text: String?,
    val leftTextVisibility: Int,
    val rightTextVisibility: Int,
    val status: AndesSwitchStatus,
    val type: AndesSwitchType
)

internal object AndesSwitchConfigurationFactory {

    internal fun create(andesSwitchAttrs: AndesSwitchAttrs): AndesSwitchConfiguration {
        return with(andesSwitchAttrs) {
            AndesSwitchConfiguration(
                text = andesSwitchText,
                leftTextVisibility = andesSwitchAlign.align.leftTextVisibility(),
                rightTextVisibility = andesSwitchAlign.align.rightTextVisibility(),
                status = andesSwitchStatus,
                type = andesSwitchType
            )
        }
    }
}
