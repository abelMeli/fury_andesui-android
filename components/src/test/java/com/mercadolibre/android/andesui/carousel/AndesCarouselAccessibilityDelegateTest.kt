package com.mercadolibre.android.andesui.carousel

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.carousel.accessibility.AndesCarouselAccessibilityDelegate
import com.mercadolibre.android.andesui.carousel.margin.AndesCarouselMargin
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.assertEquals
import com.nhaarman.mockitokotlin2.any
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesCarouselAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesCarousel: AndesCarousel
    private val carouselItems = getDataSetWithItems(2)

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        andesCarousel = AndesCarousel(activity, true)
        val andesCarouselDelegate = object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                view.findViewById<TextView>(R.id.andes_carousel_text).text = carouselItems[position]
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = carouselItems.size

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andes_layout_test_carousel_item
        }
        andesCarousel.delegate = andesCarouselDelegate

        activity.setContentView(andesCarousel)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesCarousel list accessibility`() {
        val nodeInfo = createAccessibilityNodeInfoForCarousel()
        Assert.assertNotNull(nodeInfo)
        Assert.assertNotNull(nodeInfo.collectionInfo)
        Assert.assertEquals(1, nodeInfo.collectionInfo.columnCount)
        Assert.assertEquals(carouselItems.size, nodeInfo.collectionInfo.rowCount)
        Assert.assertEquals(
            AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_SINGLE,
            nodeInfo.collectionInfo.selectionMode
        )
    }

    @Test
    fun `test carousel a11y onInitializeAccessibilityNodeInfo when delegate has not been initialized`() {
        var carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.NONE)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val a11yDelegate = (reflectionRecyclerView.compatAccessibilityDelegate as AndesCarouselAccessibilityDelegate)
        a11yDelegate.onInitializeAccessibilityNodeInfo(carousel, AccessibilityNodeInfoCompat.wrap(carousel.createAccessibilityNodeInfo()))
        var size = ReflectionHelpers.getField<(() -> Int)>(a11yDelegate, "size")
        assertNotNull(reflectionRecyclerView.accessibilityDelegate)
        0 assertEquals(size())
    }

    @Test
    fun `test carousel a11y onInitializeAccessibilityNodeInfo when delegate has been initialized`() {
        var carousel = AndesCarousel(context, true, AndesCarouselMargin.NONE)
        val SIZE = 8
        val dataSet = getDataSetWithItems(SIZE)
        val delegate = createDelegate(dataSet)
        carousel.delegate = delegate
        Assert.assertEquals(carousel.margin, AndesCarouselMargin.NONE)
        var reflectionRecyclerView = ReflectionHelpers.getField<RecyclerView>(carousel, "recyclerViewComponent")
        val a11yDelegate = (reflectionRecyclerView.compatAccessibilityDelegate as AndesCarouselAccessibilityDelegate)
        a11yDelegate.onInitializeAccessibilityNodeInfo(carousel, AccessibilityNodeInfoCompat.wrap(carousel.createAccessibilityNodeInfo()))
        var size = ReflectionHelpers.getField<(() -> Int)>(a11yDelegate, "size")
        assertNotNull(reflectionRecyclerView.accessibilityDelegate)
        SIZE assertEquals(size())
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

    private fun createAccessibilityNodeInfoForCarousel(): AccessibilityNodeInfo {
        val recyclerView = andesCarousel.findViewById<RecyclerView>(R.id.andes_carousel_recyclerview)
        return recyclerView.createAccessibilityNodeInfo()
    }

    private fun getDataSetWithItems(count: Int): List<String> = MutableList(count) { "test" }
}
