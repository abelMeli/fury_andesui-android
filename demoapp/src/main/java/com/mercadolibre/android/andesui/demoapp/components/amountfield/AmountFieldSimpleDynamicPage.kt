package com.mercadolibre.android.andesui.demoapp.components.amountfield

import android.view.View
import com.mercadolibre.android.andesui.amountfield.entrymode.AndesAmountFieldEntryMode
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicAmountfieldBinding
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.switchandes.AndesSwitch
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus

class AmountFieldSimpleDynamicPage {

    private lateinit var binding: AndesuiDynamicAmountfieldBinding
    private var selectedCountry = AndesCountry.AR
    private var selectedState: AndesAmountFieldState = AndesAmountFieldState.Idle
    private var selectedEntryMode: AndesAmountFieldEntryMode? = null
    private var selectedCurrency = AndesMoneyAmountCurrency.ARS
    private var selectedEntryType = AndesAmountFieldEntryType.MONEY
    private var showIsoValueInCurrency = false
    private val countryList by lazy {
        AndesCountry
            .values()
            .map { AndesDropDownItem().apply { title = it.name } }
    }
    private val stateList by lazy {
        listOf(
            "Idle",
            "Error"
        ).map { AndesDropDownItem().apply { title = it } }
    }
    private val entryModeList by lazy {
        listOf(
            "INT",
            "DECIMAL",
            "Not set"
        )
        .map { AndesDropDownItem().apply { title = it } }
    }
    private val currencyList by lazy {
        AndesMoneyAmountCurrency
            .values()
            .map { AndesDropDownItem().apply { title = it.name } }
    }
    private val entryTypeList by lazy {
        AndesAmountFieldEntryType
            .values()
            .map { AndesDropDownItem().apply { title = it.name } }
    }

    fun create(containerView: View) {
        setupBinding(containerView)
        setupDropdowns()
        setupTextFields()
        setupButtons()
        setupSwitch()
    }

    private fun setupSwitch() {
        binding.dynamicAmountFieldSwitchIso.setOnStatusChangeListener(
            object : AndesSwitch.OnStatusChangeListener {
                override fun onStatusChange(andesSwitch: AndesSwitchStatus) {
                    showIsoValueInCurrency = andesSwitch == AndesSwitchStatus.CHECKED
                }
            }
        )
    }

    private fun setupButtons() {
        binding.dynamicAmountFieldClearButton.apply {
            text = "Clear"
            setOnClickListener {
                clearValues()
                setValuesToComponent()
            }
        }
        binding.dynamicAmountFieldUpdateButton.apply {
            text = "Update"
            setOnClickListener {
                setValuesToComponent()
            }
        }
    }

    private fun setupTextFields() {
        binding.dynamicAmountFieldTextfieldHelper.apply {
            label = "Helper"
            placeholder = "Insert helper"
        }
        binding.dynamicAmountFieldTextfieldSuffix.apply {
            label = "Suffix"
            placeholder = "Insert suffix"
        }
        binding.dynamicAmountFieldTextfieldMaxValue.apply {
            label = "Max value"
            placeholder = "0.00"
        }
        binding.dynamicAmountFieldTextfieldNumberDecimals.apply {
            label = "Number of decimals"
            placeholder = "0"
        }
        binding.dynamicAmountFieldTextfieldSuffixA11y.apply { 
            label = "Suffix a11y text"
            placeholder = "Insert text value"
        }
    }

    private fun clearValues() {
        resetComponent()
        resetDropdowns()
        resetTextfields()
        resetSwitch()
    }

    private fun resetSwitch() {
        binding.dynamicAmountFieldSwitchIso.status = AndesSwitchStatus.UNCHECKED
        showIsoValueInCurrency = false
    }

    private fun resetComponent() {
        binding.dynamicAmountField.value = null
    }

    private fun resetTextfields() {
        binding.dynamicAmountFieldTextfieldHelper.text = ""
        binding.dynamicAmountFieldTextfieldSuffix.text = ""
        binding.dynamicAmountFieldTextfieldNumberDecimals.text = ""
        binding.dynamicAmountFieldTextfieldMaxValue.text = ""
        binding.dynamicAmountFieldTextfieldSuffixA11y.text = ""
    }

    private fun resetDropdowns() {
        binding.dynamicAmountFieldDropdownState.setItems(stateList, 0)
        binding.dynamicAmountFieldDropdownEntryMode.setItems(entryModeList, 2)
        binding.dynamicAmountFieldDropdownCurrency.setItems(currencyList, 11)
        binding.dynamicAmountFieldDropdownCountry.setItems(countryList, 0)
        binding.dynamicAmountFieldDropdownType.setItems(entryTypeList, 0)
        selectedState = AndesAmountFieldState.Idle
        selectedEntryMode = AndesAmountFieldEntryMode.INT
        selectedCurrency = AndesMoneyAmountCurrency.ARS
        selectedCountry = AndesCountry.AR
        selectedEntryType = AndesAmountFieldEntryType.MONEY
    }

    private fun setValuesToComponent() {
        val newNumberOfDecimals = if (binding.dynamicAmountFieldTextfieldNumberDecimals.text.isNullOrEmpty()) {
            null
        } else binding.dynamicAmountFieldTextfieldNumberDecimals.text!!.toInt()

        val newMaxValue = if (binding.dynamicAmountFieldTextfieldMaxValue.text.isNullOrEmpty()) {
            null
        } else binding.dynamicAmountFieldTextfieldMaxValue.text!!

        binding.dynamicAmountField.apply {
            helperText = binding.dynamicAmountFieldTextfieldHelper.text
            suffixText = binding.dynamicAmountFieldTextfieldSuffix.text
            suffixA11yText = binding.dynamicAmountFieldTextfieldSuffixA11y.text
            numberOfDecimals = newNumberOfDecimals
            maxValue = newMaxValue
            currency = selectedCurrency
            showCurrencyAsIsoValue = showIsoValueInCurrency
            country = selectedCountry
            entryMode = selectedEntryMode
            state = selectedState
            entryType = selectedEntryType
        }
    }

    private fun setupBinding(containerView: View) {
        binding = AndesuiDynamicAmountfieldBinding.bind(containerView)
    }

    private fun setupDropdowns() {
        setupCountryDropdown()
        setupCurrencyDropdown()
        setupEntryModeDropdown()
        setupStateDropdown()
        setupEntryTypeDropdown()
    }

    private fun setupEntryTypeDropdown() {
        binding.dynamicAmountFieldDropdownType.apply {
            label = "Entry type"
            placeholder = "Select entry type"
            setItems(entryTypeList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedEntryType = AndesAmountFieldEntryType.valueOf(entryTypeList[position].title)
                }
            }
        }
    }

    private fun setupCountryDropdown() {
        binding.dynamicAmountFieldDropdownCountry.apply {
            label = "Country"
            placeholder = "Select country"
            setItems(countryList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedCountry = AndesCountry.valueOf(countryList[position].title)
                }
            }
        }
    }

    private fun setupCurrencyDropdown() {
        binding.dynamicAmountFieldDropdownCurrency.apply {
            label = "Currency"
            placeholder = "Select currency"
            setItems(currencyList, 11)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedCurrency = AndesMoneyAmountCurrency.valueOf(currencyList[position].title)
                }
            }
        }
    }

    private fun setupEntryModeDropdown() {
        binding.dynamicAmountFieldDropdownEntryMode.apply {
            label = "Entry mode"
            placeholder = "Select entry mode"
            setItems(entryModeList, 2)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedEntryMode = if (position == 2) {
                        null
                    } else {
                        AndesAmountFieldEntryMode.valueOf(entryModeList[position].title)
                    }
                }
            }
        }
    }

    private fun setupStateDropdown() {
        binding.dynamicAmountFieldDropdownState.apply {
            label = "State"
            placeholder = "Select state"
            setItems(stateList, 0)
            delegate = object : AndesDropdownDelegate {
                override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
                    selectedState = when (stateList[position].title) {
                        "Idle" -> AndesAmountFieldState.Idle
                        "Error" -> AndesAmountFieldState.Error
                        else -> AndesAmountFieldState.Idle
                    }
                }
            }
        }
    }
}
