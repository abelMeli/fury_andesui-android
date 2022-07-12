package com.mercadolibre.android.andesui.modal.full.headertype

/**
 * This enum handles the visibility of the header components for the modal.
 * This components are the title and the "X" dismiss button.
 * Each enum value represents a combination of visibility VISIBLE or GONE over this two components.
 */
internal enum class AndesModalFullHeaderType(internal val header: AndesModalFullHeaderTypeInterface) {
    HEADER_NONE(AndesModalHeaderNone),
    TITLE_CLOSE(AndesModalHeaderTitleClose),
    ONLY_TITLE(AndesModalHeaderOnlyTitle),
    ONLY_CLOSE(AndesModalHeaderOnlyClose)
}
