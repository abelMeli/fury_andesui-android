package com.mercadolibre.android.andesui.demoapp.components.tabs

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.tabs.AndesTabItem
import com.mercadolibre.android.andesui.tabs.AndesTabs
import com.mercadolibre.android.andesui.tabs.type.AndesTabsType

class TabsDynamicPage {

    private lateinit var tabsDynamic: AndesTabs
    private lateinit var items: ArrayList<AndesTabItem>
    private lateinit var views: ArrayList<View>
    private lateinit var spinnerType: Spinner
    private lateinit var spinnerCallback: Spinner
    private lateinit var clearButton: AndesButton
    private lateinit var updateButton: AndesButton
    private lateinit var callback: AndesTabs.OnTabChangedListener
    private var callbackType = CALLBACK_TYPE_NONE

    companion object {
        private const val CALLBACK_TYPE_NONE = "None"
        private const val CALLBACK_TYPE_SELECT = "Select"
        private const val CALLBACK_TYPE_UNSELECT = "Unselect"
        private const val CALLBACK_TYPE_RESELECT = "Reselect"
        private const val TAB_TYPE_LEFT_ALIGN = "Left align"
    }

    fun create(context: Context, container: View) {
        initComponents(context, container)
        setupTypeSpinner(context)
        setupTypeCallback(context)
        setupDynamicTab()
    }

    private fun initComponents(context: Context, container: View) {
        tabsDynamic = container.findViewById(R.id.showcase_dynamic_tabs)
        spinnerType = container.findViewById(R.id.dynamic_tabs_spinner_type)
        spinnerCallback = container.findViewById(R.id.dynamic_tabs_spinner_callback)
        clearButton = container.findViewById(R.id.dynamic_tabs_button_clear)
        updateButton = container.findViewById(R.id.dynamic_tabs_button_update)

        views = arrayListOf()
        val view1 = TextView(context)
        view1.text = "Tab content, section 1"
        val view2 = TextView(context)
        views.add(view1)
        view2.text = "Tab content, section 2"
        views.add(view2)
        val view3 = TextView(context)
        view3.text = "Tab content, section 3"
        views.add(view3)

        items = arrayListOf()
        items.add(AndesTabItem("Tab 1"))
        items.add(AndesTabItem("Tab 2"))
        items.add(AndesTabItem("Tab 3"))
    }

    private fun setupTypeSpinner(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_tabs_spinner_type,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }
    }

    private fun setupTypeCallback(context: Context) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_tabs_spinner_callback,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCallback.adapter = adapter
        }

        callback = object : AndesTabs.OnTabChangedListener {
            override fun onTabSelected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_SELECT) {
                    Toast.makeText(context, "Tab $position selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabUnselected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_UNSELECT) {
                    Toast.makeText(context, "Tab $position unselected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabReselected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_RESELECT) {
                    Toast.makeText(context, "Tab $position reselected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupDynamicTab() {
        tabsDynamic.setItems(items)
        tabsDynamic.setOnTabChangedListener(callback)

        clearButton.setOnClickListener {
            spinnerType.setSelection(0)
            spinnerCallback.setSelection(0)
            updateView()
        }

        updateButton.setOnClickListener {
            updateView()
        }
    }

    private fun updateView() {
        if (spinnerType.selectedItem == TAB_TYPE_LEFT_ALIGN) {
            tabsDynamic.type = AndesTabsType.LeftAlign()
        } else {
            tabsDynamic.type = AndesTabsType.FullWidth
        }

        callbackType = spinnerCallback.selectedItem as String
    }
}
