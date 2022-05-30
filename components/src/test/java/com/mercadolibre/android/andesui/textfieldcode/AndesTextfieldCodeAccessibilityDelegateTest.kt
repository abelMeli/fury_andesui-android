package com.mercadolibre.android.andesui.textfieldcode

import android.content.Context
import android.view.View
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
import com.mercadolibre.android.andesui.textfield.AndesTextfieldCode
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldCodeState
import com.mercadolibre.android.andesui.textfield.style.AndesTextfieldCodeStyle
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
class AndesTextfieldCodeAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var textfieldCode: AndesTextfieldCode
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var a11yDelegate: View.AccessibilityDelegate

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
        textfieldCode = AndesTextfieldCode(context)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(textfieldCode)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all atributes set and state IDLE`() {
        val label = "label"
        val helper = "helper"
        val enteredText = "1"
        val counterText = "Caracteres ingresados: 1 de 6"
        textfieldCode.style = AndesTextfieldCodeStyle.THREE_BY_THREE
        textfieldCode.label = label
        textfieldCode.helper = helper
        textfieldCode.text = enteredText
        nodeInfo = textfieldCode.createAccessibilityNodeInfo()
        a11yDelegate = textfieldCode.accessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfieldCode, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label $helper. $enteredText, $counterText", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all atributes by default`() {
        val label = ""
        val helper = ""
        val enteredText = ""
        val counterText = "Caracteres ingresados: 0 de 3"
        nodeInfo = textfieldCode.createAccessibilityNodeInfo()
        a11yDelegate = textfieldCode.accessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfieldCode, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label $helper. $enteredText, $counterText", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all atributes set and state ERROR`() {
        val label = "label"
        val helper = "helper"
        val enteredText = "1"
        val counterText = "Caracteres ingresados: 1 de 4"
        val errorText = "Error"
        textfieldCode.style = AndesTextfieldCodeStyle.FOURSOME
        textfieldCode.label = label
        textfieldCode.helper = helper
        textfieldCode.text = enteredText
        textfieldCode.state = AndesTextfieldCodeState.ERROR
        nodeInfo = textfieldCode.createAccessibilityNodeInfo()
        a11yDelegate = textfieldCode.accessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfieldCode, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. $errorText, $helper. $enteredText. $counterText", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with all atributes set and state DISABLED`() {
        val label = "label"
        val helper = "helper"
        val enteredText = "1"
        textfieldCode.label = label
        textfieldCode.helper = helper
        textfieldCode.text = enteredText
        textfieldCode.state = AndesTextfieldCodeState.DISABLED
        nodeInfo = textfieldCode.createAccessibilityNodeInfo()
        a11yDelegate = textfieldCode.accessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfieldCode, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label, $helper.", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }
}
