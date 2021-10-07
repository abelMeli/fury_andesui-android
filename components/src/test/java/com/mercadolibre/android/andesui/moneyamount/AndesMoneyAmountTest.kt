package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import android.text.SpannedString
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.utils.MockConfigProvider
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

    @Test(expected = IllegalArgumentException::class)
    fun `Build SUPERSCRIPT, SIZE_14 throws exception`() {
        AndesMoneyAmount(
            context = context,
            amount = 100.0,
            currency = AndesMoneyAmountCurrency.ARS,
            size = AndesMoneyAmountSize.SIZE_14,
            style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        )
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
        moneyAmount.text.toString() assertEquals "- $ 1.235"
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
        moneyAmount.text.toString() assertEquals "$ 1.234"
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
        moneyAmount.text.toString() assertEquals "$ 1,234.00"
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
        moneyAmount.text.toString() assertEquals "- $ 1,23415"
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
        moneyAmount.text.toString() assertEquals "$ 1,23415"
        (moneyAmount.text as SpannedString).getSpans(
            0,
            moneyAmount.text.length,
            Any::class.java
        ).size assertEquals 2 // two spans: superscript + strike
    }
}
