package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrsParser
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
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
class AndesMoneyAmountDiscountAttrsParserTest {

    private lateinit var context: Context
    private val attrsParser = AndesMoneyAmountDiscountAttrsParser

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
                .addAttribute(R.attr.andesMoneyAmountDiscountSize, entry.key)
                .build()
            val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
            andesMoneyAmountAttrs.discountSize assertEquals entry.value
        }
    }

    @Test
    fun `All attributes correctly parsed`() {
        val size = AndesMoneyAmountSize.SIZE_48

        val attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.andesMoneyAmountDiscountSize, "size_48")
                .addAttribute(R.attr.andesMoneyDiscount, "50")
                .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        Assert.assertEquals(size, andesMoneyAmountAttrs.discountSize)
        Assert.assertEquals(50, andesMoneyAmountAttrs.discount)
    }

    @Test
    fun `Default attributes values`() {
        val size = AndesMoneyAmountSize.SIZE_24

        val attrs = Robolectric.buildAttributeSet()
            .build()

        val andesMoneyAmountAttrs = attrsParser.parse(context, attrs)
        Assert.assertEquals(size, andesMoneyAmountAttrs.discountSize)
        Assert.assertEquals(0, andesMoneyAmountAttrs.discount)
    }
}
