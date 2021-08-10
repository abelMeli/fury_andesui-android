package com.mercadolibre.android.andesui.demoapp.components.tabs

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.tabs.AndesTabItem
import com.mercadolibre.android.andesui.tabs.AndesTabs

class TabsStaticPage {

    private lateinit var andesTabsLeftAlign: AndesTabs
    private lateinit var andesTabsFullWidth: AndesTabs
    private lateinit var viewPager: ViewPager
    private lateinit var items: ArrayList<AndesTabItem>
    private lateinit var views: ArrayList<View>

    fun create(context: Context, container: View) {
        initComponents(container, context)
        setupAndesTabLeftAlign()
        setupAndesTabFullWidth()
    }

    private fun initComponents(container: View, context: Context) {
        andesTabsLeftAlign = container.findViewById(R.id.andes_tab_left_align)
        andesTabsFullWidth = container.findViewById(R.id.andes_tab_full_width)
        viewPager = container.findViewById(R.id.view_pager)

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

    private fun setupAndesTabLeftAlign() {
        andesTabsLeftAlign.setItems(items)
        viewPager.adapter = AndesTabsPagerAdapter(views)
        andesTabsLeftAlign.setupWithViewPager(viewPager)
    }

    private fun setupAndesTabFullWidth() {
        andesTabsFullWidth.setItems(items)
    }
}
