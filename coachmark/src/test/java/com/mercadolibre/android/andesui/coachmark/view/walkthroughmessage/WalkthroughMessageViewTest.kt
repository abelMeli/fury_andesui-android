package com.mercadolibre.android.andesui.coachmark.view.walkthroughmessage

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughMessageBinding
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class WalkthroughMessageViewTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `WalkthroughMessageView setData middle item`() {
        // GIVEN
        val walkthroughMessageView = WalkthroughMessageView(context)
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
        val walkthroughMessageView = WalkthroughMessageView(context)
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
        val walkthroughMessageView = WalkthroughMessageView(context)
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
        val walkthroughMessageView = WalkthroughMessageView(context)
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

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    fun AndesWalkthroughMessageBinding.walkthroughTitle(): TextView =
        root.findViewById(R.id.walkthroughTitle)

    fun AndesWalkthroughMessageBinding.walkthroughDescription(): TextView =
        root.findViewById(R.id.walkthroughDescription)

    fun AndesWalkthroughMessageBinding.walkthroughNextButton(): TextView =
        root.findViewById(R.id.walkthroughNextButton)
}
