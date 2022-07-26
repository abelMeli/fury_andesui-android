package com.mercadolibre.android.andesui.carousel

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.carousel.utils.AndesCarouselDelegate
import com.mercadolibre.android.andesui.utils.Constants
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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

    private fun createAccessibilityNodeInfoForCarousel(): AccessibilityNodeInfo {
        val recyclerView = andesCarousel.findViewById<RecyclerView>(R.id.andes_carousel_recyclerview)
        return recyclerView.createAccessibilityNodeInfo()
    }

    private fun getDataSetWithItems(count: Int): List<String> = MutableList(count) { "test" }
}
