package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountDiscountTest {

    private var context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun `AndesMoneyAmountDiscount construction correctly`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )

        andesDiscount.text assertEquals "10% OFF"
        andesDiscount.textSize assertEquals context.resources.getDimension(R.dimen.andes_text_size_12)
    }

    @Test
    fun `AndesMoneyAmountDiscount discount greater than 100 throws exception`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )

        Assert.assertThrows(IllegalArgumentException::class.java) { andesDiscount.discount = 101 }
    }

    @Test
    fun `AndesMoneyAmountDiscount discount lower than 0 throws exception`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )

        Assert.assertThrows(IllegalArgumentException::class.java) { andesDiscount.discount = -1 }
    }

    @Test
    fun `AndesMoneyAmountDiscount discount 100 works correctly`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )

        andesDiscount.discount = 100

        andesDiscount.text assertEquals "100% OFF"
    }

    @Test
    fun `AndesMoneyAmountDiscount discount 0 works correctly`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )

        andesDiscount.discount = 0

        andesDiscount.text assertEquals "0% OFF"
    }
}
