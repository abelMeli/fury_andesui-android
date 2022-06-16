package com.mercadolibre.android.andesui.amountfield.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.amountfield.entrytype.AndesAmountFieldEntryType
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.utils.assertEquals
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
class AndesAmountFieldSimpleAccessibilityDelegateTest {

    private lateinit var context: Context
    private lateinit var nodeInfo: AccessibilityNodeInfo
    private lateinit var amountField: AndesAmountFieldSimple

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        amountField = AndesAmountFieldSimple(context)
        setupActivityForTesting()
    }

    private fun setupActivityForTesting() {
        val robolectricActivity = Robolectric.buildActivity(AppCompatActivity::class.java).create()
        val activity = robolectricActivity.get()
        activity.setTheme(R.style.Theme_AppCompat)
        activity.setContentView(amountField)
        robolectricActivity.start().postCreate(null).resume().visible()
    }

    @Test
    fun `Given component money with suffix, helper and idle state, when generating content description, the value is correct`() {
        amountField.apply {
            suffixText = "/hora"
            helperText = "helper"
        }

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals "Pesos /hora, , helper"
    }

    @Test
    fun `Given component money with suffix, a11y suffix, helper and idle state, when generating content description, the value is correct`() {
        amountField.apply {
            suffixText = "/hora"
            helperText = "helper"
            suffixA11yText = "por hora"
        }

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals "Pesos por hora, , helper"
    }

    @Test
    fun `Given component percentage with helper and idle state, when generating content description, the value is correct`() {
        amountField.apply {
            helperText = "helper"
            entryType = AndesAmountFieldEntryType.PERCENTAGE
        }

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals " %, , helper"
    }

    @Test
    fun `Given component created only with context, when generating content description, the value is correct`() {
        amountField.apply {
            suffixText = null
            helperText = null
        }

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals "Pesos , , "
    }

    @Test
    fun `Given component with error state, when generating content description, the value is correct`() {
        amountField.apply {
            state = AndesAmountFieldState.Error
        }

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals "Pesos , Error, "
    }

    @Test
    fun `Given component in amount exceeded state, when generating content description, the value is correct`() {
        amountField.apply {
            state = AndesAmountFieldState.AmountExceeded
            helperText = "helper"
        }

        val amountExceededText = context.resources.getString(R.string.andes_amount_field_exceeded_text)

        nodeInfo = amountField.createAccessibilityNodeInfo()

        nodeInfo.contentDescription assertEquals "Pesos , Error, $amountExceededText"
    }
}
