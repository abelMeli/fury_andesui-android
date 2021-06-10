package com.mercadolibre.android.andesui.textfield

import android.content.Context
import android.os.Build
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
import com.mercadolibre.android.andesui.textfield.accessibility.AndesTextfieldAccessibilityDelegate
import com.mercadolibre.android.andesui.textfield.content.AndesTextfieldLeftContent
import com.mercadolibre.android.andesui.textfield.state.AndesTextfieldState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesTextfieldAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var textfield: AndesTextfield
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var a11yDelegate: AndesTextfieldAccessibilityDelegate

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
        textfield = AndesTextfield(context)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(textfield)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with state idle, all components with value`() {
        val label = "label"
        val helper = "helper"
        val prefix = "prefix"
        val counter = 10
        val counterText = "máximo número de caracteres: 10"
        val placeholder = "placeholder"
        textfield.label = label
        textfield.helper = helper
        textfield.leftContent = AndesTextfieldLeftContent.PREFIX
        textfield.setPrefix(prefix)
        textfield.counter = counter
        textfield.placeholder = placeholder
        nodeInfo = textfield.createAccessibilityNodeInfo()
        a11yDelegate = textfield.accessibilityDelegate as AndesTextfieldAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfield, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label, $helper, $counterText. $prefix, $placeholder", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with state idle, all components with no value`() {
        nodeInfo = textfield.createAccessibilityNodeInfo()
        a11yDelegate = textfield.accessibilityDelegate as AndesTextfieldAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfield, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(", , . , ", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with state idle, with entered text, with counter`() {
        textfield.text = "entered text"
        textfield.counter = 30
        val counterText = "Caracteres ingresados: ${textfield.text?.length} de ${textfield.counter}"
        nodeInfo = textfield.createAccessibilityNodeInfo()
        a11yDelegate = textfield.accessibilityDelegate as AndesTextfieldAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfield, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals(", , $counterText. , ", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with state readonly`() {
        val label = "label"
        val helper = "helper"
        val prefix = "prefix"
        val counter = 10
        val placeholder = "placeholder"
        textfield.label = label
        textfield.helper = helper
        textfield.setPrefix(prefix)
        textfield.counter = counter
        textfield.placeholder = placeholder
        textfield.state = AndesTextfieldState.READONLY
        nodeInfo = textfield.createAccessibilityNodeInfo()
        a11yDelegate = textfield.accessibilityDelegate as AndesTextfieldAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfield, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label. $prefix, $placeholder", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }

    @Test
    fun `onInitializeAccessibilityNodeInfo works correctly with state error`() {
        val label = "label"
        val helper = "helper"
        val prefix = "prefix"
        val counter = 10
        val counterText = "máximo número de caracteres: $counter"
        val placeholder = "placeholder"
        val errorText = "Error"
        textfield.label = label
        textfield.helper = helper
        textfield.setPrefix(prefix)
        textfield.counter = counter
        textfield.placeholder = placeholder
        textfield.state = AndesTextfieldState.ERROR
        nodeInfo = textfield.createAccessibilityNodeInfo()
        a11yDelegate = textfield.accessibilityDelegate as AndesTextfieldAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(textfield, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$label, $errorText, $helper, $counterText. $prefix, $placeholder", nodeInfo.contentDescription)
        Assert.assertNotEquals("other text", nodeInfo.contentDescription)
    }
}
