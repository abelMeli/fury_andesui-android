package com.mercadolibre.android.andesui.switch

import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switch.accessibility.AndesSwitchAccessibilityDelegate
import com.mercadolibre.android.andesui.switch.align.AndesSwitchAlign
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSwitchAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var andesSwitch: AndesSwitch
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var a11yDelegate: AndesSwitchAccessibilityDelegate

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesSwitch = AndesSwitch(context)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesSwitch)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `contentDescription is correct for all values set`() {
        val text = "text"
        val statusText = context.resources.getString(R.string.andes_switch_status_checked)
        andesSwitch.text = text
        andesSwitch.status = AndesSwitchStatus.CHECKED
        andesSwitch.type = AndesSwitchType.DISABLED
        andesSwitch.align = AndesSwitchAlign.LEFT
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        a11yDelegate = andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$text. $statusText", nodeInfo.text)
    }

    @Test
    fun `contentDescription is correct for all default values`() {
        val text = ""
        val statusText = context.resources.getString(R.string.andes_switch_status_unchecked)
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        a11yDelegate = andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertEquals("$text. $statusText", nodeInfo.text)
    }

    @Test
    fun `verify action is correctly set for enabled unchecked switch`() {
        andesSwitch.type = AndesSwitchType.ENABLED
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        val spiedA11yDelegate = spy(andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate)

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        verify(spiedA11yDelegate, times(1)).addActionIfNeeded(nodeInfo, andesSwitch)
    }

    @Test
    fun `verify action is correctly set for enabled checked switch`() {
        andesSwitch.type = AndesSwitchType.ENABLED
        andesSwitch.status = AndesSwitchStatus.CHECKED
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        val spiedA11yDelegate = spy(andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate)

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        verify(spiedA11yDelegate, times(1)).addActionIfNeeded(nodeInfo, andesSwitch)
    }

    @Test
    fun `verify action is correctly set for versions below lollipop`() {
        ReflectionHelpers.setStaticField(Build.VERSION::class.java, "SDK_INT", 19)
        andesSwitch.type = AndesSwitchType.ENABLED
        andesSwitch.status = AndesSwitchStatus.CHECKED
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        val spiedA11yDelegate = spy(andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate)

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        verify(spiedA11yDelegate, times(1)).addActionIfNeeded(nodeInfo, andesSwitch)
    }

    @Test
    fun `verify action is not set for disabled switch`() {
        andesSwitch.type = AndesSwitchType.DISABLED
        nodeInfo = andesSwitch.createAccessibilityNodeInfo()
        val spiedA11yDelegate = spy(andesSwitch.accessibilityDelegate as AndesSwitchAccessibilityDelegate)

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesSwitch, nodeInfo)

        verify(spiedA11yDelegate, times(1)).addActionIfNeeded(nodeInfo, andesSwitch)
    }
}
