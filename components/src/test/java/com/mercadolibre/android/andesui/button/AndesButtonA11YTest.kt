package com.mercadolibre.android.andesui.button

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesButtonA11YTest {

    private lateinit var context: Context
    private lateinit var andesButton: AndesButton
    private lateinit var nodeInfo: AccessibilityNodeInfo

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `andes button correct role description`() {
        andesButton = AndesButton(context)
        val roleDescription = Button::class.java.name
        assertEquals(roleDescription, andesButton.accessibilityClassName)
    }

    @Test
    fun `verify that info content description is set for accessibility`() {
        val activity = Activity()
        val robolectricActivity = Robolectric.setupActivity(activity::class.java)
        val buttonText = "button text"
        andesButton = AndesButton(context = context, buttonText = buttonText)
        andesButton.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                TODO("Not yet implemented")
            }

            override fun onViewAttachedToWindow(p0: View?) {
                nodeInfo = andesButton.createAccessibilityNodeInfo()
                andesButton.onInitializeAccessibilityNodeInfo(nodeInfo)
                assertEquals(buttonText, nodeInfo.contentDescription)
            }
        })
        robolectricActivity.setContentView(andesButton)
    }
}
