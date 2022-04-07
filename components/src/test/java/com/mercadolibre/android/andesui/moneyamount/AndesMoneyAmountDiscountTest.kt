package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import android.view.View
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.R
import com.mercadolibre.android.andesui.assertEquals
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import kotlinx.android.synthetic.main.andes_layout_money_amount.view.money_amount_text
import kotlinx.android.synthetic.main.andes_layout_money_amount.view.money_amount_icon
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
        andesDiscount.money_amount_text.text.toString() assertEquals "10% OFF"
        andesDiscount.money_amount_text.textSize assertEquals context.resources.getDimension(R.dimen.andes_text_size_12)
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
        andesDiscount.money_amount_text.text.toString() assertEquals "100% OFF"
    }

    @Test
    fun `AndesMoneyAmountDiscount discount 0 works correctly`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )
        andesDiscount.discount = 0
        andesDiscount.money_amount_text.text.toString() assertEquals "0% OFF"
    }

    @Test
    fun `AndesMoneyAmountDiscount discount with icon`() {
        context.getDrawable(R.drawable.andes_comunicacion_answer_16)?.let {
            val andesDiscount = AndesMoneyAmountDiscount(
                context = context,
                discount = 10,
                size = AndesMoneyAmountSize.SIZE_12,
                icon = it
            )
            andesDiscount.money_amount_text.text.toString() assertEquals "10% OFF"
            andesDiscount.money_amount_icon.visibility assertEquals View.VISIBLE
        }
    }

    @Test
    fun `AndesMoneyAmountDiscount discount without icon`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )
        andesDiscount.money_amount_text.text.toString() assertEquals "10% OFF"
        andesDiscount.money_amount_icon.visibility assertEquals View.GONE
    }

    @Test
    fun `AndesMoneyAmountDiscount size greater than 32 throws exception`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            context.getDrawable(R.drawable.andes_comunicacion_answer_16)?.let {
                AndesMoneyAmountDiscount(
                    context = context,
                    discount = 10,
                    size = AndesMoneyAmountSize.SIZE_36,
                    icon = it
                )
            }
        }
    }

    @Test
    fun `AndesMoneyAmountDiscount size greater than 32 throws exception setter`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            context.getDrawable(R.drawable.andes_comunicacion_answer_16)?.let {
                val amountDiscount = AndesMoneyAmountDiscount(
                    context = context,
                    discount = 10,
                    size = AndesMoneyAmountSize.SIZE_24,
                    icon = it
                )
                amountDiscount.size = AndesMoneyAmountSize.SIZE_40
            }
        }
    }

    @Test
    fun `AndesMoneyAmountDiscount getContent works correctly`() {
        val andesDiscount = AndesMoneyAmountDiscount(
            context = context,
            discount = 10,
            size = AndesMoneyAmountSize.SIZE_12
        )
        andesDiscount.contentDescription assertEquals "10 ${andesDiscount.resources.getString(R.string.andes_money_amount_discount_accessibility)}"
    }
}
