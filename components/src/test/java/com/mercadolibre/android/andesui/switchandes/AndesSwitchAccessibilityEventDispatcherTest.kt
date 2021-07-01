package com.mercadolibre.android.andesui.switchandes

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.switchandes.accessibility.AndesSwitchAccessibilityEventDispatcher
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
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
class AndesSwitchAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedAndesSwitch: AndesSwitch

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        spiedAndesSwitch = spy(AndesSwitch(context))
    }

    @Test
    fun `announceForAccessibility method is triggered and announce text is correct with status unchecked`() {
        val uncheckedAnnounce = context.resources.getString(R.string.andes_switch_status_unchecked)
        val spiedEventDispatcher = spy(AndesSwitchAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yStatusChanged(spiedAndesSwitch, spiedAndesSwitch.status)

        verify(spiedAndesSwitch, times(1)).announceForAccessibility(uncheckedAnnounce)
    }

    @Test
    fun `announceForAccessibility method is triggered and announce text is correct with status checked`() {
        spiedAndesSwitch.status = AndesSwitchStatus.CHECKED
        val checkedAnnounce = context.resources.getString(R.string.andes_switch_status_checked)
        val spiedEventDispatcher = spy(AndesSwitchAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yStatusChanged(spiedAndesSwitch, spiedAndesSwitch.status)

        verify(spiedAndesSwitch, times(1)).announceForAccessibility(checkedAnnounce)
    }
}
