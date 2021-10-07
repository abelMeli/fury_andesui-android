package com.mercadolibre.android.andesui.demoapp.components.feedbackscreen

import android.os.Build
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
import com.mercadolibre.android.andesui.demoapp.utils.PageIndicator
import com.mercadolibre.android.andesui.utils.doWhenGreaterThanApi

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
    ): View? {
        val view = inflater.inflate(R.layout.andesui_showcase_main, container, false)

        initActionBar(view)
        initViewPager(view, inflater)
        attachIndicator(view)
        loadViews()
        setupToolbarViewPagerListener()
        setupStatusBarViewPagerListener()
        configToolbarVisibility()
        configStatusBarColor()

        return view
    }

    private fun initActionBar(container: View) {
        toolbar = container.findViewById(R.id.andesui_nav_bar)
        toolbar.setTitle(R.string.andes_demoapp_feedbackscreen)
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.andes_white))
    }

    private fun initViewPager(container: View, inflater: LayoutInflater) {
        viewPager = container.findViewById(R.id.andesui_viewpager)
        viewPager.adapter = AndesPagerAdapter(
            listOf<View>(
                inflater.inflate(R.layout.andesui_dynamic_feedback_screen, null, false),
                inflater.inflate(
                    R.layout.andesui_static_feedback_screen_simple_success,
                    null,
                    false
                ),
                inflater.inflate(
                    R.layout.andesui_static_feedback_screen_simple_warning,
                    null,
                    false
                ),
                inflater.inflate(R.layout.andesui_static_feedback_screen_simple_body, null, false),
                inflater.inflate(R.layout.andesui_static_feedback_screen_congrats, null, false)
            )
        )
        viewPager.offscreenPageLimit = OFFSCREEN_PAGES_LIMIT
    }

    private fun attachIndicator(container: View) {
        val indicator = container.findViewById<PageIndicator>(R.id.page_indicator)
        indicator.attach(viewPager)
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
                doWhenGreaterThanApi(Build.VERSION_CODES.LOLLIPOP) {
                    if (position == CONGRATS_POSITION) {
                        requireActivity().window.apply {
                            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            statusBarColor =
                                ContextCompat.getColor(requireContext(), R.color.andes_green_500)
                        }
                    } else {
                        requireActivity().apply {
                            val typedValue = TypedValue()
                            theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
                            window.statusBarColor =
                                ContextCompat.getColor(this, typedValue.resourceId)
                        }
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // no-op
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
