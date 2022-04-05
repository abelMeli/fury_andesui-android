package com.mercadolibre.android.andesui.amountfield.entrytype

/**
 * This enum contains the variants of entry type for the [com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple].
 * To see more info, see [this link](https://www.figma.com/file/ma8IQUYi9IS8zc8C0rAzEB/Components-specifications?node-id=4050%3A476413)
 */
enum class AndesAmountFieldEntryType(internal val entryType: AndesAmountFieldEntryTypeInterface) {
    MONEY(AndesAmountFieldEntryTypeMoney),
    PERCENTAGE(AndesAmountFieldEntryTypePercentage)
}
