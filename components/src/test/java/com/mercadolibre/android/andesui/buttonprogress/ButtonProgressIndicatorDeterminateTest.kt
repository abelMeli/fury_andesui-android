package com.mercadolibre.android.andesui.buttonprogress

import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.button.hierarchy.AndesLoudButtonHierarchy
import com.mercadolibre.android.andesui.button.hierarchy.AndesTransparentButtonHierarchy
import com.mercadolibre.android.andesui.buttonprogress.factory.AndesButtonProgressConfigurationFactory
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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
class ButtonProgressIndicatorDeterminateTest {
    private var context = RuntimeEnvironment.application
    private lateinit var buttonProgress: AndesButtonProgressIndicatorDeterminate
    private val configFactory = spy(AndesButtonProgressConfigurationFactory)

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
    }

    @Test
    fun `Only context constructor`() {
        buttonProgress = AndesButtonProgressIndicatorDeterminate(context)
        assertEquals(buttonProgress.progress, 0)
        assertEquals(buttonProgress.from, 0)
        assertEquals(buttonProgress.to, 200)
        assertEquals(buttonProgress.duration, 5000L)
        assertEquals(buttonProgress.andesButtonHierarchy, AndesLoudButtonHierarchy)
    }

    @Test
    fun `Duration 1000 milliseconds and Hierarchy Loud`() {
        val config = configFactory.create(5, 100, 1000L, AndesLoudButtonHierarchy)

        assertEquals(config.from, 5)
        assertEquals(config.to, 100)
        assertEquals(config.duration, 1000L)
        assertEquals(config.andesButtonHierarchy, AndesLoudButtonHierarchy)
        assertTrue(config.isProgressAnimationAllowed)
        assertEquals(config.progressVisibility, View.VISIBLE)
    }

    @Test
    fun `Duration 1000 milliseconds and Hierarchy Transparent`() {
        val config = configFactory.create(5, 100, 1000L, AndesTransparentButtonHierarchy)

        assertEquals(config.from, 5)
        assertEquals(config.to, 100)
        assertEquals(config.duration, 1000L)
        assertEquals(config.andesButtonHierarchy, AndesTransparentButtonHierarchy)
        assertFalse(config.isProgressAnimationAllowed)
        assertEquals(config.progressVisibility, View.GONE)
    }
}
