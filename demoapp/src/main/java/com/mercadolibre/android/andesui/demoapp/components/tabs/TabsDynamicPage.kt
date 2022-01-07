package com.mercadolibre.android.andesui.demoapp.components.tabs

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTabsBinding
import com.mercadolibre.android.andesui.tabs.AndesTabItem
import com.mercadolibre.android.andesui.tabs.AndesTabs
import com.mercadolibre.android.andesui.tabs.type.AndesTabsType

class TabsDynamicPage {

    private lateinit var items: ArrayList<AndesTabItem>
    private lateinit var views: ArrayList<View>
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
        val binding = AndesuiDynamicTabsBinding.bind(container)
        initComponents(context)
        setupTypeSpinner(context, binding.dynamicTabsSpinnerType)
        setupTypeCallback(context, binding.dynamicTabsSpinnerCallback)
        setupDynamicTab(binding)
    }

    private fun initComponents(context: Context) {
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

    private fun setupTypeSpinner(context: Context, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_tabs_spinner_type,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setupTypeCallback(context: Context, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            context,
            R.array.andes_tabs_spinner_callback,
            android.R.layout.simple_spinner_item
        ).let { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        callback = object : AndesTabs.OnTabChangedListener {
            override fun onTabSelected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_SELECT) {
                    Toast.makeText(context.applicationContext, "Tab $position selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabUnselected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_UNSELECT) {
                    Toast.makeText(context.applicationContext, "Tab $position unselected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabReselected(position: Int, tabs: List<AndesTabItem>) {
                if (callbackType == CALLBACK_TYPE_RESELECT) {
                    Toast.makeText(context.applicationContext, "Tab $position reselected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupDynamicTab(binding: AndesuiDynamicTabsBinding) = with(binding) {
        showcaseDynamicTabs.setItems(items)
        showcaseDynamicTabs.setOnTabChangedListener(callback)

        dynamicTabsButtonClear.setOnClickListener {
            dynamicTabsSpinnerType.setSelection(0)
            dynamicTabsSpinnerCallback.setSelection(0)
            updateView(this)
        }

        dynamicTabsButtonUpdate.setOnClickListener {
            updateView(this)
        }
    }

    private fun updateView(binding: AndesuiDynamicTabsBinding) = with(binding) {
        if (dynamicTabsSpinnerType.selectedItem == TAB_TYPE_LEFT_ALIGN) {
            showcaseDynamicTabs.type = AndesTabsType.LeftAlign()
        } else {
            showcaseDynamicTabs.type = AndesTabsType.FullWidth
        }

        callbackType = dynamicTabsSpinnerCallback.selectedItem as String
    }
}
