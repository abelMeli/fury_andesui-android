package com.mercadolibre.android.andesui.dropdown.type

import java.util.Locale

enum class AndesDropdownMenuType {
    BOTTOMSHEET,
    FLOATINGMENU;

    companion object {
        fun fromString(value: String): AndesDropdownMenuType = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val type get() = getAndesDropdownMenuType()

    private fun getAndesDropdownMenuType(): AndesDropdownMenuInterface {
        return when (this) {
            BOTTOMSHEET -> AndesDropdownMenuTypeBottomSheet()
            FLOATINGMENU -> AndesDropdownMenuTypeFloatingMenu()
        }
    }
}
