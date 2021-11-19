package com.mercadolibre.android.andesui.demoapp.components.dropdown

import android.os.Bundle
import android.view.LayoutInflater
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
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.dropdown.AndesDropDownForm
import com.mercadolibre.android.andesui.dropdown.AndesDropDownItem
import com.mercadolibre.android.andesui.dropdown.AndesDropdownStandalone
import com.mercadolibre.android.andesui.dropdown.size.AndesDropdownSize
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
import com.mercadolibre.android.andesui.dropdown.type.AndesDropdownMenuType
import com.mercadolibre.android.andesui.dropdown.utils.AndesDropdownDelegate
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
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
    private lateinit var sizeSpinner: Spinner
    private lateinit var checkboxStarter: AndesCheckbox
    private lateinit var checkboxIndeterminate: AndesCheckbox
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
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_dropdown, null, false),
                inflater.inflate(R.layout.andesui_static_dropdown, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addDynamicPage(adapter.views[0])
        addStaticPage(adapter.views[1])
    }

    private fun addDynamicPage(container: View) {
        buttonClear = container.findViewById(R.id.buttonClear)
        buttonUpdate = container.findViewById(R.id.buttonUpdate)

        andesDropDownForm = container.findViewById(R.id.andesDropdown)

        andesDropDownForm.label = andesDropDownLabel
        andesDropDownForm.placeholder = andesDropDownPlaceHolder
        andesDropDownForm.helper = andesDropDownHelper

        andesDropDownForm.setItems(getFakeList())

        andesDropDownForm.delegate = this

        editTextTitle = container.findViewById(R.id.editTextDropdownLabel)
        editTextPlaceHolder = container.findViewById(R.id.editTextDropdownPlaceHolder)
        editTextHelper = container.findViewById(R.id.editTextDropdownHelper)
        checkboxStarter = container.findViewById(R.id.checkboxDropdownStartingItem)
        checkboxStarter.text = "Starting Item 0"
        checkboxIndeterminate = container.findViewById(R.id.checkboxDropdownIndeterminate)
        checkboxIndeterminate.text = "Set Indeterminate"

        stateSpinner = container.findViewById(R.id.dropdownStateSpinner)
        val stateAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                this.resources.getStringArray(R.array.andes_dropdown_state_spinner)
        )
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        stateSpinner.setSelection(0)

        menuTypeSpinner = container.findViewById(R.id.dropdownMenuTypeSpinner)
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
        val spinnerType: Spinner = container.findViewById(R.id.andes_dropdown_standalone_show_case_spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.andes_type_list_spinner,
                android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        andesDropdownStandalone = container.findViewById(R.id.andesDropdownStandalone)

        andesDropdownStandalone.setItems(getFakeList())

        andesDropdownStandalone.delegate = this

        sizeSpinner = container.findViewById(R.id.andes_dropdown_standalone_show_case_spinner)
        sizeSpinner.setSelection(1) // medium value

        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                andesDropdownStandalone.size = when (sizeSpinner.getItemAtPosition(position).toString().toLowerCase()) {
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
        Toast.makeText(this, "item selected position: $position", Toast.LENGTH_SHORT).show()
    }

    private fun clear() {
        editTextTitle.text = ""
        editTextPlaceHolder.text = ""
        editTextHelper.text = ""
        stateSpinner.setSelection(0)
        menuTypeSpinner.setSelection(0)
        checkboxIndeterminate.status = AndesCheckboxStatus.UNSELECTED
        checkboxStarter.status = AndesCheckboxStatus.UNSELECTED

        andesDropDownForm.apply {
            label = andesDropDownLabel
            helper = andesDropDownHelper
            placeholder = andesDropDownPlaceHolder
            setItems(getFakeList())
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

    private fun update() {
        andesDropDownForm.label = editTextTitle.text.toString()
        andesDropDownForm.placeholder = editTextPlaceHolder.text.toString()
        andesDropDownForm.helper = editTextHelper.text.toString()
        checkboxStarterClick()
        andesDropDownForm.state = AndesDropdownState.fromString(stateSpinner.selectedItem.toString().toUpperCase())
        andesDropDownForm.menuType = AndesDropdownMenuType.fromString(menuTypeSpinner.selectedItem.toString().toUpperCase())
        checkboxIndeterminateClick()
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
