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
class AndesCarouselViewItemAccessibilityDelegateTest {
    private lateinit var context: Context
    private lateinit var andesCarousel: AndesCarousel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        val getDataSetFree = getDataSetWithItems(2)
        andesCarousel = AndesCarousel(activity, true)
        val andesCarouselDelegate = object : AndesCarouselDelegate {
            override fun bind(andesCarouselView: AndesCarousel, view: View, position: Int) {
                view.findViewById<TextView>(R.id.andes_carousel_text).text = getDataSetFree[position]
            }

            override fun onClickItem(andesCarouselView: AndesCarousel, position: Int) {
                // Empty method
            }

            override fun getDataSetSize(andesCarouselView: AndesCarousel) = getDataSetFree.size

            override fun getLayoutItem(andesCarouselView: AndesCarousel) = R.layout.andes_layout_test_carousel_item
        }
        andesCarousel.delegate = andesCarouselDelegate

        activity.setContentView(andesCarousel)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesCarousel  item accessibility selected`() {
        val itemNodeInfo = createAccessibilityNodeInfoForItemAt(0)

        Assert.assertNotNull(itemNodeInfo)
        Assert.assertNotNull(itemNodeInfo?.collectionItemInfo)
        itemNodeInfo?.collectionItemInfo?.isSelected?.let { Assert.assertFalse(it) }
        Assert.assertEquals(0, itemNodeInfo?.collectionItemInfo?.rowIndex)
        Assert.assertEquals(null, itemNodeInfo?.let { AccessibilityNodeInfoCompat.wrap(it).contentDescription })
    }

    @Test
    fun `AndesCarousel  item accessibility unselected`() {
        val itemNodeInfo = createAccessibilityNodeInfoForItemAt(1)

        Assert.assertNotNull(itemNodeInfo)
        Assert.assertNotNull(itemNodeInfo?.collectionItemInfo)
        itemNodeInfo?.collectionItemInfo?.isSelected?.let { Assert.assertFalse(it) }
        Assert.assertEquals(1, itemNodeInfo?.collectionItemInfo?.rowIndex)
        Assert.assertEquals(null, itemNodeInfo?.let { AccessibilityNodeInfoCompat.wrap(it).contentDescription })
    }

    private fun createAccessibilityNodeInfoForItemAt(index: Int): AccessibilityNodeInfo? {
        val recyclerView = andesCarousel.findViewById<RecyclerView>(R.id.andes_carousel_recyclerview)
        return recyclerView.layoutManager?.findViewByPosition(index)?.createAccessibilityNodeInfo()
    }

    private fun getDataSetWithItems(count: Int): List<String> = MutableList(count) { "test" }
}
