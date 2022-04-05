package com.mercadolibre.android.andesui.amountfield.entrymode

/**
 * This values allow the dev to choose the different entry modes the component can take. When choosing
 * the [INT] option, the writing will occur from left to right, starting from the integer part of the
 * value, while the [DECIMAL] option will configure the writing from right to left, starting from
 * the decimal values.
 *
 * To see live examples, follow [this link](https://www.figma.com/proto/Kd1wiAh2jetqnR0ZJ2EEUY/Amount-field?node-id=937%3A188891&viewport=332%2C48%2C0.32&scaling=min-zoom&starting-point-node-id=937%3A188891&show-proto-sidebar=1)
 */
enum class AndesAmountFieldEntryMode(internal val entryMode: AndesAmountFieldEntryModeInterface) {
    INT(AndesAmountFieldEntryModeInt),
    DECIMAL(AndesAmountFieldEntryModeDecimal)
}
