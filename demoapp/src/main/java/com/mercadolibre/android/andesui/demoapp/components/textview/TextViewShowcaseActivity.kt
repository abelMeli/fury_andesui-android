package com.mercadolibre.android.andesui.demoapp.components.textview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator

class TextViewShowcaseActivity : AppCompatActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        initActionBar()
        initViewPager()
        attachIndicator()
        loadViews()
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_textview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        val inflater = LayoutInflater.from(this)
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
            inflater.inflate(R.layout.andesui_dynamic_textview, null, false),
            inflater.inflate(R.layout.andesui_static_textview, null, false)
        ))
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        TextViewDynamicPage().create(this, adapter.views[0])
        TextViewStaticPage().create(this, adapter.views[1])
    }
}
