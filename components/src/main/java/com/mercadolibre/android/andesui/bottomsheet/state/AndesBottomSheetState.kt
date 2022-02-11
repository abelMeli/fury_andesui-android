package com.mercadolibre.android.andesui.bottomsheet.state

import java.util.Locale

enum class AndesBottomSheetState {
    COLLAPSED,
    EXPANDED,
    HALF_EXPANDED;

    companion object {
        fun fromString(value: String): AndesBottomSheetState = valueOf(value.toUpperCase(Locale.ROOT))
    }

    internal val state get() = getAndesBottomSheetState()

    private fun getAndesBottomSheetState(): AndesBottomSheetStateInterface {
        return when (this) {
            HALF_EXPANDED -> AndesBottomSheetStateHalfExpanded
            EXPANDED -> AndesBottomSheetStateExpanded
            COLLAPSED -> AndesBottomSheetStateCollapsed
        }
    }
}
