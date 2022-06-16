package com.mercadolibre.android.andesui.amountfield.utils

import android.content.Context
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
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
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesAmountFieldIntFormatterTest {

    private lateinit var context: Context
    private lateinit var editText: EditText
    private lateinit var formatter: AndesAmountFieldIntFormatter

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        editText = EditText(context)
        formatter = AndesAmountFieldIntFormatter(
            mock(),
            mock(),
            ',',
            2,
            null,
            false
        )
    }

    @Test
    fun `Given field empty, when entering correct input then value is correct`(){
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("50.0")

        editText.text.toString() assertEquals "50,0"
    }

    @Test
    fun `Given field with values, when entering digits then value is correct`(){
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("50")

        editText.emulateTypingWithKeyboard("1000")

        editText.text.toString() assertEquals "501.000"
    }

    @Test
    fun `Given field with values, when entering digits with decimal separator then value is correct`(){
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("50")

        editText.emulateTypingWithKeyboard("1000.4")

        editText.text.toString() assertEquals "501.000,4"
    }

    @Test
    fun `Given field with values, when deleting digits then value is correctly formatted`(){
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("5000000")

        editText.text.toString() assertEquals "5.000.000"

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals "500.000"
    }

    @Test
    fun `Given empty field, when deleting action entered, then the field value does not change`() {
        editText.addTextChangedListener(formatter)

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals ""
    }

    @Test
    fun `Given field with only one value, when deleting action entered, then the field is empty`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("1")

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals ""
    }

    @Test
    fun `Given field with values, when entering digits with decimal separator and more decimals than the permitted then value is correct`(){
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("50")

        editText.emulateTypingWithKeyboard("1000.405")

        editText.text.toString() assertEquals "501.000,40"
    }

    @Test
    fun `Given field with values, when entering more than one separator then the value is correct`(){
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("50")

        editText.emulateTypingWithKeyboard("10..00.405")

        editText.text.toString() assertEquals "5.010,00"
    }

    @Test
    fun `Given empty field with max value, when entering without reaching maxValue, then callback is called with false value`() {
        val amountListener = provideAmountListener()
        formatter = spy(
            AndesAmountFieldIntFormatter(
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
            AndesAmountFieldIntFormatter(
                mock(),
                amountListener,
                ',',
                2,
                "1000.00",
                false
            )
        )
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("2000")

        verify(amountListener, times(1)).onAmountChanged(true)
    }

    @Test
    fun `Given field, when entering values with blocked true, then final value is not changed`() {
        formatter = spy(
            AndesAmountFieldIntFormatter(
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

    @Test
    fun `Given field with values and dot decimal separator when entering value, then formatting is correct`() {
        formatter = AndesAmountFieldIntFormatter(
            mock(),
            mock(),
            '.',
            2,
            null,
            false
        )
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("200.1")

        editText.emulateTypingWithKeyboard("0")

        editText.text.toString() assertEquals "200.10"
    }

    @Test
    fun `Given field with values and comma thousand separator, when entering value, then formatting is correct`() {
        formatter = AndesAmountFieldIntFormatter(
            mock(),
            mock(),
            '.',
            2,
            null,
            false
        )
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("1000.5")

        editText.emulateTypingWithKeyboard("2")

        editText.text.toString() assertEquals "1,000.52"
    }

    @Test
    fun `Given field with value with both thousand and decimal separators, when deleting, then formatting is correct`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("1000.52")

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals "1.000,5"
    }

    @Test
    fun `Given field with value with decimal separator, when deleting, then formatting is correct`() {
        editText.addTextChangedListener(formatter)
        editText.emulateTypingWithKeyboard("100.52")

        editText.emulateDeleteKeyPressed()

        editText.text.toString() assertEquals "100,5"
    }

    @Test
    fun `Given empty field, when entering separator, then field is still empty`() {
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard(".")

        editText.text.toString() assertEquals ""
    }

    @Test
    fun `Given formatter with no decimals, when entering separator, then separator is not added`() {
        formatter = AndesAmountFieldIntFormatter(
            mock(),
            mock(),
            '.',
            0,
            null,
            false
        )
        editText.addTextChangedListener(formatter)

        editText.emulateTypingWithKeyboard("150.")

        editText.text.toString() assertEquals "150"
    }

    private fun provideAmountListener(): AmountListener {
        return spy(object : AmountListener {
            override fun onAmountChanged(isExceeded: Boolean) {
                // no-op
            }
        })
    }
}
