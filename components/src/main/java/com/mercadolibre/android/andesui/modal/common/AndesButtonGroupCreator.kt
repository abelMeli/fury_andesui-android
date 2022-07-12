package com.mercadolibre.android.andesui.modal.common

import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup

/**
 * Interface created for two reasons:
 *  -Provide a method to dismiss the modal at builder level (before the modal is shown).
 *  -Avoid passing the buttonGroup instance directly to the fragment/dialogFragment and instead
 *   passing a method to create it from the other side.
 */
interface AndesButtonGroupCreator {
    fun create(modalInterface: AndesModalInterface): AndesButtonGroupData
}

/**
 * Interface created to make available the dismiss action to the buttons in the modal button group.
 */
interface AndesModalInterface {

    /**
     * Method to dismiss the modal
     */
    fun dismiss()
}

/**
 * This class represents the buttons to show in the modal.
 * @param buttonGroup the AndesButtonGroup to display. It can have between 1 and 3 buttons
 * @param mainAction optional value to indicate the main button of the ones passed in the buttonGroup.
 * This property is only utilized in the carousel variants of the modal.
 */
data class AndesButtonGroupData(
    val buttonGroup: AndesButtonGroup,
    val mainAction: Int? = null
) {
    init {
        if (!isValid()) {
            throw IllegalArgumentException("The main action value should be between 0 and the amount of buttons passed in the buttonGroup")
        }
    }

    private fun isValid(): Boolean {
        if (mainAction == null) return true
        return mainAction < buttonGroup.getButtonsAmount() && mainAction >= 0
    }
}
