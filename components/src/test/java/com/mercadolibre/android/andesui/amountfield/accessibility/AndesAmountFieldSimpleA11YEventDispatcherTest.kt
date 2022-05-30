package com.mercadolibre.android.andesui.amountfield.accessibility

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.amountfield.AndesAmountFieldSimple
import com.mercadolibre.android.andesui.amountfield.state.AndesAmountFieldState
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesAmountFieldSimpleA11YEventDispatcherTest {

    private lateinit var context: Context
    private lateinit var amountField: AndesAmountFieldSimple
    private val a11yEventDispatcher = spy(AndesAmountFieldSimpleMoneyA11yEventDispatcher())

    init {
        MockConfigProvider.configure()
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        amountField = spy(AndesAmountFieldSimple(context))
    }

    @Test
    fun `Given a11y dispatcher, when calling announce with error state, then message to a11y is correctly sent`() {
        val textToSend = "helper text"
        val errorText = context.resources.getString(R.string.andes_amount_field_error_text)

        a11yEventDispatcher.notifyNewStateToA11y(amountField, AndesAmountFieldState.Error, textToSend)

        verify(amountField, times(1)).announceForAccessibility("$errorText, $textToSend")
    }

    @Test
    fun `Given a11y dispatcher, when calling announce with idle state, then message to a11y is not sent`() {
        val textToSend = "helper text"
        val errorText = context.resources.getString(R.string.andes_amount_field_error_text)

        a11yEventDispatcher.notifyNewStateToA11y(amountField, AndesAmountFieldState.Idle, textToSend)

        verify(amountField, times(0)).announceForAccessibility("$errorText, $textToSend")
    }
}
