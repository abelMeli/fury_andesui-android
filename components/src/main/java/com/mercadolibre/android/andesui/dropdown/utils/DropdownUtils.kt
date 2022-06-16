package com.mercadolibre.android.andesui.dropdown.utils

import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem

internal fun filterList(text: String, list: List<AndesDropDownItem>): List<AndesDropDownItem> {
    list.filter { item ->
        item.title.contains(text, true)
    }.also { filteredList ->
        return filteredList
    }
}