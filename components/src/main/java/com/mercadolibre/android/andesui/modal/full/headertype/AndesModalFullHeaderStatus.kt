package com.mercadolibre.android.andesui.modal.full.headertype

/**
 * Internal enum class to expose the two states the header title component of the full modal can have.
 */
internal enum class AndesModalFullHeaderStatus(internal val status: AndesModalFullHeaderStatusInterface) {
    COLLAPSED(AndesModalFullHeaderStatusCollapsed),
    EXPANDED(AndesModalFullHeaderStatusExpanded)
}
