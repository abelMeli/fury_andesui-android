package com.mercadolibre.android.andesui.modal.card.dialogfragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Looper.getMainLooper
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
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.button.hierarchy.AndesButtonHierarchy
import com.mercadolibre.android.andesui.buttongroup.AndesButtonGroup
import com.mercadolibre.android.andesui.databinding.AndesModalSingleContentLayoutBinding
import com.mercadolibre.android.andesui.modal.AndesModal
import com.mercadolibre.android.andesui.modal.common.AndesButtonGroupData
import com.mercadolibre.android.andesui.modal.common.AndesModalContent
import com.mercadolibre.android.andesui.modal.common.contentvariation.AndesModalCardContentVariation
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.assertIsNull
import com.mercadolibre.android.andesui.utils.getButtonGroupComponent
import com.mercadolibre.android.andesui.utils.getCloseButtonComponent
import com.mercadolibre.android.andesui.utils.getViewHolderForPosition
import com.mercadolibre.android.andesui.utils.getViewPagerComponent
import com.mercadolibre.android.andesui.utils.provideButtonGroupCreator
import com.mercadolibre.android.andesui.utils.provideContent
import com.mercadolibre.android.andesui.utils.setDrawableSuspending
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.BeforeClass
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
class AndesModalCardCarouselFragmentTest {

    private lateinit var robolectricActivity: ActivityController<FragmentActivity>
    private lateinit var activity: FragmentActivity
    private lateinit var modal: AndesDialogFragment
    private lateinit var context: Context

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }

        private const val FIRST_PAGE = 0
        private const val SECOND_PAGE = 1
        private const val THIRD_PAGE = 2
        private const val FIRST_BUTTON = 0
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
        val contentList = arrayListOf(provideContent(context))
        modal = AndesModal.cardBuilder(contentList).build()

        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.VISIBLE
        modal.getViewPagerComponent()?.adapter?.count assertEquals contentList.size
        modal.getButtonGroupComponent() assertIsNull true
    }

    @Test
    fun `modal card default with all values set`() {
        val contentList = arrayListOf(provideContent(context), provideContent(context))
        val callback = { /*no-op*/ }
        val pageSelectedCallback = { _: Int -> }
        val buttonGroupCreator = provideButtonGroupCreator(context)
        modal = AndesModal.cardBuilder(contentList)
            .withContentVariation(AndesModalCardContentVariation.THUMBNAIL)
            .withIsHeaderFixed(true)
            .withOnPageSelectedCallback(pageSelectedCallback)
            .withOnDismissCallback(callback)
            .withOnModalShowCallback(callback)
            .withButtonGroupCreator(buttonGroupCreator)
            .withIsDismissible(false)
            .build()

        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        modal.isVisible assertEquals true
        modal.getCloseButtonComponent()?.visibility assertEquals View.GONE
        modal.getViewPagerComponent()?.adapter?.count assertEquals contentList.size
        modal.getButtonGroupComponent() assertIsNull false
        modal.getButtonGroupComponent()?.visibility assertEquals View.VISIBLE
    }

    @Test
    fun `given modal with show callback, when showing modal, then callback is called`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        var testBoolean = false
        modal = AndesModal.cardBuilder(contentList)
            .withOnModalShowCallback { testBoolean = true }
            .build()

        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with dismiss callback, when dismissing modal, then callback is called`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        var testBoolean = false
        modal = AndesModal.cardBuilder(contentList)
            .withOnDismissCallback { testBoolean = true }
            .build()

        modal.show(activity)
        shadowOf(getMainLooper()).idle()
        modal.dismiss()

        testBoolean assertEquals true
    }

    @Test
    fun `given modal with page selected callback, when changing pages, then callback is called`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        var currentPage = FIRST_PAGE
        modal = AndesModal.cardBuilder(contentList)
            .withOnPageSelectedCallback { page -> currentPage = page }
            .build()
        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        modal.getViewPagerComponent()?.setCurrentItem(SECOND_PAGE, false)
        currentPage assertEquals SECOND_PAGE

        modal.getViewPagerComponent()?.setCurrentItem(THIRD_PAGE, false)
        currentPage assertEquals THIRD_PAGE
    }

    @Test
    fun `given modal with main action button, when going to last page, then hierarchy is correct`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        val buttonGroupCreator = provideButtonGroupCreator(context, true)
        modal = AndesModal.cardBuilder(contentList)
            .withButtonGroupCreator(buttonGroupCreator)
            .build()
        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        modal.getButtonGroupComponent()
            ?.getButton(FIRST_BUTTON)?.hierarchy assertEquals AndesButtonHierarchy.QUIET

        modal.getViewPagerComponent()?.setCurrentItem(THIRD_PAGE, false)
        shadowOf(getMainLooper()).idle()

        modal.getButtonGroupComponent()
            ?.getButton(FIRST_BUTTON)?.hierarchy assertEquals AndesButtonHierarchy.LOUD
    }

    @Test
    fun `andes modal carousel close button with replaceAccessibilityAction ActionClickLabel`() {
        val contentList =
            arrayListOf(provideContent(context), provideContent(context), provideContent(context))
        modal = AndesModal.cardBuilder(contentList)
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
    fun `given modal with suspended drawable in the content, when setting image, then image is correct`() {
        val suspendedDrawable: suspend () -> Drawable? = mockk()
        val mockedResponse = ColorDrawable()
        mockkStatic(::setDrawableSuspending)
        every {
            setDrawableSuspending(any(), any())
        } answers {
            secondArg<(Drawable?) -> Unit>().invoke(mockedResponse)
        }

        modal = AndesModal.cardBuilder(
            arrayListOf(
                AndesModalContent(
                    title = "title",
                    suspendedDrawable = suspendedDrawable
                )
            )
        ).build()

        modal.show(activity)
        shadowOf(getMainLooper()).idle()

        val pageView =
            (modal as AndesModalCardCarouselFragment).getViewHolderForPosition(FIRST_PAGE)
        val binding = AndesModalSingleContentLayoutBinding.bind(pageView)
        val actualImage = binding.imageHeader.imageDrawable

        actualImage assertEquals mockedResponse
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given buttongroup, when passing an invalid value for main action, then throws exception`() {
        AndesButtonGroupData(
            buttonGroup = AndesButtonGroup(
                context = context,
                buttonList = listOf(
                    AndesButton(context)
                )
            ),
            mainAction = -1
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given buttongroup, when passing a value greater than button amount for main action, then throws exception`() {
        AndesButtonGroupData(
            buttonGroup = AndesButtonGroup(
                context = context,
                buttonList = listOf(
                    AndesButton(context)
                )
            ),
            mainAction = 2
        )
    }
}
