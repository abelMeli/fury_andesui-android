package com.mercadolibre.android.andesui.timepicker.state

import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState

/**
 * Defines all style related properties that an [AndesTimePicker] needs to be drawn properly.
 * Those properties change depending on the state of the dropdown.
 *
 */
internal interface AndesTimePickerStateInterface {
    fun dropDownState(): AndesDropdownState
}

internal object AndesIdleTimePickerState : AndesTimePickerStateInterface {
    override fun dropDownState() = AndesDropdownState.ENABLED
}

internal object AndesErrorTimePickerState : AndesTimePickerStateInterface {
    override fun dropDownState() = AndesDropdownState.ERROR
}

internal object AndesDisabledTimePickerState : AndesTimePickerStateInterface {
    override fun dropDownState() = AndesDropdownState.DISABLED
}

internal object AndesReadOnlyTimePickerState : AndesTimePickerStateInterface {
    override fun dropDownState() = AndesDropdownState.ENABLED
}
