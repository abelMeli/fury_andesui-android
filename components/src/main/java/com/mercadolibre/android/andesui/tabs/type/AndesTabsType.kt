package com.mercadolibre.android.andesui.tabs.type

/**
 * List of all types that can be used to configure AndesTabs layout style.
 */
sealed class AndesTabsType {
    /**
     * Will fill the available width in screen with all AndesTabItems.
     * It is not recommended to use this type when the number of items is large.
     */
    object FullWidth : AndesTabsType()

    /**
     * Will add AndesTabItems to the screen starting from the left. In case the number of items is
     * larger than the available space, the container will scroll in order to show all items.
     *
     * @param overflowMode will show a shadow at the end of the screen.
     */
    data class LeftAlign(val overflowMode: Boolean = false) : AndesTabsType()

    companion object {
        /**
         * Retrieves an AndesTabsType that matches the string value. If it is not possible will return FullWidth type.
         */
        fun fromString(value: String): AndesTabsType {
            return when (value) {
                LeftAlign::class.java.simpleName -> LeftAlign()
                else -> FullWidth
            }
        }
    }

    internal val type get() = getAndesTabsType()

    private fun getAndesTabsType(): AndesTabsTypeInterface {
        return when (this) {
            is FullWidth -> AndesFullWidthTabsType
            is LeftAlign -> AndesLeftAlignTabsType(overflowMode)
        }
    }
}
