package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.R
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughScrollessMessageBinding
import com.mercadolibre.android.andesui.coachmark.model.WalkthroughMessageModel
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class WalkthroughScrollessMessageViewTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `WalkthroughScrollessMessageView setData middle item`() {
        // GIVEN
        val walkthroughScrollessMessageView = WalkthroughScrollessMessageView(context)
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
        val walkthroughScrollessMessageView = WalkthroughScrollessMessageView(context)
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
        val walkthroughScrollessMessageView = WalkthroughScrollessMessageView(context)
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
        val walkthroughScrollessMessageView = WalkthroughScrollessMessageView(context)
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

    /**
     * Workaround made to check view visibility. For some reason binding returns always View.VISIBLE.
     */
    fun AndesWalkthroughScrollessMessageBinding.walkthroughTitle(): TextView =
        root.findViewById(R.id.walkthroughTitle)

    fun AndesWalkthroughScrollessMessageBinding.walkthroughDescription(): TextView =
        root.findViewById(R.id.walkthroughDescription)

    fun AndesWalkthroughScrollessMessageBinding.walkthroughNextButton(): TextView =
        root.findViewById(R.id.walkthroughNextButton)
}