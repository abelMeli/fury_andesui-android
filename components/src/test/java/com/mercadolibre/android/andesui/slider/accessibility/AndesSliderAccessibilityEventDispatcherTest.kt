package com.mercadolibre.android.andesui.slider.accessibility

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.slider.AndesSlider
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSliderAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedAndesSlider: AndesSlider

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        spiedAndesSlider = spy(AndesSlider(context, 0f, 100f))
    }

    @Test
    fun `announceForAccessibility method is triggered when value has changed`() {
        AndesSliderAccessibilityEventDispatcher().notifyA11yChangedValue(
            spiedAndesSlider,
            5.0f,
            "dollars"
        )

        verify(spiedAndesSlider, times(1)).announceForAccessibility("Selected value: 5.0 dollars.")
    }
}
