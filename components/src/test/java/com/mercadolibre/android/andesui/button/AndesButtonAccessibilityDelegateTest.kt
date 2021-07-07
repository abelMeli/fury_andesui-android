package com.mercadolibre.android.andesui.button

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.accessibility.AndesButtonAccessibilityDelegate
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesButtonAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesButton: AndesButton
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var a11yDelegate: AndesButtonAccessibilityDelegate

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
        andesButton = spy(AndesButton(context))
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesButton)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `AndesButton accessibility when the button is not charging`() {
        val nodeInfo = andesButton.createAccessibilityNodeInfo()
        a11yDelegate = andesButton.accessibilityDelegate as AndesButtonAccessibilityDelegate
        a11yDelegate.onInitializeAccessibilityNodeInfo(andesButton, nodeInfo)
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Button text.", nodeInfo.contentDescription)
    }

    @Test
    fun `Andes button accessibility when the button is charging`() {
        doNothing().`when`(andesButton).startLoadingAnimation()
        andesButton.isLoading = true

        nodeInfo = andesButton.createAccessibilityNodeInfo()

        a11yDelegate = andesButton.accessibilityDelegate as AndesButtonAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesButton, nodeInfo)

        Log.v("TEST_AB", nodeInfo.contentDescription.toString())

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("Button text, Cargando.", nodeInfo.contentDescription)
    }

}
