package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.utils.assertEquals
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboAttrsParser
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesMoneyAmountComboAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesMoneyAmountComboAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `All enum SIZE have an xml attribute`() {
        val enumToAttrs = AndesMoneyAmountComboSize.values().associateBy { size -> size.name.toLowerCase() }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountComboSize, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountSize assertEquals entry.value
        }
    }

    @Test
    fun `All enum COUNTRY have an xml attribute`() {
        val enumToAttrs = AndesCountry.values().associateBy { size -> size.name }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountComboCountry, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountCountry assertEquals entry.value
        }
    }

    @Test
    fun `All attributes parsed correctly`() {
        val size = AndesMoneyAmountComboSize.SIZE_36
        val currency = AndesMoneyAmountCurrency.BRL
        val country = AndesCountry.BR

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesMoneyAmountComboAmount, "1234.56")
            .addAttribute(R.attr.andesMoneyAmountComboCountry, "BR")
            .addAttribute(R.attr.andesMoneyAmountComboCurrency, "BRL")
            .addAttribute(R.attr.andesMoneyAmountComboDiscount, "70")
            .addAttribute(R.attr.andesMoneyAmountComboPreviousAmount, "2345.67")
            .addAttribute(R.attr.andesMoneyAmountComboSize, "size_36")
            .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        Assert.assertEquals(size, andesMoneyAmountAttrs.andesMoneyAmountSize)
        Assert.assertEquals(currency, andesMoneyAmountAttrs.andesMoneyAmountCurrency)
        Assert.assertEquals(country, andesMoneyAmountAttrs.andesMoneyAmountCountry)
        Assert.assertEquals(70, andesMoneyAmountAttrs.andesMoneyDiscount)
        Assert.assertEquals(2345.67, andesMoneyAmountAttrs.andesMoneyPreviousAmount, 1.0)
        Assert.assertEquals(1234.56, andesMoneyAmountAttrs.andesMoneyAmount, 1.0)
    }

    @Test
    fun `Default attributes values`() {
        val size = AndesMoneyAmountComboSize.SIZE_24
        val currency = AndesMoneyAmountCurrency.ARS
        val country = AndesCountry.AR

        val attrs = Robolectric.buildAttributeSet()
            .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        Assert.assertEquals(size, andesMoneyAmountAttrs.andesMoneyAmountSize)
        Assert.assertEquals(currency, andesMoneyAmountAttrs.andesMoneyAmountCurrency)
        Assert.assertEquals(country, andesMoneyAmountAttrs.andesMoneyAmountCountry)
        Assert.assertEquals(0, andesMoneyAmountAttrs.andesMoneyDiscount)
        Assert.assertEquals(0.0, andesMoneyAmountAttrs.andesMoneyPreviousAmount, 1.0)
        Assert.assertEquals(0.0, andesMoneyAmountAttrs.andesMoneyAmount, 1.0)
    }
}
