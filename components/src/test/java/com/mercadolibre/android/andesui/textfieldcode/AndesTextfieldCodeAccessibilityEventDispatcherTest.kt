package com.mercadolibre.android.andesui.textfieldcode

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.textfield.AndesTextfieldCode
import com.mercadolibre.android.andesui.textfield.accessibility.AndesTextfieldCodeAccessibilityEventDispatcher
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
class AndesTextfieldCodeAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedTextfieldCode: AndesTextfieldCode

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
        spiedTextfieldCode = spy(AndesTextfieldCode(context))
    }

    @Test
    fun `announceForAccessibility method is triggered when textfieldCode is full`() {
        val spiedEventDispatcher = spy(AndesTextfieldCodeAccessibilityEventDispatcher())
        val announcedText = "Campo de texto completo"

        spiedEventDispatcher.notifyA11yTextCompleted(spiedTextfieldCode, true)

        verify(spiedTextfieldCode, times(1)).announceForAccessibility(announcedText)
    }

    @Test
    fun `announceForAccessibility method is not triggered when textfieldCode is not full`() {
        val spiedEventDispatcher = spy(AndesTextfieldCodeAccessibilityEventDispatcher())
        val announcedText = "Campo de texto completo"

        spiedEventDispatcher.notifyA11yTextCompleted(spiedTextfieldCode, false)

        verify(spiedTextfieldCode, times(0)).announceForAccessibility(announcedText)
    }
}
