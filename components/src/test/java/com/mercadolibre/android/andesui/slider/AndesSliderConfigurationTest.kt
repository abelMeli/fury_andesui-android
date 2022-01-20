package com.mercadolibre.android.andesui.slider

import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.slider.factory.AndesSliderAttrs
import com.mercadolibre.android.andesui.slider.factory.AndesSliderConfigurationFactory
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.slider.steps.AndesSliderSteps
import com.mercadolibre.android.andesui.slider.type.AndesSliderType
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.android.andesui.textview.style.AndesTextViewStyle
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesSliderConfigurationTest {

    private lateinit var context: Context
    private lateinit var attrs: AndesSliderAttrs

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Config None, Simple, IDLE, without Title`() {
        // GIVEN
        attrs = AndesSliderAttrs(
            andesSliderTitle = null,
            andesSliderState = EXPECTED_STATE_ENABLED,
            andesSliderMin = EXPECTED_MIN,
            andesSliderMax = EXPECTED_MAX,
            andesSliderValue = EXPECTED_VALUE,
            andesSliderSteps = AndesSliderSteps.None,
            andesSliderType = AndesSliderType.Simple,
            andesSliderA11ySuffix = EXPECTED_A11Y_SUFFIX
        )

        // WHEN
        val config = AndesSliderConfigurationFactory.create(attrs, context)

        // THEN
        with(config) {
            text assertEquals null
            state assertEquals EXPECTED_STATE_ENABLED
            min assertEquals EXPECTED_MIN
            max assertEquals EXPECTED_MAX
            value assertEquals EXPECTED_VALUE
            type assertEquals AndesSliderType.Simple
            rightComponent assertIsNull true
            leftComponent assertIsNull true
            isTickVisible assertEquals false
            jumpSteps assertEquals 1F
        }
    }

    @Test
    fun `Config Custom, Simple, IDLE, with Title`() {
        // GIVEN
        attrs = AndesSliderAttrs(
            andesSliderTitle = EXPECTED_TITLE,
            andesSliderState = EXPECTED_STATE_ENABLED,
            andesSliderMin = EXPECTED_MIN,
            andesSliderMax = EXPECTED_MAX,
            andesSliderValue = EXPECTED_VALUE,
            andesSliderSteps = AndesSliderSteps.Custom(11),
            andesSliderType = AndesSliderType.Simple,
            andesSliderA11ySuffix = EXPECTED_A11Y_SUFFIX
        )

        // WHEN
        val config = AndesSliderConfigurationFactory.create(attrs, context)

        // THEN
        with(config) {
            text assertEquals EXPECTED_TITLE
            state assertEquals EXPECTED_STATE_ENABLED
            min assertEquals EXPECTED_MIN
            max assertEquals EXPECTED_MAX
            value assertEquals EXPECTED_VALUE
            type assertEquals AndesSliderType.Simple
            rightComponent assertIsNull true
            leftComponent assertIsNull true
            isTickVisible assertEquals true
            jumpSteps assertEquals 10F
        }
    }

    @Test
    fun `Config None, Icon, DISABLED, with Title`() {
        // GIVEN
        val givenDrawable = ContextCompat.getDrawable(context, R.drawable.andes_ui_charge_24)!!

        attrs = AndesSliderAttrs(
            andesSliderTitle = EXPECTED_TITLE,
            andesSliderState = AndesSliderState.DISABLED,
            andesSliderMin = EXPECTED_MIN,
            andesSliderMax = EXPECTED_MAX,
            andesSliderValue = EXPECTED_VALUE,
            andesSliderSteps = AndesSliderSteps.None,
            andesSliderType = AndesSliderType.Icon(givenDrawable, givenDrawable),
            andesSliderA11ySuffix = EXPECTED_A11Y_SUFFIX
        )

        // WHEN
        val config = AndesSliderConfigurationFactory.create(attrs, context)

        // THEN
        with(config) {
            text assertEquals EXPECTED_TITLE
            state assertEquals AndesSliderState.DISABLED
            min assertEquals EXPECTED_MIN
            max assertEquals EXPECTED_MAX
            value assertEquals EXPECTED_VALUE
            assert(type is AndesSliderType.Icon)
            (leftComponent as ImageView).drawable assertEquals R.drawable.andes_ui_charge_24
            (rightComponent as ImageView).drawable assertEquals R.drawable.andes_ui_charge_24
            isTickVisible assertEquals false
            jumpSteps assertEquals 1F
        }
    }

    @Test
    fun `Config None, Limits, IDLE, with Title`() {
        // GIVEN
        val expectedLeftLimit = "0%"
        val expectedRightLimit = "100%"
        val expectedType = AndesSliderType.Limits(expectedLeftLimit, expectedRightLimit)

        attrs = AndesSliderAttrs(
            andesSliderTitle = EXPECTED_TITLE,
            andesSliderState = EXPECTED_STATE_ENABLED,
            andesSliderMin = EXPECTED_MIN,
            andesSliderMax = EXPECTED_MAX,
            andesSliderValue = EXPECTED_VALUE,
            andesSliderSteps = AndesSliderSteps.None,
            andesSliderType = expectedType,
            andesSliderA11ySuffix = EXPECTED_A11Y_SUFFIX
        )

        // WHEN
        val config = AndesSliderConfigurationFactory.create(attrs, context)

        // THEN
        with(config) {
            text assertEquals EXPECTED_TITLE
            state assertEquals EXPECTED_STATE_ENABLED
            min assertEquals EXPECTED_MIN
            max assertEquals EXPECTED_MAX
            value assertEquals EXPECTED_VALUE
            type assertEquals expectedType
            assert(rightComponent is AndesTextView)
            with(rightComponent as AndesTextView) {
                text.toString() assertEquals expectedRightLimit
                style assertEquals AndesTextViewStyle.BodyS
            }
            assert(leftComponent is AndesTextView)
            with(leftComponent as AndesTextView) {
                text.toString() assertEquals expectedLeftLimit
                style assertEquals AndesTextViewStyle.BodyS
            }
            isTickVisible assertEquals false
            jumpSteps assertEquals 1F
        }
    }

    companion object {
        private val EXPECTED_STATE_ENABLED = AndesSliderState.IDLE
        private const val EXPECTED_TITLE = "Title"
        private const val EXPECTED_MIN = 0F
        private const val EXPECTED_VALUE = 50F
        private const val EXPECTED_MAX = 100F
        private const val EXPECTED_A11Y_SUFFIX = "dollars"
    }
}
