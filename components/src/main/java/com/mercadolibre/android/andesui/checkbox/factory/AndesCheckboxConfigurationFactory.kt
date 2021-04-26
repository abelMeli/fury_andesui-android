package com.mercadolibre.android.andesui.checkbox.factory

import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType

internal data class AndesCheckboxConfiguration(
    val text: String?,
    val textSize: Int,
    val align: AndesCheckboxAlign,
    val status: AndesCheckboxStatus,
    val type: AndesCheckboxType,
    val titleNumberOfLines: Int
)

internal object AndesCheckboxConfigurationFactory {

    private const val DEFAULT_TEXT_SIZE = 16

    fun create(andesCheckboxAttrs: AndesCheckboxAttrs): AndesCheckboxConfiguration {
        return with(andesCheckboxAttrs) {
            val validatedStatus = if (andesCheckboxAttrs.andesCheckboxType == AndesCheckboxType.ERROR) {
                AndesCheckboxStatus.UNSELECTED
            } else {
                andesCheckboxAttrs.andesCheckboxStatus
            }
            AndesCheckboxConfiguration(
                text = andesCheckboxText,
                textSize = DEFAULT_TEXT_SIZE,
                align = andesCheckboxAttrs.andesCheckboxAlign,
                status = validatedStatus,
                type = andesCheckboxAttrs.andesCheckboxType,
                titleNumberOfLines = andesCheckboxAttrs.andesCheckboxTitleNumberOfLine
            )
        }
    }
}
