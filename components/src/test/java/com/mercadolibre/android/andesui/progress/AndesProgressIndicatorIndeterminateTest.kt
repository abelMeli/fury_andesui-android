package com.mercadolibre.android.andesui.progress

import android.content.Context
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.progress.factory.AndesProgressAttrs
import com.mercadolibre.android.andesui.progress.factory.AndesProgressConfigurationFactory
import com.mercadolibre.android.andesui.progress.size.AndesProgressSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
@LooperMode(LooperMode.Mode.PAUSED)
class AndesProgressIndicatorIndeterminateTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var andesProgress: AndesProgressIndicatorIndeterminate
    private lateinit var andesProgressAttrs: AndesProgressAttrs
    private val configFactory = spy(AndesProgressConfigurationFactory)

    @Test
    fun `AndesProgress start from xml`() {
        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesProgressStart, "true")
            .build()

        andesProgress = spy(AndesProgressIndicatorIndeterminate(context, attrs))

        andesProgress.loadingSpinner().stop()
        // Verify stop end?
        shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `Only context constructor`() {
        andesProgress = AndesProgressIndicatorIndeterminate(context)
        assertEquals(andesProgress.size, AndesProgressSize.SMALL)
        assertEquals(andesProgress.tint, 0)
    }

    @Test
    fun `Size XLarge and color default`() {
        andesProgressAttrs = AndesProgressAttrs(AndesProgressSize.XLARGE, 0, false)
        val config = configFactory.create(context, andesProgressAttrs)

        assertEquals(config.size, 48F)
        assertEquals(config.tint, ContextCompat.getColor(context, R.color.andes_accent_color_500))
    }

    @Test
    fun `Size Large and color default`() {
        andesProgressAttrs = AndesProgressAttrs(AndesProgressSize.LARGE, 0, false)
        val config = configFactory.create(context, andesProgressAttrs)

        assertEquals(config.size, 32F)
        assertEquals(config.tint, ContextCompat.getColor(context, R.color.andes_accent_color_500))
    }

    @Test
    fun `Size Medium and color default`() {
        andesProgressAttrs = AndesProgressAttrs(AndesProgressSize.MEDIUM, 0, false)
        val config = configFactory.create(context, andesProgressAttrs)

        assertEquals(config.size, 24F)
        assertEquals(config.tint, ContextCompat.getColor(context, R.color.andes_accent_color_500))
    }

    @Test
    fun `Size Small and color default`() {
        andesProgressAttrs = AndesProgressAttrs(AndesProgressSize.SMALL, 0, false)
        val config = configFactory.create(context, andesProgressAttrs)

        assertEquals(config.size, 16F)
        assertEquals(config.tint, ContextCompat.getColor(context, R.color.andes_accent_color_500))
    }

    @Test
    fun `Size Small and color `() {
        andesProgressAttrs = AndesProgressAttrs(AndesProgressSize.SMALL,
            ContextCompat.getColor(context, R.color.andes_yellow_ml_500), false)
        val config = configFactory.create(context, andesProgressAttrs)

        assertEquals(config.size, 16F)
        assertEquals(config.tint, ContextCompat.getColor(context, R.color.andes_yellow_ml_500))
    }

    private fun AndesProgressIndicatorIndeterminate.loadingSpinner() =
        andesProgress.findViewById<LoadingSpinner>(R.id.andes_progress_indeterminate_spinner)
}
