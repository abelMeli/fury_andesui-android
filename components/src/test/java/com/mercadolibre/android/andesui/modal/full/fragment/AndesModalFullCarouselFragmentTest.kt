package com.mercadolibre.android.andesui.modal.full.fragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.databinding.AndesModalBaseFullLayoutBinding
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalFullContentVariation
import com.mercadolibre.android.andesui.modal.views.AndesModalHeaderTypeComponent
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getViewHolderForPosition
import com.mercadolibre.android.andesui.utils.getViewPagerComponent
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import com.mercadolibre.android.andesui.utils.provideContent
import com.mercadolibre.android.andesui.utils.provideOtherContent
import com.mercadolibre.android.andesui.utils.setDrawableSuspending
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
class AndesModalFullCarouselFragmentTest {

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
    fun `modal full carousel with all default values`() {
        val contentList = arrayListOf(provideContent(context))

        fragment = AndesModal.fullBuilder(contentList).build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.isVisible assertEquals true
        fragment.getViewPagerComponent()?.adapter?.count assertEquals contentList.size
        fragment.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal full carousel with all values set`() {
        val contentList = arrayListOf(provideContent(context), provideContent(context))
        val callback = { /*no-op*/ }
        val pageSelectedCallback = { _: Int -> }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        fragment = AndesModal.fullBuilder(contentList)
            .withContentVariation(AndesModalFullContentVariation.THUMBNAIL)
            .withIsHeaderFixed(true)
            .withOnPageSelectedCallback(pageSelectedCallback)
            .withOnDismissCallback(callback)
            .withOnModalShowCallback(callback)
            .withButtonGroupCreator(buttonGroupCreator)
            .withIsDismissible(false)
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.isVisible assertEquals true
        fragment.getViewPagerComponent()?.adapter?.count assertEquals contentList.size
        fragment.getButtonGroupComponent() assertIsNull false
        fragment.getButtonGroupComponent()?.visibility assertEquals View.VISIBLE
    }

    @Test
    fun `given modal full carousel with show callback, when showing modal, then callback is called`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        var testBoolean = false
        fragment = AndesModal.fullBuilder(contentList)
            .withOnModalShowCallback{ testBoolean = true }
            .build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with page selected callback, when changing pages, then callback is called`() {
        var currentPage = FIRST_PAGE

        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))

        fragment = AndesModal.fullBuilder(contentList)
            .withOnPageSelectedCallback { page -> currentPage = page }
            .build()

        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getViewPagerComponent()?.setCurrentItem(SECOND_PAGE, false)
        shadowOf(Looper.getMainLooper()).idle()
        currentPage assertEquals SECOND_PAGE

        fragment.getViewPagerComponent()?.setCurrentItem(THIRD_PAGE, false)
        shadowOf(Looper.getMainLooper()).idle()
        currentPage assertEquals THIRD_PAGE
    }

    @Test @Config(qualifiers = "w320dp")
    fun `given modal full carousel with second page selected, validate that the title and subtitle`() {
        val contentList =
            arrayListOf(provideContent(context), provideOtherContent(context))
        val callback = { /*no-op*/ }
        val pageSelectedCallback = { _: Int -> }
        val buttonGroupCreator = provideButtonGroupCreator(context)

        fragment = AndesModal.fullBuilder(contentList)
            .withContentVariation(AndesModalFullContentVariation.THUMBNAIL)
            .withIsHeaderFixed(true)
            .withOnPageSelectedCallback(pageSelectedCallback)
            .withOnDismissCallback(callback)
            .withOnModalShowCallback(callback)
            .withButtonGroupCreator(buttonGroupCreator)
            .withIsDismissible(false)
            .build()

        // Show full modal
        (fragment as AndesModalFullCarouselFragment).show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        // change the page
        fragment.getViewPagerComponent()?.setCurrentItem(SECOND_PAGE, false)
        shadowOf(Looper.getMainLooper()).idle()

        // get the second ViewHolder
        val viewHolder = (fragment as AndesModalFullCarouselFragment).getViewHolderForPosition(SECOND_PAGE)

        // get text to check
        val titleText = viewHolder.findViewById<AndesTextView>(R.id.title).text.toString()

        val subTitleText = viewHolder.findViewById<AndesTextView>(R.id.sub_title).text.toString()

        contentList[SECOND_PAGE].title assertEquals titleText
        contentList[SECOND_PAGE].subtitle assertEquals subTitleText
    }

    @Test
    fun `given modal with main action button, when going to last page, then hierarchy is correct`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        val buttonGroupCreator = provideButtonGroupCreator(context)
        fragment = AndesModal.fullBuilder(contentList)
            .withButtonGroupCreator(buttonGroupCreator)
            .build()
        fragment.show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getViewPagerComponent()?.setCurrentItem(THIRD_PAGE, false)
        shadowOf(Looper.getMainLooper()).idle()

        fragment.getButtonGroupComponent()?.getButton(FIRST_BUTTON)?.hierarchy assertEquals AndesButtonHierarchy.LOUD
    }

    @Test
    fun `andes modal full carousel, close button with replaceAccessibilityAction ActionClickLabel`() {
        val contentList =
            arrayListOf(provideContent(context), provideOtherContent(context))

        fragment = AndesModal.fullBuilder(contentList)
            .withContentVariation(AndesModalFullContentVariation.THUMBNAIL)
            .withIsHeaderFixed(true)
            .withIsDismissible(false)
            .build()

        (fragment as AndesModalFullCarouselFragment).show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        val viewPager = fragment.getViewPagerComponent()

        viewPager?.setCurrentItem(SECOND_PAGE, false)
        shadowOf(Looper.getMainLooper()).idle()

        val viewHolder = (fragment as AndesModalFullCarouselFragment).getViewHolderForPosition(SECOND_PAGE)

        val headerType = viewHolder.findViewById<AndesModalHeaderTypeComponent>(R.id.header_type)

        val closeButton = headerType.findViewById<ImageView>(R.id.closeImageview)

        closeButton?.let { button ->
            val nodeInfo = button.createAccessibilityNodeInfo()
            val actionClick = nodeInfo.actionList.filter {
                it.id == AccessibilityNodeInfo.ACTION_CLICK
            }[0]
            actionClick.label assertEquals context.resources.getString(R.string.andes_modal_dismiss_content_description)
        }
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
            arrayListOf(
                AndesModalContent(
                    title = "title",
                    suspendedDrawable = suspendedDrawable
                )
            )
        ).build()

        (fragment as AndesModalFullCarouselFragment).show(activity, R.id.content_frame)
        shadowOf(Looper.getMainLooper()).idle()

        val pageView = (fragment as AndesModalFullCarouselFragment).getViewHolderForPosition(FIRST_PAGE)
        val binding = AndesModalBaseFullLayoutBinding.bind(pageView)
        val actualImage = binding.imageHeader.imageDrawable

        actualImage assertEquals mockedResponse
    }

    companion object {
        private const val FIRST_PAGE = 0
        private const val SECOND_PAGE = 1
        private const val THIRD_PAGE = 2
        private const val FIRST_BUTTON = 0
    }
}
