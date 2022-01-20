package com.mercadolibre.android.andesui.slider

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildAttributeSet
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSliderTest {

    private lateinit var andesSlider: AndesSlider

    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        andesSlider = AndesSlider(
            context = context,
            min = MIN,
            max = MAX,
            value = VALUE,
            state = AndesSliderState.IDLE
        )
    }

    @Test
    fun `andes slider default values`() {
        assertNotNull(andesSlider)
        with(andesSlider) {
            type assertEquals TYPE
            steps assertEquals STEPS
            value assertEquals VALUE
            state assertEquals STATE_ENABLED
            text assertIsNull true
        }
    }

    @Test
    fun `andes slider attr set`() {
        val attrs = buildAttributeSet()
            .addAttribute(R.attr.andesSliderTitle, "Title")
            .addAttribute(R.attr.andesSliderMin, "$MIN")
            .addAttribute(R.attr.andesSliderMax, "$MAX")
            .addAttribute(R.attr.andesSliderValue, "$MIN")
            .addAttribute(R.attr.andesSliderState, "3000")
            .addAttribute(R.attr.andesSliderType, "1000")
            .addAttribute(R.attr.andesSliderSteps, "6")
            .build()

        andesSlider = AndesSlider(context, attrs)
        assertNotNull(andesSlider)
        with(andesSlider) {
            type assertEquals TYPE
            min assertEquals MIN
            max assertEquals MAX
            value assertEquals MIN
            assert(steps is AndesSliderSteps.Custom)
            steps.type.stepSize(0F, 100F) assertEquals 20F
            steps.type.isTickVisible() assertEquals true
            state assertEquals STATE_ENABLED
            text.toString() assertEquals "Title"
            text assertIsNull false
        }
    }

    companion object {
        private val STATE_ENABLED = AndesSliderState.IDLE
        private const val MIN = 0F
        private const val VALUE = 25F
        private const val MAX = 100F
        private val STEPS = AndesSliderSteps.None
        private val TYPE = AndesSliderType.Simple
    }
}
