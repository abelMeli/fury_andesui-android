package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.currency.AndesCurrencyHelper
import com.mercadolibre.android.andesui.moneyamount.MoneyAmountUtils.formatMoneyAmount
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountConfiguration {

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private val configFactory = spy(AndesMoneyAmountConfigurationFactory)
    private lateinit var attrs: AndesMoneyAmountAttrs

    init {
        MockConfigProvider.configure()
    }

    @Test
    fun `Size_18`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_18,
            andesMoneyAmountType = AndesMoneyAmountType.PREVIOUS,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.EUR,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_18))
    }

    @Test
    fun `Size_24`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_24,
            andesMoneyAmountType = AndesMoneyAmountType.NEGATIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NONE,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.COP,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_24))
    }

    @Test
    fun `Size_32`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_32,
            andesMoneyAmountType = AndesMoneyAmountType.POSITIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.CRC,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_32))
    }

    @Test
    fun `Size_36`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_36,
            andesMoneyAmountType = AndesMoneyAmountType.PREVIOUS,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NORMAL,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.GTQ,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_36))
    }

    @Test
    fun `Size_40`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_40,
            andesMoneyAmountType = AndesMoneyAmountType.NEGATIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NONE,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.BRL,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_40))
    }

    @Test
    fun `Size_44`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_44,
            andesMoneyAmountType = AndesMoneyAmountType.POSITIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NORMAL,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.DOP,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, context.resources.getDimension(R.dimen.andes_text_size_44))
    }

    @Test
    fun `Positive`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.fromString("SIZE_18"),
            andesMoneyAmountType = AndesMoneyAmountType.POSITIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.MXN,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        val moneyAmount = MoneyAmountUtils.formatAmount(
            amount = attrs.andesMoneyAmount,
            decimalPlaces = config.currencyInfo.decimalPlaces,
            currencySymbol = config.currencyInfo.symbol,
            country = config.countryInfo
        )
        assertEquals(false, moneyAmount.contains("-"))
    }

    @Test
    fun `Negative`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_52,
            andesMoneyAmountType = AndesMoneyAmountType.NEGATIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.VEF,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)

        val moneyAmount = formatMoneyAmount(
            context,
            attrs.andesMoneyAmountDecimalsStyle.style.getAmountStyled(
                attrs.andesMoneyAmount,
                config.countryInfo,
                config.currencyInfo,
                attrs.andesShowZerosDecimal,
                attrs.andesMoneyAmountSize.size.superScriptSize(context)
            ),
            attrs.andesMoneyAmountType,
            null,
            0F,
            0F,
            AndesColor(R.color.andes_bg_color_primary)
        )
        assertEquals(true, moneyAmount.contains("-"))
    }

    @Test
    fun `None`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_40,
            andesMoneyAmountType = AndesMoneyAmountType.NEGATIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.fromString("NONE"),
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.BOB,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        val countryInfo = AndesCurrencyHelper.getCountry(attrs.andesMoneyAmountCountry)
        val moneyAmount = formatMoneyAmount(
            context,
            attrs.andesMoneyAmountDecimalsStyle.style.getAmountStyled(
                attrs.andesMoneyAmount,
                config.countryInfo,
                config.currencyInfo,
                attrs.andesShowZerosDecimal,
                attrs.andesMoneyAmountSize.size.superScriptSize(context)
            ),
            attrs.andesMoneyAmountType,
            null,
            0F,
            0F,
            AndesColor(R.color.andes_bg_color_primary)
        )
        assertEquals(false, moneyAmount.contains(countryInfo.decimalSeparator))
    }

    @Test
    fun `Normal`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.56,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.fromString("SIZE_44"),
            andesMoneyAmountType = AndesMoneyAmountType.NEGATIVE,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NORMAL,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.USD,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        val countryInfo = AndesCurrencyHelper.getCountry(attrs.andesMoneyAmountCountry)
        val moneyAmount = MoneyAmountUtils.formatAmount(
            amount = attrs.andesMoneyAmount,
            decimalPlaces = config.currencyInfo.decimalPlaces,
            currencySymbol = config.currencyInfo.symbol,
            country = config.countryInfo
        )
        assertEquals(true, moneyAmount.contains(countryInfo.decimalSeparator))
    }

    @Test
    fun `Normal without ShowZerosDecimal`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.00,
            andesShowZerosDecimal = true,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_60,
            andesMoneyAmountType = AndesMoneyAmountType.PREVIOUS,
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.fromString("NORMAL"),
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.UYU,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        val countryInfo = AndesCurrencyHelper.getCountry(attrs.andesMoneyAmountCountry)
        val moneyAmount = MoneyAmountUtils.formatAmount(
            amount = attrs.andesMoneyAmount,
            decimalPlaces = config.currencyInfo.decimalPlaces,
            currencySymbol = config.currencyInfo.symbol,
            country = config.countryInfo
        )
        assertEquals(true, moneyAmount.contains(countryInfo.decimalSeparator))
    }

    @Test
    fun `Normal with ShowZerosDecimal`() {
        attrs = AndesMoneyAmountAttrs(
            andesMoneyAmount = 1234.00,
            andesShowZerosDecimal = false,
            andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_48,
            andesMoneyAmountType = AndesMoneyAmountType.fromString("POSITIVE"),
            andesMoneyAmountDecimalsStyle = AndesMoneyAmountDecimalsStyle.NORMAL,
            andesMoneyAmountCurrency = AndesMoneyAmountCurrency.CLF,
            andesMoneyAmountCountry = AndesCountry.AR,
            andesShowIcon = false,
            andesSuffix = null,
            andesSuffixAccessibility = null
        )
        val config = configFactory.create(context, attrs)
        val countryInfo = AndesCurrencyHelper.getCountry(attrs.andesMoneyAmountCountry)
        val moneyAmount = formatMoneyAmount(
            context,
            attrs.andesMoneyAmountDecimalsStyle.style.getAmountStyled(
                attrs.andesMoneyAmount,
                config.countryInfo,
                config.currencyInfo,
                attrs.andesShowZerosDecimal,
                attrs.andesMoneyAmountSize.size.superScriptSize(context)
            ),
            attrs.andesMoneyAmountType,
            null,
            0F,
            0F,
            AndesColor(R.color.andes_bg_color_primary)
        )
        assertEquals(false, moneyAmount.contains(countryInfo.decimalSeparator))
    }
}
