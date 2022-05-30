package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.actionLinkIdList
import com.mercadolibre.android.andesui.utils.toA11yAction
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTextViewA11yDelegateTest {

    private lateinit var context: Context
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var andesTextView: AndesTextView

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesTextView = AndesTextView(context)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(andesTextView)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `custom link actions correctly set with api less than 26`() {
        andesTextView.text = "Text to test accessibility"
        andesTextView.bodyLinks = getBodyLinksForTest()

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        val actionList = nodeInfo.actionList
        val firstLinkAction = actionLinkIdList[0].toA11yAction("Link disponible: Text")
        val secondLinkAction = actionLinkIdList[1].toA11yAction("Some text")
        val containsFirstLinkAction = actionList.contains(firstLinkAction)
        val containsSecondLinkAction = actionList.contains(secondLinkAction)

        Assert.assertTrue(containsFirstLinkAction)
        Assert.assertFalse(containsSecondLinkAction)
    }

    @Test
    fun `performing link actions`() {
        andesTextView.text = "Text to test accessibility"
        andesTextView.bodyLinks = getBodyLinksForTest()
        val firstLinkAction = actionLinkIdList[0].toA11yAction("Link disponible: Text")
        val otherAction = AccessibilityNodeInfo.ACTION_EXPAND
        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        val actionPerformed = andesTextView.performAccessibilityAction(firstLinkAction.id, null)
        val actionNotPerformed = andesTextView.performAccessibilityAction(otherAction, null)

        actionPerformed assertEquals true
        actionNotPerformed assertEquals false
    }

    private fun getBodyLinksForTest(): AndesBodyLinks {
        return AndesBodyLinks(
            links = listOf(
                AndesBodyLink(0, 4)
            ),
            listener = { }
        )
    }
}
