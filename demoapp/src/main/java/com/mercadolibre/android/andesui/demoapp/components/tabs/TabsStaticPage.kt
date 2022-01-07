package com.mercadolibre.android.andesui.demoapp.components.tabs

import android.content.Context
import android.view.View
import android.widget.TextView
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTabsBinding
import com.mercadolibre.android.andesui.tabs.AndesTabItem

class TabsStaticPage {

    private lateinit var items: ArrayList<AndesTabItem>
    private lateinit var views: ArrayList<View>

    fun create(context: Context, container: View) {
        val binding = AndesuiStaticTabsBinding.bind(container)
        initComponents(context)
        setupAndesTabLeftAlign(binding)
        setupAndesTabFullWidth(binding)
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

    private fun setupAndesTabLeftAlign(binding: AndesuiStaticTabsBinding) = with(binding) {
        andesTabLeftAlign.setItems(items)
        viewPager.adapter = AndesTabsPagerAdapter(views)
        andesTabLeftAlign.setupWithViewPager(viewPager)
    }

    private fun setupAndesTabFullWidth(binding: AndesuiStaticTabsBinding) {
        binding.andesTabFullWidth.setItems(items)
    }
}
