package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.view.MotionEventBuilder
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.databinding.AndesLayoutMoneyAmountBinding
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.typeface.getFontOrDefault
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesMoneyAmountTest {

    private var context = ApplicationProvider.getApplicationContext<Context>()

    init {
        MockConfigProvider.configure()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Build SUPERSCRIPT, SIZE_12 throws exception`() {
        AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.ARS,
            size = AndesMoneyAmountSize.SIZE_12,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        )
    }


    fun `Build SUPERSCRIPT, SIZE_14 not throws exception`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.ARS,
            size = AndesMoneyAmountSize.SIZE_14,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 100"
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Build SUPERSCRIPT, Crypto currency throws exception`() {
        AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.BTC,
            size = AndesMoneyAmountSize.SIZE_14,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Build NONE, Crypto currency throws exception`() {
        AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.BTC,
            size = AndesMoneyAmountSize.SIZE_14,
            style = AndesMoneyAmountDecimalsStyle.NONE
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Build Suffix, Size 12 throws exception`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.ARS,
            size = AndesMoneyAmountSize.SIZE_12,
            style = AndesMoneyAmountDecimalsStyle.NONE
        )
        moneyAmount.setSuffix(SpannableStringBuilder("/unidad"),"por unidad")
    }

    @Test
    fun `NEGATIVE, NONE, ARS, AR, no zero decimals`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.56,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.NEGATIVE,
            style = AndesMoneyAmountDecimalsStyle.NONE,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.AR
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "- $ 1.235"
        }
    }

    @Test
    fun `POSITIVE, NORMAL, ARS, AR, no zero decimals`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.00,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NORMAL,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.AR
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1.234"
        }
    }

    @Test
    fun `POSITIVE, NORMAL, ARS, MX, zero decimals`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.00,
            showZerosDecimal = true,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NORMAL,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1,234.00"
        }
    }

    @Test
    fun `NEGATIVE, SUPERSCRIPT, ARS, MX, zero decimals`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.15,
            showZerosDecimal = true,
            type = AndesMoneyAmountType.NEGATIVE,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "- $ 1,23415"
        }
    }

    @Test
    fun `PREVIOUS, SUPERSCRIPT, ARS, MX, zero decimals`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.15,
            showZerosDecimal = true,
            type = AndesMoneyAmountType.PREVIOUS,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1,23415"
            (moneyAmountText.text as SpannedString).getSpans(
                0,
                moneyAmountText.text.length,
                Any::class.java
            ).size assertEquals 3 // three spans: amountsize + superscript + strike
        }
    }

    @Test
    fun `PREVIOUS, SUPERSCRIPT, ARS, MX, zero decimals, suffix`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.15,
            showZerosDecimal = true,
            type = AndesMoneyAmountType.PREVIOUS,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        moneyAmount.setSuffix(SpannableStringBuilder("/unidad"),"Por unidad")
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1,23415/unidad"
        }
    }

    @Test
    fun `PREVIOUS, SUPERSCRIPT, ARS, MX, zero decimals, suffix, text color`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.15,
            showZerosDecimal = true,
            type = AndesMoneyAmountType.PREVIOUS,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        moneyAmount.setSuffix(SpannableStringBuilder("/unidad"),"Por unidad")
        moneyAmount.setTextColor(R.color.andes_red_500)
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1,23415/unidad"
        }
    }

    @Test
    fun `PREVIOUS, SUPERSCRIPT, ARS, MX, suffix, text color`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.15,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.PREVIOUS,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.MX
        )
        moneyAmount.setSuffix(SpannableStringBuilder("/unidad"),"Por unidad")
        moneyAmount.setTextColor(AndesColor(R.color.andes_blue_ml_500, 1F))

        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1,23415/unidad"
        }
    }

    @Test
    fun `POSITIVE, NORMAL, BTC, with image`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 0.00000001,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NORMAL,
            currency = AndesMoneyAmountCurrency.BTC,
            country = AndesCountry.UY
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "BTC 0,00000001"
            moneyAmountIcon.visibility = View.VISIBLE
        }
    }

    @Test
    fun `POSITIVE, NORMAL, BTC, without image`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 0.00000001,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NORMAL,
            currency = AndesMoneyAmountCurrency.BTC,
            country = AndesCountry.UY
        )
        moneyAmount.showIcon = false
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "BTC 0,00000001"
            moneyAmountIcon.visibility = View.GONE
        }
    }

    @Test
    fun `POSITIVE, NORMAL, ARS, without image`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.0,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NONE,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.UY
        )
        moneyAmount.showIcon = false
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1.234"
            moneyAmountIcon.visibility = View.GONE
        }
    }

    @Test
    fun `POSITIVE, NORMAL, ARS, with image`() {
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.0,
            showZerosDecimal = false,
            type = AndesMoneyAmountType.POSITIVE,
            style = AndesMoneyAmountDecimalsStyle.NONE,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.UY
        )
        moneyAmount.showIcon = true
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))
        with(binding) {
            moneyAmountText.text.toString() assertEquals "$ 1.234"
            moneyAmountIcon.visibility = View.GONE
        }
    }

    @Test
    fun `AndesMoneyAmountDiscount size greater than 32 throws exception setter`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            val moneyAmount = AndesMoneyAmount(
                context = context,
                amount = 1234.0,
                showZerosDecimal = false,
                type = AndesMoneyAmountType.POSITIVE,
                style = AndesMoneyAmountDecimalsStyle.NONE,
                currency = AndesMoneyAmountCurrency.ARS,
                country = AndesCountry.UY
            )
            moneyAmount.size = AndesMoneyAmountSize.SIZE_12
            moneyAmount.setSuffix(SpannableStringBuilder("/unidad"), "por unidad.")
        }
    }

    @Test
    fun `dispatchTouchEvent, sends MotionEvent To Parent`() {
        val moneyAmount = Mockito.spy(
            AndesMoneyAmount(
                context = context,
                amount = 1234.0,
                showZerosDecimal = false,
                type = AndesMoneyAmountType.POSITIVE,
                style = AndesMoneyAmountDecimalsStyle.NONE,
                currency = AndesMoneyAmountCurrency.ARS,
                country = AndesCountry.UY
            )
        )
        val clickMotionEvent = MotionEventBuilder
            .newBuilder()
            .setAction(MotionEvent.ACTION_DOWN)
            .build()

        clickMotionEvent.action assertEquals MotionEvent.ACTION_DOWN
        val isEventIntercepted = moneyAmount.dispatchTouchEvent(clickMotionEvent)

        Assert.assertFalse(isEventIntercepted)
        verify(moneyAmount, times(1)).dispatchTouchEvent(clickMotionEvent)
    }

    @Test
    fun `dispatchTouchEvent, not sends MotionEvent To Parent`() {
        val moneyAmount = Mockito.spy(
            AndesMoneyAmount(
                context = context,
                amount = 1234.0,
                showZerosDecimal = false,
                type = AndesMoneyAmountType.POSITIVE,
                style = AndesMoneyAmountDecimalsStyle.NONE,
                currency = AndesMoneyAmountCurrency.ARS,
                country = AndesCountry.UY
            )
        )
        val clickMotionEvent = MotionEventBuilder
            .newBuilder()
            .setAction(MotionEvent.ACTION_UP)
            .build()

        clickMotionEvent.action assertEquals MotionEvent.ACTION_UP
        val isEventIntercepted = moneyAmount.dispatchTouchEvent(clickMotionEvent)

        Assert.assertFalse(isEventIntercepted)
        verify(moneyAmount, times(1)).dispatchTouchEvent(clickMotionEvent)
    }

    @Test
    fun `set semibold changes moneyamount text typeface`() {
        // GIVEN
        setupTypeface()
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.0,
            currency = AndesMoneyAmountCurrency.ARS
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))

        // WHEN
        moneyAmount.semiBold = true

        // THEN
        binding.moneyAmountText().typeface assertEquals Typeface.DEFAULT_BOLD
    }

    @Test
    fun `default moneyamount text typeface is regular`() {
        // GIVEN
        setupTypeface()
        val moneyAmount = AndesMoneyAmount(
            context = context,
            amount = 1234.0,
            currency = AndesMoneyAmountCurrency.ARS
        )
        val binding = AndesLayoutMoneyAmountBinding.bind(moneyAmount.getChildAt(0))

        // THEN
        binding.moneyAmountText().typeface assertEquals Typeface.DEFAULT
    }

    fun AndesLayoutMoneyAmountBinding.moneyAmountText(): TextView =
        root.findViewById(R.id.money_amount_text)

    private fun setupTypeface() {
        mockkStatic("com.mercadolibre.android.andesui.typeface.FontKtxKt")
        every { context.getFontOrDefault(R.font.andes_font_semibold) } returns Typeface.DEFAULT_BOLD
        every { context.getFontOrDefault(R.font.andes_font_regular) } returns Typeface.DEFAULT
    }
}
