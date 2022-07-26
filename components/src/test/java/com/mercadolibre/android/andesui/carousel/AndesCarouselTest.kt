package com.mercadolibre.android.andesui.carousel

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselAttrs
import com.mercadolibre.android.andesui.carousel.factory.AndesCarouselConfigurationFactory
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselAutoplayOn
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselFiniteItemDecoration
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselInfiniteItemDecoration
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselLayoutManager
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.buildAttributeSet
import com.mercadolibre.android.andesui.utils.getAccessibilityManager
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesCarouselTest {

    private lateinit var context: Context
    private val configFactory = spy(AndesCarouselConfigurationFactory)
    private lateinit var attrs: AndesCarouselAttrs
    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test margin none`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "100")
        }

        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_none).toInt(), outRect.left)
    }

    @Test
    fun `test margin default`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "101")
        }

        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_small).toInt(), outRect.left)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test margin Xsmall`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "102")
        }
        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_xsmall).toInt(), outRect.left)
    }

    @Test
    fun `test padding Xsmall`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.XSMALL,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_xsmall).toInt())
    }

    @Test
    fun `test margin small`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "103")
        }
        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_small).toInt(), outRect.left)
    }

    @Test
    fun `test padding small`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.SMALL,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_small).toInt())
    }

    @Test
    fun `test margin medium`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "104")
        }
        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_medium).toInt(), outRect.left)
    }

    @Test
    fun `test padding medium`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.MEDIUM,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_medium).toInt())
    }

    @Test
    fun `test margin large`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselCenter, "true")
            addAttribute(R.attr.andesCarouselInfinite, "true")
            addAttribute(R.attr.andesCarouselMargin, "105")
        }
        val carousel = AndesCarousel(context, attrSet)
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        carousel.delegate = andesCarouselDelegate

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val outRect = Rect(-1,-1,-1,-1)
        val view = View(context)
        val state: RecyclerView.State = mock()
        val spyParent = spy(reflectionRecyclerView)
        `when`(spyParent.getChildAdapterPosition(any())).thenReturn(1)

        reflectionRecyclerView.getItemDecorationAt(0).getItemOffsets(outRect, view, spyParent, state)

        Assert.assertEquals(context.resources.getDimension(R.dimen.andes_carousel_padding_large).toInt(), outRect.left)
    }

    @Test
    fun `test padding large`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.LARGE,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.padding, context.resources.getDimension(R.dimen.andes_carousel_recylcer_padding_large).toInt())
    }

    @Test
    fun `test center true`() {
        attrs = AndesCarouselAttrs(
            CENTER,
            AndesCarouselMargin.NONE,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.center, true)
    }

    @Test
    fun `test center false`() {
        attrs = AndesCarouselAttrs(
            FREE,
            AndesCarouselMargin.NONE,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        Assert.assertEquals(config.center, false)
    }

    @Test
    fun `test infinite false`() {
        attrs = AndesCarouselAttrs(
            FREE,
            AndesCarouselMargin.NONE,
            FINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        assertFalse(config.infinite)
    }

    @Test
    fun `test infinite true`() {
        attrs = AndesCarouselAttrs(
            FREE,
            AndesCarouselMargin.NONE,
            INFINITE,
            STATIC,
            -1
        )
        val config = configFactory.create(context, attrs)
        assertTrue(config.infinite)
    }

    @Test
    fun `test delegate`() {
        val andesCarouselDelegate = object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                // Empty method
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = SIZE

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = LAYOUT
        }
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        carousel.delegate = andesCarouselDelegate
        Assert.assertNotNull(carousel.delegate)

        Assert.assertEquals(SIZE, andesCarouselDelegate.getDataSetSize(carousel))
        Assert.assertEquals(LAYOUT, andesCarouselDelegate.getLayoutItem(carousel))
    }

    @Test
    fun `test carousel with different padding`() {
        var carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.NONE)

        carousel = AndesCarousel(context, true, AndesCarouselMargin.DEFAULT)
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.DEFAULT)
    }

    @Test
    fun `test scroll carousel`() {
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val carousel = spy(AndesCarousel(context, true, AndesCarouselMargin.NONE))
        carousel.delegate = andesCarouselDelegate

        carousel.scrollToPosition(1)

        verify(carousel).scrollToPosition(1)
    }

    @Test
    fun `test scroll to position and layout infinite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // Empty method
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, INFINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        carousel.scrollToPosition(2)

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager
        setupActivityForTesting(carousel)

        verify(scroll, atLeastOnce()).onScrolled(any(), any(), any())
        assertTrue(layoutManager is AndesCarouselLayoutManager)
        assertTrue(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselInfiniteItemDecoration)
    }


    @Test
    fun `test scroll to position and layout finite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)

        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // Empty method
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, FINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        carousel.scrollToPosition(2)

        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager
        setupActivityForTesting(carousel)

        verify(scroll).onScrolled(any(), any(), any())
        assertTrue(layoutManager is AndesCarouselLayoutManager)
        assertFalse(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselFiniteItemDecoration)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test scroll horizontally to right and layout infinite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx = dx
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, INFINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        setupActivityForTesting(carousel)
        reflectionRecyclerView.scrollBy(10000, 0)

        verify(scroll, atLeastOnce()).onScrolled(any(), any(), any())
        assertEquals(10000, testDx)
        assertTrue(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselInfiniteItemDecoration)
    }

    @Test
    fun `test scroll horizontally to right and layout finite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx = dx
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, FINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        setupActivityForTesting(carousel)
        reflectionRecyclerView.scrollBy(10000, 0)

        verify(scroll, atLeastOnce()).onScrolled(any(), any(), any())
        assertEquals(0, testDx)
        assertFalse(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselFiniteItemDecoration)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test scroll horizontally to left and layout infinite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx = dx
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, INFINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        setupActivityForTesting(carousel)
        reflectionRecyclerView.scrollBy(-10000, 0)

        verify(scroll, atLeastOnce()).onScrolled(any(), any(), any())
        assertEquals(-10000, testDx)
        assertTrue(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselInfiniteItemDecoration)
    }

    @Test
    fun `test scroll horizontally to left and layout finite carousel`() {
        val getDataSetFree = getDataSetWithItems(2)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx = dx
            }
        })
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, FINITE, false, -1)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        setupActivityForTesting(carousel)
        reflectionRecyclerView.scrollBy(-10000, 0)

        verify(scroll, atLeastOnce()).onScrolled(any(), any(), any())
        assertEquals(0, testDx)
        assertFalse(layoutManager.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselFiniteItemDecoration)
    }

    @Test
    fun `given creation by xml with finite setted value, then finite getters return correct value`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselInfinite, "false")
        }

        val carousel = AndesCarousel(context, attrSet)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        assertFalse(layoutManager.isInfinite)
    }

    @Test
    fun `given creation by xml with default values, then carousel is finite`() {
        val attrSet = buildAttributeSet {
        }

        val carousel = AndesCarousel(context, attrSet)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        assertFalse(layoutManager.isInfinite)
    }

    private fun setupActivityForTesting(carousel: AndesCarousel): ActivityController<AppCompatActivity>? {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(carousel)
        robolectricActivity.start().postCreate(null).resume().visible()
        return robolectricActivity
    }

    @Test
    fun `test scrollToPosition carousel`() {
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        var recyclerViewMock: RecyclerView = mock()
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)

        carousel.scrollToPosition(10)

        verify(recyclerViewMock).scrollToPosition(10)
    }

    @Test
    fun `test smoothScrollToPosition carousel`() {
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        var recyclerViewMock: RecyclerView = mock()
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)

        carousel.smoothScrollToPosition(10)

        verify(recyclerViewMock).smoothScrollToPosition(10)
    }

    private fun createDelegate(dataSet: List<String>) : AndesCarouselDelegate {
        return object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                view.findViewById<TextView>(R.id.andes_carousel_text).text = dataSet[position]
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = dataSet.size

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andes_layout_test_carousel_item
        }
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test carousel is infinite scrolling carousel when accessibility is on and try to scrolling`() {
        val spyContext = spy(context)
        val mockAccManager: AccessibilityManager = mock()
        val getDataSetFree = getDataSetWithItems(8)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx = dx
            }
        }

        `when`(spyContext.getAccessibilityManager()).thenReturn(mockAccManager)
        `when`(mockAccManager.isEnabled()).thenReturn(true)
        val carousel = AndesCarousel(spyContext, true, AndesCarouselMargin.MEDIUM, INFINITE, false, -1)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManager = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager
        carousel.delegate = andesCarouselDelegate
        reflectionRecyclerView.scrollToPosition(8)
        carousel.addOnScrollListener(scroll)
        setupActivityForTesting(carousel)
        reflectionRecyclerView.scrollBy(1000000, 0)

        assertTrue(layoutManager.isInfinite)
        assertEquals(0, testDx)
    }

    @Test
    fun `given creation by xml with default values and infinite setted later, then carousel is infinite`() {
        val attrSet = buildAttributeSet {
        }

        val carousel = AndesCarousel(context, attrSet)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManagerBefore = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager
        carousel.infinite = true
        var layoutManagerAfter = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        assertTrue(layoutManagerAfter == layoutManagerBefore)
        assertTrue(layoutManagerBefore.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselInfiniteItemDecoration)
    }

    @Test
    fun `given creation by xml as infinite and finite setted later, then carousel is finite`() {
        val attrSet = buildAttributeSet {
            addAttribute(R.attr.andesCarouselInfinite, "true")
        }

        val carousel = AndesCarousel(context, attrSet)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        var layoutManagerBefore = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager
        carousel.infinite = false
        var layoutManagerAfter = reflectionRecyclerView.layoutManager as AndesCarouselLayoutManager

        assertTrue(layoutManagerAfter == layoutManagerBefore)
        assertFalse(layoutManagerBefore.isInfinite)
        assertTrue(reflectionRecyclerView.getItemDecorationAt(0) is AndesCarouselFiniteItemDecoration)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test carousel without autoplay, position should not change automatically`() = runBlockingTest {
        val getDataSetFree = getDataSetWithItems(2)
        var testDx = 0
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val scroll = spy(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                testDx += dx
            }
        })

        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, FINITE, STATIC, 10)
        carousel.delegate = andesCarouselDelegate
        carousel.addOnScrollListener(scroll)
        val andesCarouselAttrs =  ReflectionHelpers.getField<AndesCarouselAttrs>(carousel, "andesCarouselAttrs")
        val recyclerViewMock: RecyclerView = mock()
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)
        setupActivityForTesting(carousel)
        coroutinesTestRule.advanceTimeBy(12000)


        verify(recyclerViewMock, times(0)).smoothScrollToPosition(1)
        assertFalse(andesCarouselAttrs.andesCarouselAutoplay)
        assertFalse(carousel.autoplay)
        assertEquals(10, andesCarouselAttrs.andesCarouselAutoplaySpeed)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test created finite carousel without autoplay, and setted later position should not change automatically`() = runBlockingTest {
        val getDataSetFree = getDataSetWithItems(2)
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, FINITE, STATIC, 10)
        carousel.delegate = andesCarouselDelegate
        val recyclerView =  ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val recyclerViewMock: RecyclerView = spy(recyclerView)
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)
        carousel.autoplay = true
        val andesCarouselAttrs =  ReflectionHelpers.getField<AndesCarouselAttrs>(carousel, "andesCarouselAttrs")
        setupActivityForTesting(carousel)
        coroutinesTestRule.advanceTimeBy(12000)

        verify(recyclerViewMock, times(0))?.smoothScrollToPosition(eq(1))
        assertTrue(andesCarouselAttrs.andesCarouselAutoplay)
        assertTrue(carousel.autoplay)
        assertFalse(carousel.infinite)
        assertEquals(10, andesCarouselAttrs.andesCarouselAutoplaySpeed)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test created infinite carousel without autoplay, and setted later position should change automatically`() = runBlockingTest {
        val getDataSetFree = getDataSetWithItems(2)
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, INFINITE, STATIC, 10)
        carousel.delegate = andesCarouselDelegate
        val recyclerView =  ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val recyclerViewMock: RecyclerView = spy(recyclerView)
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)
        carousel.autoplay = true
        val andesCarouselAttrs =  ReflectionHelpers.getField<AndesCarouselAttrs>(carousel, "andesCarouselAttrs")
        setupActivityForTesting(carousel)
        coroutinesTestRule.advanceTimeBy(3000)

        verify(recyclerViewMock, atLeastOnce())?.smoothScrollToPosition(eq(1))
        assertTrue(andesCarouselAttrs.andesCarouselAutoplay)
        assertEquals(10, andesCarouselAttrs.andesCarouselAutoplaySpeed)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test carousel with autoplay, position should change automatically`() = runBlockingTest {
        val getDataSetFree = getDataSetWithItems(2)
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE, INFINITE, AUTOPLAY, 10)

        val recyclerView =  ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val recyclerViewMock: RecyclerView = spy(recyclerView)
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)
        val andesCarouselAttrs =  ReflectionHelpers.getField<AndesCarouselAttrs>(carousel, "andesCarouselAttrs")

        setupActivityForTesting(carousel)
        carousel.delegate = andesCarouselDelegate
        Thread.sleep(5000)

        verify(recyclerViewMock, atLeastOnce())?.smoothScrollToPosition(eq(1))
        assertTrue(andesCarouselAttrs.andesCarouselAutoplay)
        assertEquals(10, andesCarouselAttrs.andesCarouselAutoplaySpeed)
    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun `test created infinite carousel without autoplay and removed animations, and autoplay set later position should not change automatically`() = runBlockingTest {
        val getDataSetFree = getDataSetWithItems(2)
        val andesCarouselDelegate = createDelegate(getDataSetFree)
        val spyContext = spy(context)
        val carousel = AndesCarousel(spyContext, true, AndesCarouselMargin.NONE, INFINITE, AUTOPLAY, 10)
        val tempSettings = AndesCarouselAutoplayOn.a11ySettingsValidate
        AndesCarouselAutoplayOn.a11ySettingsValidate = mapOf("Setting 1" to 0.0f, "Setting 2" to 0.0f)
        carousel.delegate = andesCarouselDelegate
        val recyclerView =  ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val recyclerViewMock: RecyclerView = spy(recyclerView)
        ReflectionHelpers.setField(carousel, "recyclerViewComponent", recyclerViewMock)
        setupActivityForTesting(carousel)
        carousel.autoplaySpeed = 10
        carousel.autoplay = true
        val andesCarouselAttrs =  ReflectionHelpers.getField<AndesCarouselAttrs>(carousel, "andesCarouselAttrs")
        coroutinesTestRule.advanceTimeBy(3000)

        verify(recyclerViewMock, times(0))?.smoothScrollToPosition(eq(1))
        assertTrue(carousel.infinite)
        assertEquals(10, carousel.autoplaySpeed)
        assertTrue(andesCarouselAttrs.andesCarouselAutoplay)
        assertEquals(10, andesCarouselAttrs.andesCarouselAutoplaySpeed)
        AndesCarouselAutoplayOn.a11ySettingsValidate = tempSettings
    }

    private fun getDataSetWithItems(count: Int): List<String> = MutableList(count) {"test"}

    inner class MainCoroutineScopeRule(private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
        TestWatcher(),
        TestCoroutineScope by TestCoroutineScope(dispatcher) {
        override fun starting(description: Description?) {
            super.starting(description)
            // If your codebase allows the injection of other dispatchers like
            // Dispatchers.Default and Dispatchers.IO, consider injecting all of them here
            // and renaming this class to `CoroutineScopeRule`
            //
            // All injected dispatchers in a test should point to a single instance of
            // TestCoroutineDispatcher.
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            advanceUntilIdle()
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }

    companion object {
        private const val CENTER = true
        private const val FREE = false
        private const val FINITE = false
        private const val INFINITE = true
        private const val STATIC = false
        private const val AUTOPLAY = true
        private const val SIZE = 0
        private const val LAYOUT = 0
    }
}
