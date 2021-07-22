package com.mercadolibre.android.andesui.demoapp.components.textfield

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class TextfieldShowcaseActivity : AppCompatActivity() {

    private lateinit var viewPager: CustomViewPager
    private val lifecycleScope = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.andesui_showcase_main)

        initActionBar()
        initViewPager()
        attachIndicator()
    }

    override fun onDestroy() {
        lifecycleScope.cancel()
        super.onDestroy()
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById(R.id.andesui_nav_bar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(listOf<View>(
                InflateTextfieldHelper.inflateAndesTextfield(this),
                InflateTextfieldHelper.inflateAndesTextfieldArea(this),
                InflateTextfieldHelper.inflateAndesTextfieldCode(this),
                AutosuggestView(this, lifecycleScope),
                InflateTextfieldHelper.inflateStaticTextfieldLayout(this)
        ))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(position: Int) = Unit
            override fun onPageScrolled(position: Int, p1: Float, p2: Int) = Unit
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> supportActionBar?.title = resources.getString(R.string.andes_textfield_screen)
                    1 -> supportActionBar?.title = resources.getString(R.string.andes_textarea_screen)
                    2 -> supportActionBar?.title = resources.getString(R.string.andes_textcode_screen)
                    3 -> supportActionBar?.title = resources.getString(R.string.andes_autosuggest_screen)
                    else -> supportActionBar?.title = resources.getString(R.string.andes_textfield_screen)
                }
            }
        })
    }

    private fun attachIndicator() {
        val indicator = findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
    }
}
