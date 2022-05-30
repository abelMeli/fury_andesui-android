package com.mercadolibre.android.andesui.switchandes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatus
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatusChecked
import com.mercadolibre.android.andesui.switchandes.status.AndesSwitchStatusUnchecked
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchType
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchTypeDisabled
import com.mercadolibre.android.andesui.switchandes.type.AndesSwitchTypeEnabled
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
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
