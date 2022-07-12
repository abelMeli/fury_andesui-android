package com.mercadolibre.android.andesui.stickyscrollview

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.stickyscrollview.listener.AndesStickyScrollListener
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.utils.Constants
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesStickyScrollViewTest {

    private lateinit var robolectricActivity: ActivityController<AppCompatActivity>
    private lateinit var activity: AppCompatActivity
    private lateinit var context: Context

    private lateinit var scrollView: AndesStickyScrollView
    private lateinit var scrollContent: LinearLayout
    private lateinit var anotherView: AndesTextView
    private lateinit var titleTextView: AndesTextView
    private lateinit var subTitleTextView: AndesTextView

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.setTheme(R.style.Theme_AppCompat)

        setupTestActivity()
        setupViews()
    }

    @Test
    @Config(qualifiers = "w120dp")
    fun `Given stickyScrollView with title, subtitle and headerId set, when scrolling, then header is stickied to top`() {
        scrollView.setHeaderId(R.id.andes_header_view)
        setupScrollContentOnlyWithTitleAndSubtitle()

        scrollView.smoothScrollTo(SCROLL_X, SCROLL_Y)

        scrollView.scrollY assertEquals SCROLL_Y
        scrollView.isHeaderStickiedToTop() assertEquals true
    }

    @Test
    @Config(qualifiers = "w120dp")
    fun `Given stickyScrollView with top view, title, subtitle and headerId set, when scrolling a small amount, then header is not stickied to top`() {
        scrollView.setHeaderId(R.id.andes_header_view)
        setupScrollContentWithTopView()

        scrollView.smoothScrollTo(SCROLL_X, SCROLL_Y)

        scrollView.scrollY assertEquals SCROLL_Y
        scrollView.isHeaderStickiedToTop() assertEquals false
    }

    @Test
    @Config(qualifiers = "w120dp")
    fun `Given stickyScrollView with title and subtitle but not headerId set, when scrolling, then header is not stickied to top`() {
        setupScrollContentOnlyWithTitleAndSubtitle()

        scrollView.smoothScrollTo(SCROLL_X, SCROLL_Y)

        scrollView.scrollY assertEquals SCROLL_Y
        scrollView.isHeaderStickiedToTop() assertEquals false
    }

    @Test
    @Config(qualifiers = "w120dp")
    fun `Given stickyScrollView with scrollListener set, when scrolling, then callback is called`() {
        scrollView.setHeaderId(R.id.andes_header_view)
        setupScrollContentOnlyWithTitleAndSubtitle()
        val spiedScrollListener = spy(createScrollListener())
        scrollView.setScrollViewListener(spiedScrollListener)

        scrollView.smoothScrollTo(SCROLL_X, SCROLL_Y)

        verify(spiedScrollListener, times(1)).onScrollChanged(any(), any(), any(), any(), any())
    }

    private fun createScrollListener() = object : AndesStickyScrollListener {
        override fun onScrollChanged(
            mScrollX: Int,
            mScrollY: Int,
            oldX: Int,
            oldY: Int,
            isHeaderStickiedToTop: Boolean
        ) {
            // no-op
        }

        override fun onScrollStopped(stoppedY: Boolean) {
            // no-op
        }
    }

    private fun setupTestActivity() {
        robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
    }

    private fun setupViews() {
        titleTextView = AndesTextView(context).apply {
            text = context.resources.getString(R.string.andes_button_text)
            id = R.id.andes_header_view
        }
        subTitleTextView = AndesTextView(context)
        subTitleTextView.text = TEXT_LOREM

        anotherView = AndesTextView(context)
        anotherView.text = TEXT_LOREM

        scrollContent = LinearLayout(context)
        scrollContent.orientation = LinearLayout.VERTICAL

        scrollView = AndesStickyScrollView(activity)
    }

    private fun setupScrollContentWithTopView() {
        scrollContent.addView(anotherView)
        scrollContent.addView(titleTextView)
        scrollContent.addView(subTitleTextView)
        scrollView.addView(scrollContent)

        activity.setContentView(scrollView)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun setupScrollContentOnlyWithTitleAndSubtitle() {
        scrollContent.addView(titleTextView)
        scrollContent.addView(subTitleTextView)
        scrollView.addView(scrollContent)

        activity.setContentView(scrollView)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    private fun AndesStickyScrollView.isHeaderStickiedToTop(): Boolean {
        val headerView= rootView.findViewById<View>(R.id.andes_header_view)
        val isHeaderElevated = headerView.elevation == ELEVATION_VIEW
        val isHeaderTranslatedInYAxis = headerView.translationY == SCROLL_Y.toFloat()
        val isHeaderTranslatedInZAxis = ViewCompat.getTranslationZ(headerView) == STICKY_VALUE
        return isHeaderElevated && isHeaderTranslatedInYAxis && isHeaderTranslatedInZAxis
    }

    companion object {
        private const val SCROLL_X = 0
        private const val SCROLL_Y = 20
        private const val STICKY_VALUE = 1f
        private const val ELEVATION_VIEW = 14f
        private const val TEXT_LOREM =
            """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                |incididunt ut labore et dolore magna aliqua. Lorem ipsum dolor sit amet, 
                |consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore 
                |magna aliqua. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod 
                |tempor incididunt ut labore et dolore magna aliqua."""
    }
}