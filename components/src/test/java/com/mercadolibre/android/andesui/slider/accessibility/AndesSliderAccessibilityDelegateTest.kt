package com.mercadolibre.android.andesui.slider.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.assertIsNull
import com.mercadolibre.android.andesui.slider.AndesSlider
import com.mercadolibre.android.andesui.slider.state.AndesSliderState
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesSliderAccessibilityDelegateTest {
    private lateinit var context: Context
    private lateinit var andesSlider: AndesSlider
    private lateinit var nodeInfo: AccessibilityNodeInfo

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        andesSlider = AndesSlider(context, null)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_MaterialComponents_NoActionBar_Bridge)
        activity.setContentView(andesSlider)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `ContentDescription Min, Max, Value`() {
        // GIVEN
        with(andesSlider) {
            min = 100f
            max = 200f
            value = 150f
        }

        // WHEN
        nodeInfo = andesSlider.createAccessibilityNodeInfo()

        // THEN
        nodeInfo assertIsNull false
        nodeInfo.contentDescription assertEquals ", from 100.0 to 200.0 . Selected value: 150.0 ."
        nodeInfo.isEnabled assertEquals true
    }

    @Test
    fun `ContentDescription Min, Max, Value, Suffix`() {
        // GIVEN
        with(andesSlider) {
            min = 100f
            max = 200f
            value = 150f
            accessibilityContentSuffix = "dollars"
        }

        // WHEN
        nodeInfo = andesSlider.createAccessibilityNodeInfo()

        // THEN
        nodeInfo assertIsNull false
        nodeInfo.contentDescription assertEquals ", from 100.0 to 200.0 dollars. Selected value: 150.0 dollars."
        nodeInfo.isEnabled assertEquals true
    }

    @Test
    fun `ContentDescription Title, Min, Max, Value, Suffix`() {
        // GIVEN
        with(andesSlider) {
            min = 100f
            max = 200f
            value = 150f
            accessibilityContentSuffix = "dollars"
            text = "Price"
        }

        // WHEN
        nodeInfo = andesSlider.createAccessibilityNodeInfo()

        // THEN
        nodeInfo assertIsNull false
        nodeInfo.contentDescription assertEquals "Price, from 100.0 to 200.0 dollars. Selected value: 150.0 dollars."
        nodeInfo.isEnabled assertEquals true
    }

    @Test
    fun `ContentDescription Title, Min, Max, Value, Suffix, Disabled`() {
        // GIVEN
        with(andesSlider) {
            min = 100f
            max = 200f
            value = 150f
            accessibilityContentSuffix = "dollars"
            text = "Price"
            state = AndesSliderState.DISABLED
        }

        // WHEN
        nodeInfo = andesSlider.createAccessibilityNodeInfo()

        // THEN
        nodeInfo assertIsNull false
        nodeInfo.contentDescription assertEquals "Price, from 100.0 to 200.0 dollars. Selected value: 150.0 dollars."
        nodeInfo.isEnabled assertEquals false
    }

    @Test
    fun `Component a11y role`() {
        andesSlider.accessibilityClassName assertEquals SeekBar::class.java.name
    }
}
