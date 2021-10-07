package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountDiscountConfiguration {

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private val configFactory = spy(AndesMoneyAmountDiscountConfigurationFactory)
    private lateinit var attrs: AndesMoneyAmountDiscountAttrs

    @Test
    fun `Size size_18`() {
        attrs = AndesMoneyAmountDiscountAttrs(
                andesMoneyDiscount = 15,
                andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_18
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, context.resources.getDimension(R.dimen.andes_text_size_18))
    }

    @Test
    fun `Size size_24`() {
        attrs = AndesMoneyAmountDiscountAttrs(
                andesMoneyDiscount = 15,
                andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_24
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, context.resources.getDimension(R.dimen.andes_text_size_24))
    }

    @Test
    fun `Discount`() {
        attrs = AndesMoneyAmountDiscountAttrs(
                andesMoneyDiscount = 15,
                andesMoneyAmountSize = AndesMoneyAmountSize.SIZE_24
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discount, 15)
    }
}
