package com.mercadolibre.android.andesui.inputstepper.factory

import android.content.Context
import android.view.LayoutInflater
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.databinding.AndesInputstepperBinding
import com.mercadolibre.android.andesui.inputstepper.enabledstate.AndesInputStepperEnabledState
import com.mercadolibre.android.andesui.inputstepper.size.AndesInputStepperSize
import com.mercadolibre.android.andesui.inputstepper.status.AndesInputStepperStatus
import com.mercadolibre.android.andesui.utils.Constants
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Constants.TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
class AndesInputStepperConfiguratorTest {

    private lateinit var binding: AndesInputstepperBinding
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        binding = AndesInputstepperBinding.inflate(LayoutInflater.from(context), null, false)
    }

    @Test
    fun verifySizeSetup() {
        val expectedHeight = context.resources.getDimension(R.dimen.andes_inputstepper_height_large).toInt()
        val configurator = Mockito.spy(AndesInputStepperSize.Large)

        configurator.applyConfig(binding)

        Assert.assertEquals(expectedHeight, binding.andesInputstepperContainer.maxHeight)
    }

    @Test
    fun verifyStatusSetup() {
        val expectedEnabled = true
        val configurator = Mockito.spy(AndesInputStepperStatus.Enabled)

        configurator.applyConfig(binding)

        Assert.assertEquals(expectedEnabled, binding.andesInputstepperContainer.isEnabled)
    }

    @Test
    fun verifyEnabledStateSetup() {
        val expectedEnabled = true
        val configurator = Mockito.spy(AndesInputStepperEnabledState.InRange)

        configurator.applyConfig(binding)

        Assert.assertEquals(expectedEnabled, binding.andesInputstepperActionRemove.isEnabled)
        Assert.assertEquals(expectedEnabled, binding.andesInputstepperActionAdd.isEnabled)
    }
}