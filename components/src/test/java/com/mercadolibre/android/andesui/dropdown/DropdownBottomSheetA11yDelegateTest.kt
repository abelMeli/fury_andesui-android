package com.mercadolibre.android.andesui.dropdown

import android.content.Context
import android.os.Looper.getMainLooper
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.dropdown.utils.DropdownBottomSheetDialog
import com.mercadolibre.android.andesui.list.AndesList
import com.mercadolibre.android.andesui.list.AndesListViewItem
import com.mercadolibre.android.andesui.list.utils.AndesListDelegate
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
class DropdownBottomSheetA11yDelegateTest {

    private lateinit var context: Context
    private lateinit var bottomSheet: DropdownBottomSheetDialog
    private val theme = R.style.Andes_BottomSheetDialog
    private lateinit var triggerButton: Button
    private var nodeInfo: AccessibilityNodeInfo? = null

    companion object {
        private const val EMPTY_LIST_LENGHT = 0
    }

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
        bottomSheet = DropdownBottomSheetDialog(
            context,
            theme,
            createCustomDelegate()
        )
        triggerButton = Button(context)
        setupActivityForTest()
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(triggerButton)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `actions in nodeInfo are correct for versions newer than API 21`() {
        triggerButton.setOnClickListener {
            bottomSheet.show()
        }
        triggerButton.performClick()
        val bottomSheetLayout = bottomSheet.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        shadowOf(getMainLooper()).idle()

        nodeInfo = bottomSheetLayout?.createAccessibilityNodeInfo()
        val actionList = nodeInfo?.actionList
        val containsDismiss = actionList?.contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS)
        val containsExpand = actionList?.contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND)

        Assert.assertNotNull(nodeInfo)
        containsDismiss?.let {
            Assert.assertTrue(it)
        }
        containsExpand?.let {
            Assert.assertTrue(it)
        }
    }

    @Test
    fun `performing actions on bottomSheet`() {
        triggerButton.setOnClickListener {
            bottomSheet.show()
        }
        triggerButton.performClick()
        val bottomSheetLayout = bottomSheet.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        shadowOf(getMainLooper()).idle()
        nodeInfo = bottomSheetLayout?.createAccessibilityNodeInfo()

        val actionExpand = bottomSheetLayout?.performAccessibilityAction(AccessibilityNodeInfo.ACTION_EXPAND, null)
        val actionDismiss = bottomSheetLayout?.performAccessibilityAction(AccessibilityNodeInfo.ACTION_DISMISS, null)
        val actionFocus = bottomSheetLayout?.performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)

        Assert.assertNotNull(nodeInfo)
        Assert.assertNotNull(actionExpand)
        Assert.assertNotNull(actionDismiss)
        Assert.assertNotNull(actionFocus)
        actionExpand?.let {
            Assert.assertTrue(it)
        }

        actionDismiss?.let {
            Assert.assertTrue(it)
        }

        actionFocus?.let {
            Assert.assertFalse(it)
        }
    }

    private fun createCustomDelegate() = object : AndesListDelegate {
        override fun onItemClick(andesList: AndesList, position: Int) {
            // no-op
        }

        override fun bind(andesList: AndesList, view: View, position: Int): AndesListViewItem {
            return AndesListViewItem()
        }

        override fun getDataSetSize(andesList: AndesList): Int {
            return EMPTY_LIST_LENGHT
        }
    }
}
