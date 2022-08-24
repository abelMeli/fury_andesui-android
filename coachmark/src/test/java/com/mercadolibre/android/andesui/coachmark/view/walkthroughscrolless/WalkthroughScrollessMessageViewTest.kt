package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughScrollessMessageBinding
import com.mercadolibre.android.andesui.coachmark.model.AndesScrollessWalkthroughCoachmark
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStep
import com.mercadolibre.android.andesui.coachmark.model.AndesWalkthroughCoachmarkStyle
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage.ArcArrow
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE], qualifiers = "w720dp-h1600dp-mdpi")
class WalkthroughScrollessMessageViewTest {

    private lateinit var walkthroughScrollessMessageView: WalkthroughScrollessMessageView
    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var activity: AppCompatActivity

    @Before
    fun setUp() {
        setupActivityForTest()
    }

    @Test
    fun `WalkthroughScrollessMessageView setData middle item`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Button"

        // WHEN
        walkthroughScrollessMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            false
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        with(binding) {
            walkthroughTitle().visibility assertEquals View.VISIBLE
            walkthroughTitle.text.toString() assertEquals title
            walkthroughDescription().visibility assertEquals View.VISIBLE
            walkthroughDescription.text.toString() assertEquals description
            walkthroughNextButton().visibility assertEquals View.VISIBLE
            walkthroughNextButton.text.toString() assertEquals button
            walkthroughNextButton.background assertEquals R.drawable.andes_walkthrough_configuration_button_background
        }
    }

    @Test
    fun `WalkthroughScrollessMessageView setData last item`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"

        // WHEN
        walkthroughScrollessMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        with(binding) {
            walkthroughTitle().visibility assertEquals View.VISIBLE
            walkthroughTitle.text.toString() assertEquals title
            walkthroughDescription().visibility assertEquals View.VISIBLE
            walkthroughDescription.text.toString() assertEquals description
            walkthroughNextButton().visibility assertEquals View.VISIBLE
            walkthroughNextButton.text.toString() assertEquals button
            walkthroughNextButton.background assertEquals R.drawable.andes_walkthrough_configuration_blue_button_background
        }
    }

    @Test
    fun `WalkthroughScrollessMessageView clear data`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"

        // WHEN
        walkthroughScrollessMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )
        walkthroughScrollessMessageView.clear()

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        with(binding) {
            walkthroughTitle().visibility assertEquals View.GONE
            walkthroughTitle.text.toString() assertEquals title
            walkthroughDescription().visibility assertEquals View.GONE
            walkthroughDescription.text.toString() assertEquals description
            walkthroughNextButton().visibility assertEquals View.VISIBLE
            walkthroughNextButton.text.toString() assertEquals button
            walkthroughNextButton.background assertEquals R.drawable.andes_walkthrough_configuration_blue_button_background
        }
    }

    @Test
    fun `WalkthroughScrollessMessageView set position callback`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"
        val callback = Mockito.spy(object : WalkthroughScrollessMessageView.WalkthroughButtonClicklistener {
            override fun onClickNextButton(position: Int) = Unit
        })
        walkthroughScrollessMessageView.setPosition(1)
        walkthroughScrollessMessageView.setListener(callback)

        // WHEN
        walkthroughScrollessMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.walkthroughNextButton.performClick()

        // THEN
        verify(callback).onClickNextButton(1)
    }

    @Test
    fun `Show top arrow when target is top-left aligned`() {
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(SCREEN_RECT.left, SCREEN_RECT.top, 56, 128)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.VISIBLE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is top-centeredHorizontal aligned`() {
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240, SCREEN_RECT.top, 480, 128)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is top,centeredHorizontal slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 + arrowMinWidth, SCREEN_RECT.top, 480 + arrowMinWidth, 128)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show top arrow when target is top,centeredHorizontal more than slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 + arrowMinWidth + 1, SCREEN_RECT.top, 480 + arrowMinWidth + 1, 128)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.VISIBLE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show bottom arrow when target is bottom-right aligned`() {
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(720 - 60, 1400, SCREEN_RECT.right, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.VISIBLE
    }

    @Test
    fun `Do NOT show bottom arrow when target is bottom-centeredHorizontal aligned`() {
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240, 1400, 480, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is bottom,centeredHorizontal slightly left`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 - arrowMinWidth, 1400, 480 - arrowMinWidth, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show bottom arrow when target is bottom,centeredHorizontal more than slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughScrollessMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 - arrowMinWidth - 1, 1400, 480 - arrowMinWidth - 1, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughScrollessMessageBinding.bind(walkthroughScrollessMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.VISIBLE
    }

    @Test
    fun `has arrow returns correctly when showing top arrow`() {
        val data = AndesScrollessWalkthroughCoachmark(
            steps = arrayListOf(
                AndesWalkthroughCoachmarkStep(
                    "title",
                    "description",
                    "next",
                    View(activity),
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            anchorView = View(activity)
        ) { }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, data).build()
        val message =
            ReflectionHelpers.getField<WalkthroughScrollessMessageView>(
                coachmarkScrollessView,
                "walkthroughScrollessMessageView"
            )

        message.getTopArrow().visibility = View.VISIBLE
        message.getBottomArrow().visibility = View.GONE

        coachmarkScrollessView.hasArrow() assertEquals true
    }

    @Test
    fun `has arrow returns correctly when showing bottom arrow`() {
        val data = AndesScrollessWalkthroughCoachmark(
            steps = arrayListOf(
                AndesWalkthroughCoachmarkStep(
                    "title",
                    "description",
                    "next",
                    View(activity),
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            anchorView = View(activity)
        ) { }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, data).build()
        val message =
            ReflectionHelpers.getField<WalkthroughScrollessMessageView>(
                coachmarkScrollessView,
                "walkthroughScrollessMessageView"
            )

        message.getTopArrow().visibility = View.GONE
        message.getBottomArrow().visibility = View.VISIBLE

        coachmarkScrollessView.hasArrow() assertEquals true
    }

    @Test
    fun `has arrow returns correctly when showing no arrow`() {
        val data = AndesScrollessWalkthroughCoachmark(
            steps = arrayListOf(
                AndesWalkthroughCoachmarkStep(
                    "title",
                    "description",
                    "next",
                    View(activity),
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            anchorView = View(activity)
        ) { }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, data).build()
        val message =
            ReflectionHelpers.getField<WalkthroughScrollessMessageView>(
                coachmarkScrollessView,
                "walkthroughScrollessMessageView"
            )

        message.getTopArrow().visibility = View.GONE
        message.getBottomArrow().visibility = View.GONE

        coachmarkScrollessView.hasArrow() assertEquals false
    }

    @Test
    fun `setting new dimension WRAP_CONTENT for message works correctly`() {
        val data = AndesScrollessWalkthroughCoachmark(
            steps = arrayListOf(
                AndesWalkthroughCoachmarkStep(
                    "title",
                    "description",
                    "next",
                    View(activity),
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            anchorView = View(activity)
        ) { }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, data).build()
        val message =
            ReflectionHelpers.getField<WalkthroughScrollessMessageView>(
                coachmarkScrollessView,
                "walkthroughScrollessMessageView"
            )

        coachmarkScrollessView.setNewMessageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT)

        message.getButton().getVerticalBias() assertEquals CENTER_BIAS
        message.getButton().getGoneBottomMargin() assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_arrow_margin_gone)
    }

    @Test
    fun `setting new custom dimension for message works correctly`() {
        val data = AndesScrollessWalkthroughCoachmark(
            steps = arrayListOf(
                AndesWalkthroughCoachmarkStep(
                    "title",
                    "description",
                    "next",
                    View(activity),
                    AndesWalkthroughCoachmarkStyle.CIRCLE
                )
            ),
            anchorView = View(activity)
        ) { }
        val coachmarkScrollessView = CoachmarkScrollessView.Builder(activity, data).build()
        val message =
            ReflectionHelpers.getField<WalkthroughScrollessMessageView>(
                coachmarkScrollessView,
                "walkthroughScrollessMessageView"
            )

        coachmarkScrollessView.setNewMessageDimensions(600)

        message.getButton().getVerticalBias() assertEquals BOTTOM_BIAS
        message.getButton().getGoneBottomMargin() assertEquals context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_button_bottom_margin)
    }

    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        walkthroughScrollessMessageView = WalkthroughScrollessMessageView(activity)
        activity.setContentView(walkthroughScrollessMessageView)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    fun AndesWalkthroughScrollessMessageBinding.walkthroughTitle(): TextView =
        root.findViewById(R.id.walkthroughTitle)

    fun AndesWalkthroughScrollessMessageBinding.walkthroughDescription(): TextView =
        root.findViewById(R.id.walkthroughDescription)

    fun AndesWalkthroughScrollessMessageBinding.walkthroughNextButton(): TextView =
        root.findViewById(R.id.walkthroughNextButton)

    fun AndesWalkthroughScrollessMessageBinding.arcArrowTop(): ArcArrow =
        root.findViewById(R.id.arcArrowTop)

    fun AndesWalkthroughScrollessMessageBinding.arcArrowBottom(): ArcArrow =
        root.findViewById(R.id.arcArrowBottom)

    private fun WalkthroughScrollessMessageView.getTopArrow(): View =
        findViewById(R.id.arcArrowTop)

    private fun WalkthroughScrollessMessageView.getBottomArrow(): View =
        findViewById(R.id.arcArrowBottom)

    private fun WalkthroughScrollessMessageView.getButton(): View =
        findViewById(R.id.walkthroughNextButton)

    private fun View.getVerticalBias(): Float? {
        val params = layoutParams as? ConstraintLayout.LayoutParams
            ?: return null
        return params.verticalBias
    }

    private fun View.getGoneBottomMargin(): Int? {
        val params = layoutParams as? ConstraintLayout.LayoutParams
            ?: return null
        return params.goneBottomMargin
    }

    companion object {
        private val SCREEN_RECT: Rect = Rect(0, 56, 720, 1600)
        private const val CENTER_BIAS = 0.5f
        private const val BOTTOM_BIAS = 1f
    }
}