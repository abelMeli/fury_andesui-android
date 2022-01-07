package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.mercadolibre.android.andesui.demoapp.R
import com.mercadolibre.android.andesui.demoapp.commons.AndesPagerAdapter
import com.mercadolibre.android.andesui.demoapp.commons.CustomViewPager
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiDynamicFeedbackScreenBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiShowcaseMainBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFeedbackScreenCongratsBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFeedbackScreenSimpleBodyBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFeedbackScreenSimpleSuccessBinding
import com.mercadolibre.android.andesui.demoapp.databinding.AndesuiStaticFeedbackScreenSimpleWarningBinding

@Suppress("TooManyFunctions")
class FeedbackScreenConfigFragment : Fragment() {

    private lateinit var viewPager: CustomViewPager
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarViewPagerListener: ViewPager.OnPageChangeListener
    private lateinit var statusBarViewPagerListener: ViewPager.OnPageChangeListener

    companion object {
        private const val OFFSCREEN_PAGES_LIMIT = 4
        private const val DYNAMIC_POSITION = 0
        private const val SIMPLE_SUCCESS_POSITION = 1
        private const val SIMPLE_WARNING_POSITION = 2
        private const val SIMPLE_ERROR_POSITION = 3
        private const val CONGRATS_POSITION = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AndesuiShowcaseMainBinding.inflate(inflater, container, false)

        initActionBar(binding)
        initViewPager(binding)
        attachIndicator(binding)
        loadViews()
        setupToolbarViewPagerListener()
        setupStatusBarViewPagerListener()
        configToolbarVisibility()
        configStatusBarColor()

        return binding.root
    }

    private fun initActionBar(binding: AndesuiShowcaseMainBinding) {
        toolbar = binding.andesuiNavBar
        toolbar.setTitle(R.string.andes_demoapp_screen_feedbackscreen)
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.andes_white))
    }

    private fun initViewPager(binding: AndesuiShowcaseMainBinding) {
        viewPager = binding.andesuiViewpager
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                AndesuiDynamicFeedbackScreenBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleSuccessBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleWarningBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenSimpleBodyBinding.inflate(layoutInflater).root,
                AndesuiStaticFeedbackScreenCongratsBinding.inflate(layoutInflater).root
            )
        )
        viewPager.offscreenPageLimit = OFFSCREEN_PAGES_LIMIT
    }

    private fun attachIndicator(binding: AndesuiShowcaseMainBinding) {
        binding.pageIndicator.attach(viewPager)
    }

    private fun loadViews() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        FeedbackScreenDynamicPage().create(requireContext(), adapter.views[DYNAMIC_POSITION])
        FeedbackScreenStaticSimpleGreenPage().create(
            requireContext(),
            adapter.views[SIMPLE_SUCCESS_POSITION] as ConstraintLayout
        )
        FeedbackScreenStaticSimpleOrangePage().create(
            requireContext(),
            adapter.views[SIMPLE_WARNING_POSITION] as ConstraintLayout
        )
        FeedbackScreenStaticSimpleRedBodyPage().create(
            requireContext(),
            adapter.views[SIMPLE_ERROR_POSITION] as ConstraintLayout
        )
    }

    /**
     * we need to load this view separately from the others because of the behavior of the statusBar.
     * The congrats feedbackScreen should not be used inside a viewPager because
     */
    private fun loadCongratsView() {
        val adapter = viewPager.adapter as AndesPagerAdapter
        FeedbackScreenStaticCongratsPage().create(
            requireContext(),
            adapter.views[CONGRATS_POSITION] as ConstraintLayout
        )
    }

    private fun setupToolbarViewPagerListener() {
        toolbarViewPagerListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) {
                    toolbar.visibility = View.VISIBLE
                } else {
                    toolbar.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {
                // no-op
            }

            override fun onPageScrollStateChanged(state: Int) {
                // no-op
            }
        }
    }

    private fun setupStatusBarViewPagerListener() {
        statusBarViewPagerListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // no-op
            }

            override fun onPageSelected(position: Int) {
                if (position == CONGRATS_POSITION) {
                    loadCongratsView()
                }
                updateStatusBar(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                // no-op
            }
        }
    }

    @SuppressLint("NewApi")
    private fun updateStatusBar(position: Int) {
        if (position == CONGRATS_POSITION) {
            with(requireActivity().window) {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.andes_green_500)
            }
        } else {
            with(requireActivity()) {
                val typedValue = TypedValue()
                theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
                window.statusBarColor =
                    ContextCompat.getColor(this, typedValue.resourceId)
            }
        }
    }

    /**
     * we need to set this listener to be able to hide the toolbar when a feedbackScreen is showing
     */
    private fun configToolbarVisibility() {
        viewPager.addOnPageChangeListener(toolbarViewPagerListener)
    }

    /**
     * we need to set this listener to be able to configure the statusBarColor while navigating the static pages.
     * since the viewPager pre-loads and attaches the views contained in the adapter before we navigate to them,
     * and the statusBarColor is set at the attaching of the feedbackScreen, we need to manually set the color to
     * prevent changing the color before necessary.
     */
    private fun configStatusBarColor() {
        viewPager.addOnPageChangeListener(statusBarViewPagerListener)
    }
}
