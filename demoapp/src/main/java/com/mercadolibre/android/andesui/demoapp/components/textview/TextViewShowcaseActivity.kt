package com.mercadolibre.android.andesui.demoapp.components.textview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicTextviewBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiShowcaseMainBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticTextviewBinding

class TextViewShowcaseActivity : AppCompatActivity() {

    private lateinit var viewPager: CustomViewPager
    private val binding by lazy { AndesuiShowcaseMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActionBar()
        initViewPager()
        attachIndicator()
        loadViews()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.andesuiNavBar)
        supportActionBar?.title = resources.getString(R.string.andes_demoapp_screen_textview)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        viewPager = binding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(listOf(
            AndesuiDynamicTextviewBinding.inflate(layoutInflater).root,
            AndesuiStaticTextviewBinding.inflate(layoutInflater).root
        ))
    }

    private fun attachIndicator() {
        binding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        TextViewDynamicPage().create(this, adapter.views[0])
        TextViewStaticPage().create(this, adapter.views[1])
    }
}
