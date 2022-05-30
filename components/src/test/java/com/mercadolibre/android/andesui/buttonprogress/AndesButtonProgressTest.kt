package com.mercadolibre.android.andesui.buttonprogress

import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.button.AndesButton
import com.mercadolibre.android.andesui.buttonprogress.status.AndesButtonProgressAction
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesButtonProgressTest {
    private var context = RuntimeEnvironment.application
    private lateinit var andesButton: AndesButton

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            SoLoader.setInTestMode()
        }
    }

    @Before
    fun setUp() {
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
            // other setters
            .setRequestListeners(requestListeners)
            .build()
        Fresco.initialize(context, config)
        FLog.setMinimumLoggingLevel(FLog.VERBOSE)
    }

    @Test
    fun `Only context constructor`() {
        andesButton = AndesButton(context)
        assertEquals(andesButton.getProgressIndicatorValue(), 0)
    }

    @Test
    fun `Start progress`() {
        andesButton = AndesButton(context)
        andesButton.progressStatus = AndesButtonProgressAction.START
        assertTrue(andesButton.getProgressIndicatorValue() > 0)
    }

    @Test
    fun `Pause progress`() {
        andesButton = AndesButton(context)
        andesButton.progressStatus = AndesButtonProgressAction.PAUSE
    }

    @Test
    fun `Resume progress`() {
        andesButton = AndesButton(context)
        andesButton.progressStatus = AndesButtonProgressAction.RESUME
    }

    @Test
    fun `Cancel progress`() {
        andesButton = AndesButton(context)
        andesButton.progressStatus = AndesButtonProgressAction.CANCEL
    }

    @Test
    fun `Start progress with different values`() {
        andesButton = AndesButton(context)
        andesButton.setProgressIndicatorFrom(40)
        andesButton.setProgressIndicatorTo(100)
        andesButton.setProgressIndicatorDuration(5000)
        andesButton.progressStatus = AndesButtonProgressAction.START
        assertTrue(andesButton.getProgressIndicatorValue() > 40)
    }
}
