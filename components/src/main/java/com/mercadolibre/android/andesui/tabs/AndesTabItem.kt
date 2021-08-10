package com.mercadolibre.android.andesui.tabs

/**
 * Represents a section of [AndesTabs].
 * @param title text of the section.
 * @param isEnabled sets if the section is selectable or not.
 */
data class AndesTabItem(
    val title: String,
    val isEnabled: Boolean = true
)
