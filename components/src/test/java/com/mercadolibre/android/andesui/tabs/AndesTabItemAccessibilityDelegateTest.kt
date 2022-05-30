package com.mercadolibre.android.andesui.tabs

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.google.android.material.tabs.TabLayout
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTabItemAccessibilityDelegateTest {

    private val tabItems: List<AndesTabItem> = listOf(
        AndesTabItem("First"),
        AndesTabItem("Second")
    )
    private lateinit var context: Context
    private lateinit var andesTabs: AndesTabs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        andesTabs = AndesTabs(activity, tabItems)
        activity.setContentView(andesTabs)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesTab list item accessibility selected`() {
        val itemNodeInfo = createAccessibilityNodeInfoForItemAt(0)

        Assert.assertNotNull(itemNodeInfo)
        Assert.assertNotNull(itemNodeInfo.collectionItemInfo)
        Assert.assertTrue(itemNodeInfo.collectionItemInfo.isSelected)
        Assert.assertFalse(itemNodeInfo.isClickable)
        Assert.assertEquals(0, itemNodeInfo.collectionItemInfo.columnIndex)
        Assert.assertEquals("Tab", AccessibilityNodeInfoCompat.wrap(itemNodeInfo).roleDescription)
        Assert.assertEquals("First", AccessibilityNodeInfoCompat.wrap(itemNodeInfo).contentDescription)
    }

    @Test
    fun `AndesTab list item accessibility unselected`() {
        val itemNodeInfo = createAccessibilityNodeInfoForItemAt(1)

        Assert.assertNotNull(itemNodeInfo)
        Assert.assertNotNull(itemNodeInfo.collectionItemInfo)
        Assert.assertFalse(itemNodeInfo.collectionItemInfo.isSelected)
        Assert.assertTrue(itemNodeInfo.isClickable)
        Assert.assertEquals(1, itemNodeInfo.collectionItemInfo.columnIndex)
        Assert.assertEquals("Tab", AccessibilityNodeInfoCompat.wrap(itemNodeInfo).roleDescription)
        Assert.assertEquals("Second", AccessibilityNodeInfoCompat.wrap(itemNodeInfo).contentDescription)
    }

    private fun createAccessibilityNodeInfoForItemAt(index: Int): AccessibilityNodeInfo {
        val tabLayout = andesTabs.findViewById<TabLayout>(R.id.andes_tabs_tab_layout)
        return (tabLayout.getTabAt(index)?.customView?.parent as? View)?.createAccessibilityNodeInfo()!!
    }
}
