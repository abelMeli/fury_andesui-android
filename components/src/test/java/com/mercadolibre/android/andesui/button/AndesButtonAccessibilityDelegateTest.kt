package com.mercadolibre.android.andesui.button

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
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
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesButtonAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesButton: AndesButton

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
        andesButton = AndesButton(context)
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
        val buttonText = "Button text"
        andesButton.text = buttonText
        val nodeInfo = andesButton.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$buttonText.", nodeInfo.contentDescription)
    }

    @Test
    fun `Andes button accessibility when the button is charging`() {
        val buttonText = "Button text"
        val loadingText = context.resources.getString(R.string.andes_button_loading)
        andesButton.text = buttonText
        andesButton.isLoading = true
        val nodeInfo = andesButton.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$buttonText, $loadingText.", nodeInfo.contentDescription)
    }

    @Test
    fun `Andes button accessibility when the button is charging and has no text`() {
        val loadingText = context.resources.getString(R.string.andes_button_loading)
        andesButton.text = null
        andesButton.isLoading = true
        val nodeInfo = andesButton.createAccessibilityNodeInfo()
        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(", $loadingText.", nodeInfo.contentDescription)
    }
}
