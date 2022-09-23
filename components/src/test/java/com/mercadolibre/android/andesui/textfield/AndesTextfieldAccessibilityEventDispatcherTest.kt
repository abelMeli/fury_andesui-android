package com.mercadolibre.android.andesui.textfield

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.textfield.accessibility.AndesTextfieldAccessibilityEventDispatcher
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
class AndesTextfieldAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedTextfield: AndesTextfield

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        spiedTextfield = spy(AndesTextfield(context))
    }

    @Test
    fun `announceForAccessibility method is triggered when text reaches 80 percent and count is bigger than minimum`() {
        val counter = 50
        val text = "a string with 40 characters to test func"
        val announceText = "10 caracteres restantes"
        val spiedEventDispatcher = spy(AndesTextfieldAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yTextThresholdReached(
                view = spiedTextfield,
                maxCharsCount = counter,
                text = text
        )

        verify(spiedTextfield, times(1)).announceForAccessibility(announceText)
    }

    @Test
    fun `announceForAccessibility method is not triggered when text reaches 80 percent but counter is smaller than minimum`() {
        val counter = 19
        val text = "123456789012345678"
        val announceText = "10 caracteres restantes"
        val spiedEventDispatcher = spy(AndesTextfieldAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yTextThresholdReached(
                view = spiedTextfield,
                maxCharsCount = counter,
                text = text
        )

        verify(spiedTextfield, times(0)).announceForAccessibility(announceText)
    }

    @Test
    fun `announceForAccessibility is not triggered when text does not reach 80 percent`() {
        val counter = 50
        val text = "string with 20 chars"
        val announceText = "10 caracteres restantes"
        val spiedEventDispatcher = spy(AndesTextfieldAccessibilityEventDispatcher())

        spiedEventDispatcher.notifyA11yTextThresholdReached(
                view = spiedTextfield,
                maxCharsCount = counter,
                text = text
        )

        verify(spiedTextfield, times(0)).announceForAccessibility(announceText)
    }
}
