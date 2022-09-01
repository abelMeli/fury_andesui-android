package com.mercadolibre.android.andesui.textview

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLink
import com.mercadolibre.android.andesui.message.bodylinks.AndesBodyLinks
import com.mercadolibre.android.andesui.textfield.AndesTextfield
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import com.mercadolibre.android.andesui.utils.actionLinkIdList
import com.mercadolibre.android.andesui.utils.toA11yAction
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesTextViewA11yDelegateTest {

    private lateinit var context: Context
    private lateinit var activity: AppCompatActivity
    private lateinit var robolectricActivity: ActivityController<AppCompatActivity>
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var andesTextView: AndesTextView
    private lateinit var andesTextfield: AndesTextfield
    private lateinit var andesAmountField: AndesAmountFieldSimple
    private lateinit var imageView: ImageView
    private lateinit var containerLayout: LinearLayout

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        createActivity()
        MockConfigProvider.configure()
        setupFresco()
        setupComponents()
        setupActivityForTesting()
    }

    private fun createActivity() {
        robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        this.activity = activity
    }

    private fun setupComponents() {
        containerLayout = LinearLayout(activity)
        andesTextView = AndesTextView(activity).apply {
            id = View.generateViewId()
        }
        andesTextfield = AndesTextfield(activity).apply {
            id = View.generateViewId()
        }
        andesAmountField = AndesAmountFieldSimple(activity).apply {
            id = View.generateViewId()
        }
        imageView = ImageView(activity).apply {
            id = View.generateViewId()
        }
        containerLayout.apply {
            addView(andesTextView)
            addView(andesTextfield)
            addView(andesAmountField)
            addView(imageView)
        }
    }

    private fun setupFresco() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    private fun setupActivityForTesting() {
        activity.setContentView(containerLayout)
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

    @Test
    fun `given textview, when it is not set as label, then referenced view does not change`() {
        val referencedViewId = andesTextView.labelFor

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        referencedViewId assertEquals View.NO_ID
        andesTextView.labelFor assertEquals View.NO_ID
    }

    @Test
    fun `given textview, when set as label for a textfield, then referenced view is moved to internal edittext`() {
        andesTextView.apply {
            text = "a11y label"
            labelFor = andesTextfield.id
        }

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        andesTextView.labelFor assertEquals andesTextfield.getInternalEditText().id
    }

    @Test
    fun `given textview, when set as label for an amountfield, then referenced view is moved to internal edittext`() {
        andesTextView.apply {
            text = "a11y label"
            labelFor = andesAmountField.id
        }

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        andesTextView.labelFor assertEquals andesAmountField.getInternalEditText().id
    }

    @Test
    fun `given textview, when set as label for a regular image, then referenced view remains the same`() {
        andesTextView.apply {
            text = "a11y label"
            labelFor = imageView.id
        }

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        andesTextView.labelFor assertEquals imageView.id
    }

    @Test
    fun `given textview created with applicationContext, when set as label, then referenced view remains the same`() {
        val newTextView = AndesTextView(context).apply {
                text = "a11y label"
                labelFor = andesAmountField.id
            }

        nodeInfo = newTextView.createAccessibilityNodeInfo()

        newTextView.labelFor assertEquals andesAmountField.id
    }

    @Test
    fun `given textview, when set as label for a component not attached to screen, then referenced view remains the same`() {
        val newAmountField = AndesAmountFieldSimple(context).apply {
            id = View.generateViewId()
        }
        andesTextView.apply {
            text = "a11y label"
            labelFor = newAmountField.id
        }

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        andesTextView.labelFor assertEquals newAmountField.id
    }

    @Test
    fun `given textview, when set as label for itself, then referenced view remains the same`() {
        andesTextView.apply {
            text = "a11y label"
            labelFor = id
        }

        nodeInfo = andesTextView.createAccessibilityNodeInfo()

        andesTextView.labelFor assertEquals andesTextView.id
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
