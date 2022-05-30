package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.discount.AndesMoneyAmountDiscountConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.utils.Constants.TEST_ANDROID_VERSION_CODE
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [TEST_ANDROID_VERSION_CODE])
class AndesMoneyAmountDiscountConfiguration {

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private val configFactory = spy(AndesMoneyAmountDiscountConfigurationFactory)
    private lateinit var attrs: AndesMoneyAmountDiscountAttrs

    @Test
    fun `Size size_18`() {
        attrs = AndesMoneyAmountDiscountAttrs(
            discount = 15,
            discountSize = AndesMoneyAmountSize.SIZE_18,
            discountIcon = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, context.resources.getDimension(R.dimen.andes_text_size_18))
    }

    @Test
    fun `Size size_24`() {
        attrs = AndesMoneyAmountDiscountAttrs(
            discount = 15,
            discountSize = AndesMoneyAmountSize.SIZE_24,
            discountIcon = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, context.resources.getDimension(R.dimen.andes_text_size_24))
    }

    @Test
    fun `Discount`() {
        attrs = AndesMoneyAmountDiscountAttrs(
            discount = 15,
            discountSize = AndesMoneyAmountSize.SIZE_24,
            discountIcon = null
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discount, 15)
    }
}
