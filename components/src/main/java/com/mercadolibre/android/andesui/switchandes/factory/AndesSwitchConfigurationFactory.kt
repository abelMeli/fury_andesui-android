package com.mercadolibre.android.andesui.switchandes.factory

import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType

internal data class AndesSwitchConfiguration(
    val text: String?,
    val leftTextVisibility: Int,
    val rightTextVisibility: Int,
    val status: AndesSwitchStatus,
    val type: AndesSwitchType,
    val titleNumberOfLines: Int
)

internal object AndesSwitchConfigurationFactory {

    internal fun create(andesSwitchAttrs: AndesSwitchAttrs): AndesSwitchConfiguration {
        return with(andesSwitchAttrs) {
            AndesSwitchConfiguration(
                text = andesSwitchText,
                leftTextVisibility = andesSwitchAlign.align.leftTextVisibility(),
                rightTextVisibility = andesSwitchAlign.align.rightTextVisibility(),
                status = andesSwitchStatus,
                type = andesSwitchType,
                titleNumberOfLines = andesSwitchTitleNumberOfLines
            )
        }
    }
}
