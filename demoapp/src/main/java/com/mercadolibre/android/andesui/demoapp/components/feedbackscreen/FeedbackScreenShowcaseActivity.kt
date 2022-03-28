package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.BaseActivity
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.*

class FeedbackScreenShowcaseActivity : BaseActivity() {

    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        attachIndicator()
        loadViews()
        configToolbarVisibility()
        configStatusBarColor()
    }

    override fun getAppBarTitle(): String =
        resources.getString(R.string.andes_demoapp_screen_feedbackscreen)

    private fun initViewPager() {
        viewPager = baseBinding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicFeedbackScreenBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleSuccessBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleWarningBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleBodyBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenCongratsBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleBodyBinding.inflate(layoutInflater).root
            )
        )
        viewPager.offscreenPageLimit = OFFSCREEN_PAGES_LIMIT

    }

    private fun attachIndicator() {
        baseBinding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        FeedbackScreenDynamicPage().create(this, adapter.views[DYNAMIC_POSITION])
        FeedbackScreenStaticSimpleGreenPage().create(
            this,
            adapter.views[SIMPLE_SUCCESS_POSITION] as ConstraintLayout
        )
        FeedbackScreenStaticSimpleOrangePage().create(
            this,
            adapter.views[SIMPLE_WARNING_POSITION] as ConstraintLayout
        )
        FeedbackScreenStaticSimpleRedBodyPage().create(
            this,
            adapter.views[SIMPLE_ERROR_POSITION] as ConstraintLayout
        )
        FeedbackScreenStaticErrorPage().create(
            adapter.views[GENERIC_ERROR_POSITION] as ConstraintLayout
        )
    }


    /**
     * we need to load this view separately from the others because of the behavior of the statusBar.
     * The congrats feedbackScreen should not be used inside a viewPager.
     */
    private fun loadCongratsView() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        FeedbackScreenStaticCongratsPage().create(
            this,
            adapter.views[CONGRATS_POSITION] as ConstraintLayout
        )
    }


    /**
     * we need to set this listener to be able to hide the toolbar when a feedbackScreen is showing
     */
    private fun configToolbarVisibility() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) {
                    supportActionBar?.show()
                } else {
                    supportActionBar?.hide()
                }
            }

            override fun onPageSelected(position: Int) = Unit

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
    }


    /**
     * we need to set this listener to be able to configure the statusBarColor while navigating the static pages.
     * since the viewPager pre-loads and attaches the views contained in the adapter before we navigate to them,
     * and the statusBarColor is set at the attaching of the feedbackScreen, we need to manually set the color to
     * prevent changing the color before necessary.
     */
    private fun configStatusBarColor() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                if (position == CONGRATS_POSITION) {
                    loadCongratsView()
                }
                updateStatusBar(position)
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
    }


    private fun updateStatusBar(position: Int) {
        if (position == CONGRATS_POSITION) {
            with(window) {
                clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor =
                    androidx.core.content.ContextCompat.getColor(this@FeedbackScreenShowcaseActivity, R.color.andes_green_500)
            }
        } else {
            val typedValue = android.util.TypedValue()
            theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue,true)
            window.statusBarColor =
                androidx.core.content.ContextCompat.getColor(this, typedValue.resourceId)
        }
    }

    companion object {
        private const val OFFSCREEN_PAGES_LIMIT = 4
        private const val DYNAMIC_POSITION = 0
        private const val SIMPLE_SUCCESS_POSITION = 1
        private const val SIMPLE_WARNING_POSITION = 2
        private const val SIMPLE_ERROR_POSITION = 3
        private const val CONGRATS_POSITION = 4
        private const val GENERIC_ERROR_POSITION = 5
    }
}

