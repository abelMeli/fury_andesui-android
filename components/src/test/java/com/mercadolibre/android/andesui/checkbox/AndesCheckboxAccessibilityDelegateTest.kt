package com.mercadolibre.android.andesui.checkbox

import android.app.Activity
import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.accessibility.AndesCheckboxAccessibilityDelegate
import com.mercadolibre.android.andesui.checkbox.align.AndesCheckboxAlign
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.checkbox.type.AndesCheckboxType
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.actionLinkIdList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesCheckboxAccessibilityDelegateTest {

    private val defaultText: String = "Test"
    private lateinit var context: Context
    private lateinit var andesCheckbox: AndesCheckbox
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var a11yDelegate: AndesCheckboxAccessibilityDelegate

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesCheckbox = AndesCheckbox(context, defaultText)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(Activity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesCheckbox)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `contentDescription is correct for all values set`() {
        val text = "text"
        val statusText = context.resources.getString(R.string.andes_checkbox_status_selected)
        andesCheckbox.text = text
        andesCheckbox.status = AndesCheckboxStatus.SELECTED
        andesCheckbox.type = AndesCheckboxType.DISABLED
        andesCheckbox.align = AndesCheckboxAlign.LEFT
        nodeInfo = andesCheckbox.createAccessibilityNodeInfo()
        a11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertFalse(nodeInfo.isEnabled)
        Assert.assertEquals("$statusText, $text", nodeInfo.contentDescription)
    }

    @Test
    fun `contentDescription is correct for status value undefined`() {
        val statusText = context.resources.getString(R.string.andes_checkbox_status_undefined)
        andesCheckbox.type = AndesCheckboxType.IDLE
        andesCheckbox.status = AndesCheckboxStatus.UNDEFINED
        nodeInfo = andesCheckbox.createAccessibilityNodeInfo()
        a11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertTrue(nodeInfo.isEnabled)
        Assert.assertEquals("$statusText, $defaultText", nodeInfo.contentDescription)
    }

    @Test
    fun `contentDescription is correct for error value set`() {
        val statusText = context.resources.getString(R.string.andes_checkbox_status_unselected)
        val errorText = context.resources.getString(R.string.andes_checkbox_type_error)
        andesCheckbox.type = AndesCheckboxType.ERROR
        andesCheckbox.align = AndesCheckboxAlign.LEFT
        nodeInfo = andesCheckbox.createAccessibilityNodeInfo()
        a11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertTrue(nodeInfo.isEnabled)
        Assert.assertEquals("$errorText, $statusText, $defaultText", nodeInfo.contentDescription)
    }

    @Test
    fun `contentDescription is correct for all default values`() {
        val statusText = context.resources.getString(R.string.andes_checkbox_status_unselected)
        nodeInfo = andesCheckbox.createAccessibilityNodeInfo()
        a11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        a11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        Assert.assertNotNull(nodeInfo)
        Assert.assertTrue(nodeInfo.isEnabled)
        Assert.assertEquals("$statusText, $defaultText", nodeInfo.contentDescription)
    }

    @Test
    fun `verify action is correctly set for enabled selected checkbox`() {
        andesCheckbox.type = AndesCheckboxType.IDLE
        andesCheckbox.status = AndesCheckboxStatus.SELECTED
        nodeInfo = andesCheckbox.createAccessibilityNodeInfo()
        val spiedA11yDelegate = spy(andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate)

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        verify(spiedA11yDelegate, times(1)).addActionIfNeeded(nodeInfo, context.resources)
    }

    @Test
    fun `verify action is correctly set for disabled unselected checkbox`() {
        andesCheckbox.type = AndesCheckboxType.DISABLED
        nodeInfo = spy(andesCheckbox.createAccessibilityNodeInfo())
        val spiedA11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        verify(nodeInfo, times(1)).addAction(any<AccessibilityNodeInfo.AccessibilityAction>())
    }

    @Test
    fun `verify action is correctly set for error unselected checkbox`() {
        andesCheckbox.type = AndesCheckboxType.ERROR
        nodeInfo = spy(andesCheckbox.createAccessibilityNodeInfo())
        val spiedA11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        spiedA11yDelegate.onInitializeAccessibilityNodeInfo(andesCheckbox, nodeInfo)

        verify(nodeInfo, times(2)).addAction(any<AccessibilityNodeInfo.AccessibilityAction>())
    }

    @Test
    fun `testing a11y actions`() {
        andesCheckbox.text = "checkbox text with body links"
        val bodyLinks = AndesBodyLinks(
            listOf(
                AndesBodyLink(1, 4),
                AndesBodyLink(5, 9),
                AndesBodyLink(10, 11),
                AndesBodyLink(12, 13)
            )
        ) { /* not used*/ }
        andesCheckbox.bodyLinks = bodyLinks
        val actionClickFirstLinkId = actionLinkIdList[0]
        val actionClickSecondLinkId = actionLinkIdList[1]
        val actionClickThirdLinkId = actionLinkIdList[2]
        val actionClickFourthLinkId = actionLinkIdList[3]
        val actionDifferentId = AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS

        val a11yDelegate = andesCheckbox.accessibilityDelegate as AndesCheckboxAccessibilityDelegate

        andesCheckbox.createAccessibilityNodeInfo()
        val firstActionPerformed = a11yDelegate.performAccessibilityAction(andesCheckbox, actionClickFirstLinkId, null)
        val secondActionPerformed = a11yDelegate.performAccessibilityAction(andesCheckbox, actionClickSecondLinkId, null)
        val thirdActionPerformed = a11yDelegate.performAccessibilityAction(andesCheckbox, actionClickThirdLinkId, null)
        val fourthActionPerformed = a11yDelegate.performAccessibilityAction(andesCheckbox, actionClickFourthLinkId, null)
        val differentActionPerformed = a11yDelegate.performAccessibilityAction(andesCheckbox, actionDifferentId, null)

        assertTrue(firstActionPerformed)
        assertTrue(secondActionPerformed)
        assertTrue(thirdActionPerformed)
        assertTrue(fourthActionPerformed)
        assertFalse(differentActionPerformed)
    }
}
