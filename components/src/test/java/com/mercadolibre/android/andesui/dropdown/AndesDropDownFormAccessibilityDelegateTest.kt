package com.mercadolibre.android.andesui.dropdown

import android.content.Context
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
import com.mercadolibre.android.andesui.dropdown.state.AndesDropdownState
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
class AndesDropDownFormAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesDropDownForm: AndesDropDownForm
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
        andesDropDownForm = AndesDropDownForm(
            context = context,
            label = "",
            helper = "",
            placeHolder = ""
        )
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesDropDownForm)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all parameters set and state error`() {
        val label = "label"
        val helper = "helper"
        val placeholder = "placeholder"
        val errorText = "Error"

        andesDropDownForm.label = label
        andesDropDownForm.helper = helper
        andesDropDownForm.placeholder = placeholder
        andesDropDownForm.state = AndesDropdownState.ERROR
        nodeInfo = andesDropDownForm.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. $errorText, $helper. $placeholder", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all parameters set and state different from error`() {
        val label = "label"
        val helper = "helper"
        val placeholder = "placeholder"

        andesDropDownForm.label = label
        andesDropDownForm.helper = helper
        andesDropDownForm.placeholder = placeholder
        andesDropDownForm.state = AndesDropdownState.ENABLED
        nodeInfo = andesDropDownForm.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. , $helper. $placeholder", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all parameters default from xml`() {
        val label = ""
        val helper = ""
        val placeholder = ""

        andesDropDownForm.label = null
        andesDropDownForm.helper = null
        andesDropDownForm.placeholder = null
        andesDropDownForm.state = AndesDropdownState.ENABLED
        nodeInfo = andesDropDownForm.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. , $helper. $placeholder", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly when the textfield state is readOnly`() {
        val label = "label"
        val helper = "helper"
        val placeholder = "placeholder"

        andesDropDownForm.label = label
        andesDropDownForm.helper = helper
        andesDropDownForm.placeholder = placeholder
        andesDropDownForm.state = AndesDropdownState.ENABLED
        andesDropDownForm.setReadOnly()
        nodeInfo = andesDropDownForm.createAccessibilityNodeInfo()

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. , . $placeholder", nodeInfo.contentDescription)
    }
}
