package com.mercadolibre.android.andesui.demoapp.components.dropdown

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.checkbox.AndesCheckbox
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicDropdownBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticDropdownBinding
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.AndesDropdownStandalone
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.textfield.AndesTextfield

@Suppress("TooManyFunctions")
class DropdownShowcaseActivity : BaseActivity(), AndesDropdownDelegate {

    private var andesDropDownLabel = "Title"
    private var andesDropDownPlaceHolder = "Placeholder"
    private var andesDropDownHelper = "Helper"

    private lateinit var andesDropDownForm: AndesDropDownForm
    private lateinit var andesDropdownStandalone: AndesDropdownStandalone
    private lateinit var buttonClear: AndesButton
    private lateinit var buttonUpdate: AndesButton
    private lateinit var editTextTitle: AndesTextfield
    private lateinit var editTextPlaceHolder: AndesTextfield
    private lateinit var editTextHelper: AndesTextfield
    private lateinit var editTextSearchPlaceHolder: AndesTextfield
    private lateinit var checkboxStarter: AndesCheckbox
    private lateinit var checkboxIndeterminate: AndesCheckbox
    private lateinit var checkboxSearchable: AndesCheckbox
    private lateinit var stateSpinner: Spinner
    private lateinit var menuTypeSpinner: Spinner

    private lateinit var viewPager: CustomViewPager

    companion object {
        private const val DROP_DOWN_ITEMS_COUNT = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_dropdown)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            AndesuiDynamicDropdownBinding.inflate(layoutInflater).root,
            AndesuiStaticDropdownBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    private fun addDynamicPage(container: View) {
        val binding = AndesuiDynamicDropdownBinding.bind(container)
        buttonClear = binding.buttonClear
        buttonUpdate = binding.buttonUpdate

        andesDropDownForm = binding.andesDropdown

        andesDropDownForm.label = andesDropDownLabel
        andesDropDownForm.placeholder = andesDropDownPlaceHolder
        andesDropDownForm.helper = andesDropDownHelper

        andesDropDownForm.setItems(getFakeList())

        andesDropDownForm.delegate = this

        editTextTitle = binding.editTextDropdownLabel
        editTextPlaceHolder = binding.editTextDropdownPlaceHolder
        editTextHelper = binding.editTextDropdownHelper
        editTextSearchPlaceHolder = binding.editTextDropdownSearchPlaceholder
        checkboxStarter = binding.checkboxDropdownStartingItem
        checkboxStarter.text = "Starting Item 0"
        checkboxIndeterminate = binding.checkboxDropdownIndeterminate
        checkboxIndeterminate.text = "Set Indeterminate"
        checkboxSearchable = binding.checkboxDropdownSearchable
        checkboxSearchable.text = "Searchable"

        stateSpinner = binding.dropdownStateSpinner
        val stateAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                this.resources.getStringArray(R.array.andes_dropdown_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        stateSpinner.setSelection(0)

        menuTypeSpinner = binding.dropdownMenuTypeSpinner
        val menuTypeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            this.resources.getStringArray(R.array.andes_dropdown_menutype_spinner)
        )
        menuTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        menuTypeSpinner.adapter = menuTypeAdapter

        menuTypeSpinner.setSelection(0)

        buttonClear.setOnClickListener {
            clear()
        }

        buttonUpdate.setOnClickListener {
            update()
        }
    }

    private fun addStaticPage(container: View) {
        val binding = AndesuiStaticDropdownBinding.bind(container)
        val spinnerType = binding.andesDropdownStandaloneShowCaseSpinner
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_type_list_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        andesDropdownStandalone = binding.andesDropdownStandalone

        andesDropdownStandalone.setItems(getFakeList())

        andesDropdownStandalone.addSearchable(AndesSearchbox(this))

        andesDropdownStandalone.delegate = this

        spinnerType.setSelection(1) // medium value

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                andesDropdownStandalone.size = when (spinnerType.getItemAtPosition(position).toString().toLowerCase()) {
                    "small" -> {
                        AndesDropdownSize.SMALL
                    }
                    "medium" -> {
                        AndesDropdownSize.MEDIUM
                    }
                    "large" -> {
                        AndesDropdownSize.LARGE
                    }
                    else -> AndesDropdownSize.MEDIUM
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Do nothing.
            }
        }
    }

    override fun onItemSelected(andesDropDown: AndesListDelegate, position: Int) {
        Toast.makeText(applicationContext, "item selected position: $position", Toast.LENGTH_SHORT).show()
    }

    private fun clear() {
        editTextTitle.text = ""
        editTextPlaceHolder.text = ""
        editTextHelper.text = ""
        editTextSearchPlaceHolder.text = ""
        stateSpinner.setSelection(0)
        menuTypeSpinner.setSelection(0)
        checkboxIndeterminate.status = AndesCheckboxStatus.UNSELECTED
        checkboxStarter.status = AndesCheckboxStatus.UNSELECTED
        checkboxSearchable.status = AndesCheckboxStatus.UNSELECTED

        andesDropDownForm.apply {
            label = andesDropDownLabel
            helper = andesDropDownHelper
            placeholder = andesDropDownPlaceHolder
            setItems(getFakeList())
            removeSearchable()
            menuType = AndesDropdownMenuType.BOTTOMSHEET
            state = AndesDropdownState.ENABLED
        }
    }

    private fun checkboxStarterClick() {
        if (checkboxStarter.status == AndesCheckboxStatus.SELECTED) {
            andesDropDownForm.setItems(getFakeList(), 0)
        } else {
            andesDropDownForm.setItems(getFakeList())
        }
    }

    private fun checkboxIndeterminateClick() {
        if (checkboxIndeterminate.status == AndesCheckboxStatus.SELECTED) {
            andesDropDownForm.setIndeterminate(true)
        } else {
            andesDropDownForm.setIndeterminate(false)
        }
    }

    private fun checkboxSearchableClick() {
        if (checkboxSearchable.status == AndesCheckboxStatus.SELECTED) {
            andesDropDownForm.addSearchable(AndesSearchbox(this, editTextSearchPlaceHolder.text.toString()))
        } else {
            andesDropDownForm.removeSearchable()
        }
    }

    private fun update() {
        andesDropDownForm.label = editTextTitle.text.toString()
        andesDropDownForm.placeholder = editTextPlaceHolder.text.toString()
        andesDropDownForm.helper = editTextHelper.text.toString()
        checkboxStarterClick()
        andesDropDownForm.state = AndesDropdownState.fromString(stateSpinner.selectedItem.toString().toUpperCase())
        andesDropDownForm.menuType = AndesDropdownMenuType.fromString(menuTypeSpinner.selectedItem.toString().toUpperCase())
        checkboxIndeterminateClick()
        checkboxSearchableClick()
    }

    private fun getFakeList(): List<AndesDropDownItem> {
        val listItems: MutableList<AndesDropDownItem> = mutableListOf()

        var item: AndesDropDownItem?
        for (i in 1..DROP_DOWN_ITEMS_COUNT) {
            item = AndesDropDownItem()
            item.title = "test$i"
            item.avatar = ContextCompat.getDrawable(this, R.drawable.andes_otros_almanaque_20)

            listItems.add(item)
        }

        return listItems
    }
}
