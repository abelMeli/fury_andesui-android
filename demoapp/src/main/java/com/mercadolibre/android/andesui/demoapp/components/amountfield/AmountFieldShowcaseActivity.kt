package com.mercadolibre.android.andesui.demoapp.components.amountfield

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicAmountfieldBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticAmountfieldBinding

class AmountFieldShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            AndesuiDynamicAmountfieldBinding.inflate(layoutInflater).root,
            AndesuiStaticAmountfieldBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        AmountFieldSimpleDynamicPage().create(adapter.views[0])
        AmountFieldSimpleStaticPage().create(adapter.views[1])
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_amountfield)
}