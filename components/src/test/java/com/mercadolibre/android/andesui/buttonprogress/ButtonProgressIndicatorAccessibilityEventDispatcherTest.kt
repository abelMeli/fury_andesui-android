package com.mercadolibre.android.andesui.buttonprogress

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.soloader.SoLoader
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.progress.accessibility.AndesProgressIndicatorAccessibilityEventDispatcher
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
class ButtonProgressIndicatorAccessibilityEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var spiedPrpgress: AndesButtonProgressIndicatorDeterminate

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SoLoader.setInTestMode()
        val requestListeners = setOf<RequestListener>(RequestLoggingListener())
        val config = ImagePipelineConfig.newBuilder(context)
                .setRequestListeners(requestListeners)
                .build()
        Fresco.initialize(context, config)
        spiedPrpgress = spy(AndesButtonProgressIndicatorDeterminate(context))
    }

    @Test
    fun `AdvertForAccessibility when is not loading`() {
        val announceText = context.resources.getString(R.string.andes_progressindicator_is_loading)
        val spiedEventDispatcher = spy(AndesProgressIndicatorAccessibilityEventDispatcher())
        spiedEventDispatcher.notifyA11ySpinnerIsLoading(
            view = spiedPrpgress,
            isLoading = false
        )
        verify(spiedPrpgress, times(0)).announceForAccessibility(announceText)
    }

    @Test
    fun `AdvertForAccessibility when is loading`() {
        val announceText = context.resources.getString(R.string.andes_progressindicator_is_loading)
        val spiedEventDispatcher = spy(AndesProgressIndicatorAccessibilityEventDispatcher())
        spiedEventDispatcher.notifyA11ySpinnerIsLoading(
            view = spiedPrpgress,
            isLoading = true
        )
        verify(spiedPrpgress, times(1)).announceForAccessibility(announceText)
    }
}
