package com.mercadolibre.android.andesui.coachmark.view

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughContainerBinding
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class CoachmarkContainerViewTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `Coachmark container text when setData`() {
        // GIVEN
        val coachmarkContainerView = CoachmarkContainerView(context)

        // WHEN
        coachmarkContainerView.setData(0, 2)

        // THEN
        val binding = AndesWalkthroughContainerBinding.bind(coachmarkContainerView)
        binding.counterText.text.toString() assertEquals "1 of 2"
    }

    @Test
    fun `Coachmark container listener called onClick`() {
        // GIVEN
        val coachmarkContainerView = CoachmarkContainerView(context)
        val listener = spy(object : CoachmarkContainerView.CoachmarkContainerListener {
            override fun onClickClose(position: Int) = Unit
        })
        coachmarkContainerView.setData(0, 2)
        coachmarkContainerView.setListener(listener)

        // WHEN
        val binding = AndesWalkthroughContainerBinding.bind(coachmarkContainerView)
        binding.closeButton.performClick()

        // THEN
        verify(listener).onClickClose(0)
    }
}
