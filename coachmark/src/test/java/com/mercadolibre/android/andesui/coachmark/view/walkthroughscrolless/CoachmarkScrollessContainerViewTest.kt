package com.mercadolibre.android.andesui.coachmark.view.walkthroughscrolless

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.coachmark.databinding.AndesWalkthroughContainerBinding
import com.mercadolibre.android.andesui.coachmark.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.coachmark.utils.assertEquals
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class CoachmarkScrollessContainerViewTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `Coachmark scrolless container text when setData`() {
        // GIVEN
        val coachmarkContainerView = CoachmarkScrollessContainerView(context)

        // WHEN
        coachmarkContainerView.setData(0, 2)

        // THEN
        val binding = AndesWalkthroughContainerBinding.bind(coachmarkContainerView)
        binding.counterText.text.toString() assertEquals "1 of 2"
    }

    @Test
    fun `Coachmark scrolless container listener called onClick`() {
        // GIVEN
        val coachmarkContainerView = CoachmarkScrollessContainerView(context)
        val listener = Mockito.spy(object : CoachmarkScrollessContainerView.CoachmarkContainerListener {
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
