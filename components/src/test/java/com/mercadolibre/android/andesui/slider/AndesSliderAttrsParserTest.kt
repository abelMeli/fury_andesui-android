package com.mercadolibre.android.andesui.slider

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.buildAttributeSet
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrsParser
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.toBitmap
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesSliderAttrsParserTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `build andes slider default`() {
        // GIVEN
        val attrs = buildAttributeSet { }

        // WHEN
        val andesSliderAttrs = AndesSliderAttrsParser.parse(context, attrs)

        // THEN
        with(andesSliderAttrs) {
            andesSliderTitle assertEquals null
            andesSliderState assertEquals AndesSliderState.IDLE
            andesSliderMin assertEquals 0F
            andesSliderMax assertEquals 100F
            andesSliderValue assertEquals 0F
            andesSliderSteps assertEquals AndesSliderSteps.None
            andesSliderType assertEquals AndesSliderType.Simple
        }
    }

    @Test
    fun `AndesSlider when SliderTypeLimits without passed content`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesSliderType, "1002")
        }

        // WHEN
        val andesSliderAttrs = AndesSliderAttrsParser.parse(context, attrs)

        // THEN
        with(andesSliderAttrs) {
            andesSliderTitle assertEquals null
            andesSliderState assertEquals AndesSliderState.IDLE
            andesSliderMin assertEquals 0F
            andesSliderMax assertEquals 100F
            andesSliderValue assertEquals 0F
            andesSliderSteps assertEquals AndesSliderSteps.None
            assert(andesSliderType is AndesSliderType.Limits)
            andesSliderType.type.leftComponent(context) assertIsNull true
            andesSliderType.type.rightComponent(context) assertIsNull true
        }
    }

    @Test
    fun `AndesSlider Disabled and SliderSteps was passed`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesSliderSteps, "11")
            addAttribute(R.attr.andesSliderState, "3001")
        }

        // WHEN
        val andesSliderAttrs = AndesSliderAttrsParser.parse(context, attrs)

        // THEN
        with(andesSliderAttrs) {
            andesSliderTitle assertEquals null
            andesSliderState assertEquals AndesSliderState.DISABLED
            andesSliderMin assertEquals 0F
            andesSliderMax assertEquals 100F
            andesSliderValue assertEquals 0F
            assert(andesSliderSteps is AndesSliderSteps.Custom)
            andesSliderType assertEquals AndesSliderType.Simple
            with(andesSliderSteps.type) {
                isTickVisible() assertEquals true
                stepSize(0F, 100F) assertEquals 10F
            }
        }
    }

    @Test
    fun `AndesSlider when SliderTypeIcon`() {
        val givenDrawable = ContextCompat.getDrawable(context, R.drawable.andes_ui_charge_24)!!
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesSliderType, "1001")
            addAttribute(R.attr.andesSliderLeftContent, "@drawable/andes_ui_charge_24")
            addAttribute(R.attr.andesSliderRightContent, "@drawable/andes_ui_charge_24")
        }

        // WHEN
        val andesSliderAttrs = AndesSliderAttrsParser.parse(context, attrs)

        // THEN
        with(andesSliderAttrs) {
            andesSliderTitle assertEquals null
            andesSliderState assertEquals AndesSliderState.IDLE
            andesSliderMin assertEquals 0F
            andesSliderMax assertEquals 100F
            andesSliderValue assertEquals 0F
            andesSliderSteps assertEquals AndesSliderSteps.None
            with(andesSliderType.type.leftComponent(context) as ImageView) {
                drawable.toBitmap() assertEquals givenDrawable.toBitmap()
                this assertIsNull false
            }

            with(andesSliderType.type.rightComponent(context) as ImageView) {
                drawable.toBitmap() assertEquals givenDrawable.toBitmap()
                this assertIsNull false
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `AndesSlider when SliderSteps value passed has exceeded`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesSliderSteps, "20")
        }

        // WHEN
        AndesSliderAttrsParser.parse(context, attrs)

        // THEN: expected IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Can not build AndesSlider when SliderTypeIcon without passed drawable`() {
        // GIVEN
        val attrs = buildAttributeSet {
            addAttribute(R.attr.andesSliderType, "1001")
        }

        // WHEN
        AndesSliderAttrsParser.parse(context, attrs)

        // THEN: expected IllegalArgumentException
    }
}
