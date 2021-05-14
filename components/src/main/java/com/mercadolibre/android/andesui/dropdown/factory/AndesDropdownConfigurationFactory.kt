package com.mercadolibre.android.andesui.dropdown.factory

import android.content.Context
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownStateInterface
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState

internal data class AndesDropdownConfiguration(
    val menuType: AndesDropdownMenuType,
    val label: String?,
    val helper: String?,
    val placeHolder: String?,
    val textfieldState: AndesTextfieldState,
    val iconColor: Int
)

internal object AndesDropdownConfigurationFactory {

    fun create(context: Context, andesDropdownAttrs: AndesDropdownAttrs): AndesDropdownConfiguration {
        return with(andesDropdownAttrs) {
            AndesDropdownConfiguration(
                    menuType = andesDropdownAttrs.andesDropdownMenuType,
                    label = andesDropdownAttrs.andesDropdownLabel,
                    helper = andesDropdownAttrs.andesDropdownHelper,
                    placeHolder = andesDropdownAttrs.andesDropdownPlaceHolder,
                    textfieldState = resolveState(context, andesDropdownState.state),
                    iconColor = resolveColor(context, andesDropdownState.state)
            )
        }
    }

    private fun resolveState(context: Context, state: AndesDropdownStateInterface): AndesTextfieldState = state.textfieldState(context)
    private fun resolveColor(context: Context, state: AndesDropdownStateInterface): Int = state.iconColor(context)
}
