package com.mercadolibre.android.andesui.demoapp.components.floatingmenu

import android.os.Bundle
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicFloatingmenuBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFloatingmenuBinding

class FloatingMenuShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_floatingmenu)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
                AndesuiDynamicFloatingmenuBinding.inflate(layoutInflater).root,
                AndesuiStaticFloatingmenuBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        FloatingMenuDynamicPage().create(this, adapter.views[0])
        FloatingMenuStaticPage().create(this, adapter.views[1])
    }
}
