package com.mercadolibre.android.andesui.modal.full.fragment

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupCreator
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalInterface
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getCloseButtonView
import com.mercadolibre.android.andesui.utils.getCustomViewComponent
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
class AndesModalFullCustomViewFragmentTest {

    private lateinit var robolectricActivity: ActivityController<FragmentActivity>
    private lateinit var activity: FragmentActivity
    private lateinit var modal: AndesModalFullCustomViewFragment
    private lateinit var context: Context

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        SoLoader.setInTestMode()
        robolectricActivity = Robolectric.buildActivity(FragmentActivity::class.java).create()
        activity = robolectricActivity.get()
        context = activity.applicationContext
        activity.setContentView(
            FrameLayout(context).apply {
                id = R.id.content_frame
            }
        )
        robolectricActivity.start().postCreate(null).resume().visible()
        setupFresco()
    }

    private fun setupFresco() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `modal full default with all default values`() {
        val customView = View(context)
        modal = AndesModal.fullBuilder(customView).build()

        modal.show(activity, R.id.content_frame)

        modal.isVisible assertEquals true
        modal.getCustomViewComponent() assertEquals customView
        modal.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal full default with all values set`() {
        val customView = View(context)
        val buttonGroupData = AndesButtonGroupData(AndesButtonGroup(context, listOf(AndesButton(context))))
        val buttonGroupCreator = object : AndesButtonGroupCreator {
            override fun create(modalInterface: AndesModalInterface) = buttonGroupData
        }
        modal = AndesModal.fullBuilder(customView)
            .withOnDismissCallback { /* no-op */ }
            .withIsDismissible(false)
            .withButtonGroupCreator(buttonGroupCreator)
            .withOnModalShowCallback { /* no-op */ }
            .build() as AndesModalFullCustomViewFragment

        modal.show(activity, R.id.content_frame)

        modal.isVisible assertEquals true
        modal.getCustomViewComponent() assertEquals customView
        modal.getButtonGroupComponent() assertEquals buttonGroupData.buttonGroup
    }

    @Test
    fun `given modal with show callback, when showing modal, then callback is called`() {
        val customView = View(context)
        var testBoolean = false
        modal = AndesModal.fullBuilder(customView)
            .withOnModalShowCallback { testBoolean = true }
            .build() as AndesModalFullCustomViewFragment

        modal.show(activity, R.id.content_frame)

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with dismiss callback, when dismissing modal, then callback is called`() {
        val customView = View(context)
        var testBoolean = false
        modal = AndesModal.fullBuilder(customView)
            .withOnDismissCallback { testBoolean = true }
            .build() as AndesModalFullCustomViewFragment

        modal.show(activity, R.id.content_frame)
        modal.dismiss()

        testBoolean assertEquals true
    }

    @Test
    fun `andes modal full custom view, close button with replaceAccessibilityAction ActionClickLabel`() {
        val customView = View(context)
        modal = AndesModal.fullBuilder(customView)
            .build()

        modal.show(activity, R.id.content_frame)

        modal.view?.let { view ->
            val closeButton = modal.getCloseButtonView()
            closeButton?.let { button ->
                val nodeInfo = button.createAccessibilityNodeInfo()
                val actionClick = nodeInfo.actionList.filter {
                    it.id == AccessibilityNodeInfo.ACTION_CLICK
                }[0]
                actionClick.label assertEquals context.resources.getString(R.string.andes_modal_dismiss_content_description)
            }
        }
    }
}
