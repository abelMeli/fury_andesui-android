package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.decimalstyle.AndesMoneyAmountDecimalsStyle
import com.mercadolibre.android.andesui.moneyamount.factory.amount.AndesMoneyAmountAttrsParser
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.moneyamount.type.AndesMoneyAmountType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesMoneyAmountAttrsParser

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `All enum SIZE have an xml attribute`() {
        val enumToAttrs =
            AndesMoneyAmountSize.values().associateBy { size -> size.name.toLowerCase() }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountSize, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountSize assertEquals entry.value
        }
    }

    @Test
    fun `All enum DECIMALSSTYLE have an xml attribute`() {
        val enumToAttrs =
            AndesMoneyAmountDecimalsStyle.values().associateBy { size -> size.name.toLowerCase() }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountStyle, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle assertEquals entry.value
        }
    }

    @Test
    fun `All enum TYPE have an xml attribute`() {
        val enumToAttrs =
            AndesMoneyAmountType.values().associateBy { size -> size.name.toLowerCase() }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountType, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountType assertEquals entry.value
        }
    }

    @Test
    fun `All enum CURRENCY have an xml attribute`() {
        val enumToAttrs = AndesMoneyAmountCurrency.values().associateBy { size -> size.name }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountCurrency, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountCurrency assertEquals entry.value
        }
    }

    @Test
    fun `All enum COUNTRY have an xml attribute`() {
        val enumToAttrs = AndesCountry.values().associateBy { size -> size.name }
        enumToAttrs.forEach { entry ->
            val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountCountry, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.andesMoneyAmountCountry assertEquals entry.value
        }
    }

    @Test
    fun `All attributes parsed correctly`() {
        val size = AndesMoneyAmountSize.SIZE_32
        val type = AndesMoneyAmountType.NEGATIVE
        val style = AndesMoneyAmountDecimalsStyle.SUPERSCRIPT
        val currency = AndesMoneyAmountCurrency.BRL
        val country = AndesCountry.BR

        val attrs = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.andesMoneyAmountSize, "size_32")
            .addAttribute(R.attr.andesMoneyAmountType, "negative")
            .addAttribute(R.attr.andesMoneyAmountStyle, "superscript")
            .addAttribute(R.attr.andesMoneyAmountCurrency, "BRL")
            .addAttribute(R.attr.andesMoneyAmountCountry, "BR")
            .addAttribute(R.attr.andesMoneyAmount, "1234.56")
            .addAttribute(R.attr.andesShowZerosDecimal, "true")
            .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        size assertEquals andesMoneyAmountAttrs.andesMoneyAmountSize
        type assertEquals andesMoneyAmountAttrs.andesMoneyAmountType
        style assertEquals andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle
        currency assertEquals andesMoneyAmountAttrs.andesMoneyAmountCurrency
        country assertEquals andesMoneyAmountAttrs.andesMoneyAmountCountry
        true assertEquals andesMoneyAmountAttrs.andesShowZerosDecimal
        Assert.assertEquals(1234.56, andesMoneyAmountAttrs.andesMoneyAmount, 1.0)
    }

    @Test
    fun `Default attribute values`() {
        val size = AndesMoneyAmountSize.SIZE_24
        val type = AndesMoneyAmountType.POSITIVE
        val style = AndesMoneyAmountDecimalsStyle.NORMAL
        val currency = AndesMoneyAmountCurrency.ARS

        val attrs = Robolectric.buildAttributeSet()
            .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        size assertEquals andesMoneyAmountAttrs.andesMoneyAmountSize
        type assertEquals andesMoneyAmountAttrs.andesMoneyAmountType
        style assertEquals andesMoneyAmountAttrs.andesMoneyAmountDecimalsStyle
        currency assertEquals andesMoneyAmountAttrs.andesMoneyAmountCurrency
    }
}
