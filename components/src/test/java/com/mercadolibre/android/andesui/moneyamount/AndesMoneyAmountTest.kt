package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.databinding.AndesLayoutMoneyAmountBinding
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
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
            (moneyAmountText.text as SpannableString).getSpans(
                0,
                moneyAmountText.text.length,
                Any::class.java
            ).size assertEquals 4 // two spans: amountsize + superscript + strike + size
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

    fun AndesLayoutMoneyAmountBinding.moneyAmountText(): TextView =
        root.findViewById(R.id.money_amount_text)

}
