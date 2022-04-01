package com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughMessageBinding
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP], qualifiers = "w720dp-h1600dp-mdpi")
class WalkthroughMessageViewTest {

    private lateinit var walkthroughMessageView: WalkthroughMessageView
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        setupActivityForTest()
    }

    @Test
    fun `WalkthroughMessageView setData middle item`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Button"

        // WHEN
        walkthroughMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            false
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
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
    fun `WalkthroughMessageView setData last item`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"

        // WHEN
        walkthroughMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
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
    fun `WalkthroughMessageView clear data`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"

        // WHEN
        walkthroughMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )
        walkthroughMessageView.clear()

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
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
    fun `WalkthroughMessageView set position callback`() {
        // GIVEN
        val title = "Title"
        val description = "Description"
        val button = "Last"
        val callback = spy(object : WalkthroughMessageView.WalkthroughButtonClicklistener {
            override fun onClickNextButton(position: Int) = Unit
        })
        walkthroughMessageView.setPosition(1)
        walkthroughMessageView.setListener(callback)

        // WHEN
        walkthroughMessageView.setData(
            WalkthroughMessageModel(
                title = title,
                description = description,
                buttonText = button
            ),
            true
        )
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.walkthroughNextButton.performClick()

        // THEN
        verify(callback).onClickNextButton(1)
    }

    @Test
    fun `Show top arrow when target is top-left aligned`() {
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(SCREEN_RECT.left, SCREEN_RECT.top, 56, 128)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.VISIBLE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is top-centeredHorizontal aligned`() {
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240, SCREEN_RECT.top, 480, 128)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is top,centeredHorizontal slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 + arrowMinWidth, SCREEN_RECT.top, 480 + arrowMinWidth, 128)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show top arrow when target is top,centeredHorizontal more than slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 + arrowMinWidth + 1, SCREEN_RECT.top, 480 + arrowMinWidth + 1, 128)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.VISIBLE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show bottom arrow when target is bottom-right aligned`() {
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(720-60, 1400, SCREEN_RECT.right, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.VISIBLE
    }

    @Test
    fun `Do NOT show bottom arrow when target is bottom-centeredHorizontal aligned`() {
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240, 1400, 480, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Do NOT show top arrow when target is bottom,centeredHorizontal slightly left`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 - arrowMinWidth, 1400, 480 - arrowMinWidth, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.GONE
    }

    @Test
    fun `Show bottom arrow when target is bottom,centeredHorizontal more than slightly right`() {
        val arrowMinWidth = context.resources.getDimensionPixelSize(R.dimen.andes_coachmark_min_with_for_show_arrow)
        // WHEN
        walkthroughMessageView.definePosition(
            overlayRect = SCREEN_RECT,
            targetRect = Rect(240 - arrowMinWidth - 1, 1400, 480 - arrowMinWidth - 1, SCREEN_RECT.bottom)
        )

        // THEN
        val binding = AndesWalkthroughMessageBinding.bind(walkthroughMessageView.getChildAt(0))
        binding.arcArrowTop().visibility assertEquals View.GONE
        binding.arcArrowBottom().visibility assertEquals View.VISIBLE
    }


    private fun setupActivityForTest() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        walkthroughMessageView = WalkthroughMessageView(activity)
        activity.setContentView(walkthroughMessageView)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    fun AndesWalkthroughMessageBinding.walkthroughTitle(): TextView =
        root.findViewById(R.id.walkthroughTitle)

    fun AndesWalkthroughMessageBinding.walkthroughDescription(): TextView =
        root.findViewById(R.id.walkthroughDescription)

    fun AndesWalkthroughMessageBinding.walkthroughNextButton(): TextView =
        root.findViewById(R.id.walkthroughNextButton)

    fun AndesWalkthroughMessageBinding.arcArrowTop(): ArcArrow =
        root.findViewById(R.id.arcArrowTop)

    fun AndesWalkthroughMessageBinding.arcArrowBottom(): ArcArrow =
        root.findViewById(R.id.arcArrowBottom)

    companion object {
        private val SCREEN_RECT: Rect = Rect(0, 56, 720, 1600)
    }
}
