package com.mercadolibre.android.andesui.demoapp.components.progressindicator

import android.os.Bundle
import android.view.View
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticProgressBinding
import com.mercadolibre.android.andesui.demoapp.utils.AndesSpecs
import com.mercadolibre.android.andesui.demoapp.utils.launchSpecs

class ProgressShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_progress_indicator)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                AndesuiStaticProgressBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        addStaticPage(adapter.views[0])
    }

    private fun addStaticPage(container: View) {
        bindAndesSpecsButton(container)
    }

    private fun bindAndesSpecsButton(container: View) {
        AndesuiStaticProgressBinding.bind(container).andesuiDemoappAndesSpecsProgress.setOnClickListener {
            launchSpecs(container.context, AndesSpecs.PROGRESS_INDICATOR)
        }
    }
}
