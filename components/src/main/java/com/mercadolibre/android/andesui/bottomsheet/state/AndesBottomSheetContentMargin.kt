package com.mercadolibre.android.andesui.bottomsheet.state

enum class AndesBottomSheetContentMargin {
    DEFAULT,
    NO_HORIZONTAL_MARGINS;

    internal val margins get() = getAndesBottomSheetContentMargins()

    private fun getAndesBottomSheetContentMargins(): AndesBottomSheetContentMarginInterface {
        return when (this) {
            DEFAULT -> AndesBottomSheetDefaultContentMargin
            NO_HORIZONTAL_MARGINS -> AndesBottomSheetHorizontalContentMargin
        }
    }
}
