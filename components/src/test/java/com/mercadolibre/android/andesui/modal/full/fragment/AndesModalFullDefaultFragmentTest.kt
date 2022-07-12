package com.mercadolibre.android.andesui.modal.full.fragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
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
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.full.headertype.AndesModalFullHeaderStatus
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.getBannerComponent
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getCloseButtonView
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
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
class AndesModalFullDefaultFragmentTest {

    private lateinit var robolectricActivity: ActivityController<FragmentActivity>
    private lateinit var activity: FragmentActivity
    private lateinit var fragment: AndesModalBaseFragment<*>
    private lateinit var context: Context

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
        fragment = AndesModal.fullBuilder(provideContent(context)).build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.isVisible assertEquals true
        fragment.getBannerComponent()?.visibility assertEquals View.GONE
        fragment.getThumbnailComponent()?.visibility assertEquals View.GONE
        fragment.getTitleComponent()?.text.toString() assertEquals TITLE
        fragment.getSubtitleComponent()?.text.toString() assertEquals SUB_TITLE
        fragment.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal full default with all default values and empty content`() {
        fragment = AndesModal.fullBuilder(AndesModalContent()).build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.isVisible assertEquals true
        fragment.getBannerComponent()?.visibility assertEquals View.GONE
        fragment.getThumbnailComponent()?.visibility assertEquals View.GONE
        fragment.getTitleComponent()?.text.toString() assertEquals ""
        fragment.getSubtitleComponent()?.text.toString() assertEquals ""
        fragment.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal full default with set values`() {
        fragment = AndesModal.fullBuilder(provideContent(context))
            .withContentVariation(AndesModalFullContentVariation.SMALL_ILLUSTRATION)
            .withIsHeaderFixed(true)
            .withIsButtonGroupFixed(true)
            .withButtonGroupCreator(provideButtonGroupCreator(context))
            .withIsDismissible(false)
            .withOnDismissCallback { /*no-op*/ }
            .withOnModalShowCallback { /*no-op*/ }
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.isVisible assertEquals true
        fragment.getBannerComponent()?.visibility assertEquals View.VISIBLE
        fragment.getThumbnailComponent()?.visibility assertEquals View.GONE
        fragment.getTitleComponent()?.text.toString() assertEquals TITLE
        fragment.getSubtitleComponent()?.text.toString() assertEquals SUB_TITLE
        fragment.getButtonGroupComponent() assertIsNull false
    }

    @Test
    fun `given modal with show callback, when showing modal, then callback is called`() {
        var callBack = false

        fragment = AndesModal.fullBuilder(provideContent(context))
            .withContentVariation(AndesModalFullContentVariation.LARGE_ILLUSTRATION)
            .withOnModalShowCallback { callBack = true }
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        callBack assertEquals true
    }

    @Test
    fun `given modal with dismiss callback, when dismissing modal, then callback is called`() {
        var callBack = false

        fragment = AndesModal.fullBuilder(provideContent(context))
            .withOnDismissCallback { callBack = true }
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()
        fragment.dismiss()
        shadowOf(Looper.getMainLooper()).idle()

        callBack assertEquals true
    }

    @Test
    fun `given modal with content variation NONE, when scrolling down, then header text status is correct`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withContentVariation(AndesModalFullContentVariation.NONE)
            .build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.scrollY = MIDDLE_SCROLL_POSITION

        fragment.getHeaderComponent()?.textStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
    }

    @Test
    fun `given modal with content variation MEDIUM ILLUSTRATION, when vertical scroll is in initial posibion, then header text is not visible`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withContentVariation(AndesModalFullContentVariation.MEDIUM_ILLUSTRATION)
            .build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.scrollY = TOP_SCROLL_POSITION

        fragment.getTitleComponent()?.visibility = View.GONE
    }

    @Test
    fun `given modal with content variation MEDIUM ILLUSTRATION, when scrolling down, then header text is visible`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withContentVariation(AndesModalFullContentVariation.MEDIUM_ILLUSTRATION)
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.scrollY = MIDDLE_SCROLL_POSITION

        fragment.getTitleComponent()?.visibility = View.VISIBLE
    }

    @Test
    fun `andes modal full default, close button with replaceAccessibilityAction ActionClickLabel`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withContentVariation(AndesModalFullContentVariation.MEDIUM_ILLUSTRATION)
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.view?.let { view ->
            val closeButton = fragment.getCloseButtonView()
            closeButton?.let { button ->
                val nodeInfo = button.createAccessibilityNodeInfo()
                val actionClick = nodeInfo.actionList.filter {
                    it.id == AccessibilityNodeInfo.ACTION_CLICK
                }[0]
                actionClick.label assertEquals context.resources.getString(R.string.andes_modal_dismiss_content_description)
            }
        }
    }

    @Test
    fun `given modal with header fixed and scrollY greater than zero then textStatus is COLLAPSED`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withIsHeaderFixed(true)
            .withContentVariation(AndesModalFullContentVariation.MEDIUM_ILLUSTRATION)
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.smoothScrollTo(0, MIDDLE_SCROLL_POSITION)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getHeaderComponent()?.textStatus assertEquals AndesModalFullHeaderStatus.COLLAPSED

    }

    @Test
    fun `given modal with header fixed and scrollY is zero then textStatus is EXPANDED`() {
        fragment = AndesModal.fullBuilder(provideLargeContent(context))
            .withIsHeaderFixed(true)
            .withContentVariation(AndesModalFullContentVariation.NONE)
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.smoothScrollTo(0, MIDDLE_SCROLL_POSITION)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getScrollComponent()?.smoothScrollTo(0, TOP_SCROLL_POSITION)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getHeaderComponent()?.textStatus assertEquals AndesModalFullHeaderStatus.EXPANDED
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

        fragment = AndesModal.fullBuilder(
            AndesModalContent(
                title = "title",
                suspendedDrawable = suspendedDrawable
            )
        ).build()

        (fragment as AndesModalFullDefaultFragment).show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        val actualImage = (fragment as AndesModalFullDefaultFragment)
            .getImageComponent()
            ?.imageDrawable

        actualImage assertEquals mockedResponse
    }

    companion object {
        private const val TITLE = "Title"
        private const val SUB_TITLE = "Subtitle"
        private const val TOP_SCROLL_POSITION = 0
        private const val MIDDLE_SCROLL_POSITION = 400
    }
}
