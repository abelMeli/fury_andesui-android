package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageView
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
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderType
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.utils.Constants
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getCloseButtonComponent
import com.mercadolibre.android.andesui.utils.getCustomViewComponent
import com.mercadolibre.android.andesui.utils.getHeaderComponent
import com.mercadolibre.android.andesui.utils.getScrollComponent
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
class AndesModalCardViewFragmentTest {

    private lateinit var robolectricActivity: ActivityController<FragmentActivity>
    private lateinit var activity: FragmentActivity
    private lateinit var modal: AndesDialogFragment
    private lateinit var context: Context

    companion object {
        private const val MIDDLE_SCROLL_POSITION = 400
        private const val TITLE = "Andes Title"

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        robolectricActivity = Robolectric.buildActivity(FragmentActivity::class.java).create()
        activity = robolectricActivity.get()
        robolectricActivity.start().postCreate(null).resume().visible()
        context = activity.applicationContext
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
    fun `modal card default with all default values`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView).build()

        modal.show(activity)

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.VISIBLE
        modal.getCustomViewComponent() assertEquals customView
        modal.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal card default with all values set`() {
        val customView = View(context)
        val buttonGroupData = AndesButtonGroupData(AndesButtonGroup(context, listOf(AndesButton(context))))
        val buttonGroupCreator = object : AndesButtonGroupCreator {
            override fun create(modalInterface: AndesModalInterface) = buttonGroupData
        }
        modal = AndesModal.cardBuilder(customView)
            .withOnDismissCallback { /* no-op */ }
            .withIsDismissible(false)
            .withButtonGroupCreator(buttonGroupCreator)
            .withOnModalShowCallback { /* no-op */ }
            .build()

        modal.show(activity)

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.GONE
        modal.getCustomViewComponent() assertEquals customView
        modal.getButtonGroupComponent() assertEquals buttonGroupData.buttonGroup
    }

    @Test
    fun `given modal with show callback, when showing modal, then callback is called`() {
        val customView = View(context)
        var testBoolean = false
        modal = AndesModal.cardBuilder(customView)
            .withOnModalShowCallback { testBoolean = true }
            .build()

        modal.show(activity)

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with dismiss callback, when dismissing modal, then callback is called`() {
        val customView = View(context)
        var testBoolean = false
        modal = AndesModal.cardBuilder(customView)
            .withOnDismissCallback { testBoolean = true }
            .build()

        modal.show(activity)
        modal.dismiss()

        testBoolean assertEquals true
    }

    @Test
    fun `andes modal custom view, close button with replaceAccessibilityAction ActionClickLabel`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView)
            .build()

        modal.show(activity)
        modal.view?.let { view ->
            val closeButton = view.findViewById<ImageView>(R.id.andes_modal_close_button)
            val nodeInfo = closeButton.createAccessibilityNodeInfo()
            val actionClick = nodeInfo.actionList.filter {
                it.id == AccessibilityNodeInfo.ACTION_CLICK
            }[0]
            actionClick.label assertEquals context.resources.getString(R.string.andes_modal_dismiss_content_description)
        }
    }

    @Test
    fun `andes modal when title is empty check that visibility is GONE`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView)
            .withHeaderTitle("")
            .withIsHeaderFixed(true)
            .build()

        modal.show(activity)

        modal.view?.let { view ->
            val title = view.findViewById<AndesTextView>(R.id.title)
            title.visibility assertEquals View.GONE
        }
    }

    @Test
    fun `given modal custom with header fixed, when scrolling down, then sticky header is visible`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView)
            .withHeaderTitle(TITLE)
            .withIsHeaderFixed(true)
            .build()

        modal.show(activity)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.GONE

        modal.getScrollComponent()?.smoothScrollTo(0, MIDDLE_SCROLL_POSITION)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.VISIBLE
    }

    @Test
    fun `given modal with header fixed, when scrolling up, then sticky header is gone`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView)
            .withHeaderTitle(TITLE)
            .withIsHeaderFixed(true)
            .build()

        modal.show(activity)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.GONE

        modal.getScrollComponent()?.smoothScrollTo(0, MIDDLE_SCROLL_POSITION)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.VISIBLE

        modal.getScrollComponent()?.smoothScrollTo(0, 0)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.GONE

    }

    @Test
    fun `given modal whit header fixed, check header config`() {
        val customView = View(context)
        modal = AndesModal.cardBuilder(customView)
            .withHeaderTitle(TITLE)
            .withIsHeaderFixed(true)
            .build()

        modal.show(activity)

        modal.getHeaderComponent()?.let {
            it.titleVisibility assertEquals View.GONE
            it.textStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED
            it.headerType assertEquals AndesModalFullHeaderType.ONLY_TITLE
            it.headerTitle assertEquals TITLE
        }
    }
}
