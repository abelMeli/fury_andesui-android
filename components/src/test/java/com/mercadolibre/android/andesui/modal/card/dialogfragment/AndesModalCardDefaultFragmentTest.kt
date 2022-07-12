package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.View
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
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.getBannerComponent
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getCloseButtonComponent
import com.mercadolibre.android.andesui.utils.getHeaderComponent
import com.mercadolibre.android.andesui.utils.getScrollComponent
import com.mercadolibre.android.andesui.utils.getSubtitleComponent
import com.mercadolibre.android.andesui.utils.getThumbnailComponent
import com.mercadolibre.android.andesui.utils.getTitleComponent
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import com.mercadolibre.android.andesui.utils.provideContent
import com.mercadolibre.android.andesui.utils.provideLargeContent
import com.mercadolibre.android.andesui.utils.setDrawableSuspending
import com.mercadolibre.android.andesui.utils.getImageComponent
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesModalCardDefaultFragmentTest {

    private lateinit var robolectricActivity: ActivityController<FragmentActivity>
    private lateinit var activity: FragmentActivity
    private lateinit var modal: AndesDialogFragment
    private lateinit var context: Context

    companion object {
        private const val MIDDLE_SCROLL_POSITION = 400

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
        modal = AndesModal.cardBuilder(provideContent(context)).build()

        modal.show(activity)

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.VISIBLE
        modal.getBannerComponent()?.visibility assertEquals View.GONE
        modal.getThumbnailComponent()?.visibility assertEquals View.GONE
        modal.getTitleComponent()?.text.toString() assertEquals "Title"
        modal.getSubtitleComponent()?.text.toString() assertEquals "Subtitle"
        modal.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal card default with all values set`() {
        modal = AndesModal.cardBuilder(provideContent(context))
            .withContentVariation(AndesModalCardContentVariation.IMAGE)
            .withIsButtonGroupFixed(true)
            .withIsHeaderFixed(true)
            .withButtonGroupCreator(provideButtonGroupCreator(context))
            .withIsDismissible(false)
            .withOnDismissCallback { /*no-op*/ }
            .withOnModalShowCallback { /*no-op*/ }
            .build()

        modal.show(activity)

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.GONE
        modal.getBannerComponent()?.visibility assertEquals View.VISIBLE
        modal.getThumbnailComponent()?.visibility assertEquals View.GONE
        modal.getTitleComponent()?.text.toString() assertEquals "Title"
        modal.getSubtitleComponent()?.text.toString() assertEquals "Subtitle"
        modal.getButtonGroupComponent() assertIsNull false
    }

    @Test
    fun `given modal with show callback, when showing modal, then callback is called`() {
        var testBoolean = false

        modal = AndesModal.cardBuilder(provideContent(context))
            .withContentVariation(AndesModalCardContentVariation.SMALL_ILLUSTRATION)
            .withOnModalShowCallback { testBoolean = true }
            .build()

        modal.show(activity)

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with dismiss callback, when dismissing modal, then callback is called`() {
        var testBoolean = false

        modal = AndesModal.cardBuilder(provideContent(context))
            .withContentVariation(AndesModalCardContentVariation.MEDIUM_ILLUSTRATION)
            .withOnDismissCallback { testBoolean = true }
            .build()

        modal.show(activity)
        modal.dismiss()

        testBoolean assertEquals true
    }

    @Test
    fun `andes modal default close button with replaceAccessibilityAction ActionClickLabel`() {
        modal = AndesModal.cardBuilder(provideContent(context))
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
    fun `given modal with header fixed, when scrolling down, then sticky header is visible`() {
        modal = AndesModal.cardBuilder(provideLargeContent(context))
            .withIsHeaderFixed(true)
            .build()

        modal.show(activity)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.GONE

        modal.getScrollComponent()?.smoothScrollTo(0, MIDDLE_SCROLL_POSITION)

        modal.getHeaderComponent()?.titleVisibility assertEquals View.VISIBLE
    }

    @Test
    fun `given modal with header fixed, when scrolling up, then sticky header is gone`() {
        modal = AndesModal.cardBuilder(provideLargeContent(context))
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
    fun `given modal with suspended drawable in the content, when setting image, then image is correct`() {
        val suspendedDrawable: suspend() -> Drawable? = mockk()
        val mockedResponse = ColorDrawable()
        mockkStatic(::setDrawableSuspending)
        every {
            setDrawableSuspending(any(), any())
        } answers {
            secondArg<(Drawable?) -> Unit>().invoke(mockedResponse)
        }

        modal = AndesModal.cardBuilder(
            AndesModalContent(
                title = "title",
                suspendedDrawable = suspendedDrawable
            )
        ).build()

        modal.show(activity)
        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val actualImage = modal.getImageComponent()?.imageDrawable

        actualImage assertEquals mockedResponse
    }
}
