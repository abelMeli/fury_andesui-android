package com.mercadolibre.android.andesui.amountfield.utils

import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.utils.emulateDeleteKeyPressed
import com.mercadolibre.android.andesui.utils.emulateTypingWithKeyboard
import com.nhaarman.mockitokotlin2.mock
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
class AndesAmountFieldDecimalFormatterTest {

    private lateinit var formatter: AndesAmountFieldDecimalFormatter
    private lateinit var editText: EditText
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        editText = EditText(context)
        formatter = spy(
            AndesAmountFieldDecimalFormatter(
                mock(),
                mock(),
                ',',
                2,
                null,
                false
            )
        )
    }

    @Test
    fun `Given empty field, when entering numbers, then final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("500")

        editText.text.toString() assertEquals "5,00"
    }

    @Test
    fun `Given empty field, when entering decimal separator dot, then final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard(".")

        editText.text.toString() assertEquals "0,00"
    }

    @Test
    fun `Given empty field, when entering decimal separator comma, then final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard(",")

        editText.text.toString() assertEquals "0,00"
    }

    @Test
    fun `Given field with values, when entering numbers, then final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("500")

        editText.emulateTypingWithKeyboard("000")

        editText.text.toString() assertEquals "5.000,00"
    }

    @Test
    fun `Given field with values, entering dot decimal separator, then field value does not change`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("500")

        editText.emulateTypingWithKeyboard(".")

        editText.text.toString() assertEquals "5,00"
    }

    @Test
    fun `Given field with values, entering comma decimal separator, then field value does not change`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("500")

        editText.emulateTypingWithKeyboard(",")

        editText.text.toString() assertEquals "5,00"
    }

    @Test
    fun `Given empty field, when deleting action entered, then the field value does not change`() {
        editText.addTextChangedListener(formatter)

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals ""
    }

    @Test
    fun `Given field with values, when deleting action entered, then the final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("500")

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals "0,50"
    }

    @Test
    fun `Given field with values, when deleting until empty field, then the final value is correctly formatted`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("500")

        editText.emulateDeleteKeyPressed()
        editText.emulateDeleteKeyPressed()
        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals ""
    }

    @Test
    fun `Given empty field with max value, when entering without reaching maxValue, then callback is called with false value`() {
        val amountListener = provideAmountListener()
        formatter = spy(
            AndesAmountFieldDecimalFormatter(
                mock(),
                amountListener,
                ',',
                2,
                "1000.00",
                false
            )
        )
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("200")

        verify(amountListener, times(3)).onAmountChanged(false)
    }

    @Test
    fun `Given empty field and max value, when entering and reaching maxValue, then callback is called with true value`() {
        val amountListener = provideAmountListener()
        formatter = spy(
            AndesAmountFieldDecimalFormatter(
                mock(),
                amountListener,
                ',',
                2,
                "1000.00",
                false
            )
        )
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("200000")

        verify(amountListener, times(1)).onAmountChanged(true)
    }

    @Test
    fun `Given field, when entering values with blocked true, then final value is not changed`() {
        formatter = spy(
            AndesAmountFieldDecimalFormatter(
                mock(),
                mock(),
                ',',
                2,
                null,
                true
            )
        )
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("200000")

        editText.text.toString() assertEquals ""
    }

    private fun provideAmountListener(): AmountListener {
        return spy(object : AmountListener {
            override fun onAmountChanged(isExceeded: Boolean) {
                // no-op
            }
        })
    }
}
