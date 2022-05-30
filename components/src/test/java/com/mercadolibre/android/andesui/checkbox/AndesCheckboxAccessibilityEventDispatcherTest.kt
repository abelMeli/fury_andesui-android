package com.mercadolibre.android.andesui.checkbox

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.checkbox.accessibility.AndesCheckboxAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.checkbox.status.AndesCheckboxStatus
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesCheckboxAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedAndesCheckbox: AndesCheckbox

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        spiedAndesCheckbox = spy(AndesCheckbox(context, "Test"))
    }

    @Test
    fun `announceForAccessibility method is triggered and announce text is correct with status unselected`() {
        val uncheckedAnnounce = context.resources.getString(R.string.andes_checkbox_status_unselected)
        val spiedEventDispatcher = spy(AndesCheckboxAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yStatusChanged(spiedAndesCheckbox, spiedAndesCheckbox.status)

        verify(spiedAndesCheckbox, times(1)).announceForAccessibility(uncheckedAnnounce)
    }

    @Test
    fun `announceForAccessibility method is triggered and announce text is correct with status selected`() {
        spiedAndesCheckbox.status = AndesCheckboxStatus.SELECTED
        val checkedAnnounce = context.resources.getString(R.string.andes_checkbox_status_selected)
        val spiedEventDispatcher = spy(AndesCheckboxAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yStatusChanged(spiedAndesCheckbox, spiedAndesCheckbox.status)

        verify(spiedAndesCheckbox, times(1)).announceForAccessibility(checkedAnnounce)
    }
}
