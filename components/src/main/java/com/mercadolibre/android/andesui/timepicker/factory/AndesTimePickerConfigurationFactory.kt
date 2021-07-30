package com.mercadolibre.android.andesui.timepicker.factory

import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.timepicker.minutesInterval.AndesTimePickerIntervalInterface
import com.mercadolibre.android.andesui.timepicker.state.AndesTimePickerState
import com.mercadolibre.android.andesui.timepicker.type.AndesTimePickerType

internal data class AndesTimePickerConfiguration(
    val iconColor: Int,
    val label: String?,
    val helper: String?,
    val state: AndesTimePickerState,
    val interval: AndesTimePickerIntervalInterface,
    val type: AndesTimePickerType,
    val currentTime: String?,
    val dropdownItemsList: ArrayList<AndesDropDownItem>
)

@Suppress("TooManyFunctions")
internal object AndesTimePickerConfigurationFactory {

    fun create(andesTimePickerAttrs: AndesTimePickerAttrs): AndesTimePickerConfiguration {
        return with(andesTimePickerAttrs) {
            AndesTimePickerConfiguration(
                iconColor = R.color.andes_accent_color_500,
                label = andesTimePickerLabel,
                helper = andesTimePickerHelper,
                state = andesTimePickerState,
                interval = andesTimePickerInterval.interval,
                currentTime = andesTimePickerCurrentTime,
                type = andesTimePickerType,
                dropdownItemsList = createDropdownItems(andesTimePickerInterval.interval.full())
            )
        }
    }
}

/**
 * method that transforms the strings list of available hours according to the interval selected
 * into an AndesDropDownItem list needed to populate the dropdown
 */
private fun createDropdownItems(list: List<String>): ArrayList<AndesDropDownItem> {
    val listItems: ArrayList<AndesDropDownItem> = arrayListOf()
    list.forEach {
        val dropDownItem = AndesDropDownItem()
        dropDownItem.title = it
        listItems.add(dropDownItem)
    }
    return listItems
}
