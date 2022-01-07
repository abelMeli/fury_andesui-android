package com.mercadolibre.android.andesui.demoapp.components.textfield

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class TextfieldShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager
    private val lifecycleScope = CoroutineScope(SupervisorJob())
    private var appBarTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
    }

    override fun getAppBarTitle() = resources.getString(R.string.andes_demoapp_screen_textfield)

    override fun onDestroy() {
        lifecycleScope.cancel()
        super.onDestroy()
    }

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
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
                appBarTitle = when (position) {
                    0 -> resources.getString(R.string.andes_demoapp_screen_textfield)
                    1 -> resources.getString(R.string.andes_textarea_screen)
                    2 -> resources.getString(R.string.andes_textcode_screen)
                    3 -> resources.getString(R.string.andes_autosuggest_screen)
                    else -> resources.getString(R.string.andes_demoapp_screen_textfield)
                }
                updateAppBarTitle(appBarTitle)
            }
        })
    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }
}
