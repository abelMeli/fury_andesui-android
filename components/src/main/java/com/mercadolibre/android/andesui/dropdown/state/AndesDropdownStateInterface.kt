package com.mercadolibre.android.andesui.dropdown.state

import android.content.Context
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

/**
 * Defines all style related properties that an [AndesDropdown] needs to be drawn properly.
 * Those properties change depending on the style of the message.
 *
 */
internal sealed class AndesDropdownStateInterface {
    abstract fun textfieldState(context: Context): AndesTextfieldState
    open fun iconColor(context: Context): Int = R.color.andes_accent_color_500
}

internal object AndesEnabledDropdownState : AndesDropdownStateInterface() {
    override fun textfieldState(context: Context): AndesTextfieldState = AndesTextfieldState.IDLE
}

internal object AndesErrorDropdownState : AndesDropdownStateInterface() {
    override fun textfieldState(context: Context): AndesTextfieldState = AndesTextfieldState.ERROR
}

internal object AndesDisabledDropdownState : AndesDropdownStateInterface() {
    override fun textfieldState(context: Context): AndesTextfieldState = AndesTextfieldState.DISABLED

    override fun iconColor(context: Context): Int = R.color.andes_gray_250
}
