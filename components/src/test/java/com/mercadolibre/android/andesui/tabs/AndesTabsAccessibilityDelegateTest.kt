package com.mercadolibre.android.andesui.tabs

import android.app.Activity
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
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
class AndesTabsAccessibilityDelegateTest {

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
        val robolectricActivity = Robolectric.buildActivity(Activity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_MaterialComponents_NoActionBar_Bridge)
        andesTabs = AndesTabs(activity, tabItems)
        activity.setContentView(andesTabs)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesTabs list accessibility`() {
        val nodeInfo = andesTabs.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertNotNull(nodeInfo.collectionInfo)
        Assert.assertEquals(tabItems.size, nodeInfo.collectionInfo.columnCount)
        Assert.assertEquals(1, nodeInfo.collectionInfo.rowCount)
        Assert.assertEquals(
            AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_SINGLE,
            nodeInfo.collectionInfo.selectionMode
        )
    }
}
