package com.mercadolibre.android.andesui.switch

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatusChecked
import com.mercadolibre.android.andesui.switch.status.AndesSwitchStatusUnchecked
import com.mercadolibre.android.andesui.switch.type.AndesSwitchType
import com.mercadolibre.android.andesui.switch.type.AndesSwitchTypeDisabled
import com.mercadolibre.android.andesui.switch.type.AndesSwitchTypeEnabled
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSwitchInterfacesTest {

    private lateinit var context: Context
    private lateinit var andesSwitch: AndesSwitch

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesSwitch = AndesSwitch(context)
    }

    @Test
    fun `testing andesSwitchStatus getter with status checked`() {
        andesSwitch.status = AndesSwitchStatus.CHECKED
        val checkedStatus = andesSwitch.status.status

        Assert.assertTrue(checkedStatus is AndesSwitchStatusChecked)
    }

    @Test
    fun `testing andesSwitchStatus getter with status unchecked`() {
        andesSwitch.status = AndesSwitchStatus.UNCHECKED
        val uncheckedStatus = andesSwitch.status.status

        Assert.assertTrue(uncheckedStatus is AndesSwitchStatusUnchecked)
    }

    @Test
    fun `testing andesSwitchType getter with type enabled`() {
        andesSwitch.type = AndesSwitchType.ENABLED
        val enabledType = andesSwitch.type.type

        Assert.assertTrue(enabledType is AndesSwitchTypeEnabled)
    }

    @Test
    fun `testing andesSwitchType getter with type disabled`() {
        andesSwitch.type = AndesSwitchType.DISABLED
        val disabledType = andesSwitch.type.type

        Assert.assertTrue(disabledType is AndesSwitchTypeDisabled)
    }
}
