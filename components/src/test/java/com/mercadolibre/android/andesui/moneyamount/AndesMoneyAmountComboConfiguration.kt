package com.mercadolibre.android.andesui.moneyamount

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mercadolibre.android.andesui.country.AndesCountry
import com.mercadolibre.android.andesui.moneyamount.combosize.AndesMoneyAmountComboSize
import com.mercadolibre.android.andesui.moneyamount.currency.AndesMoneyAmountCurrency
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboAttrs
import com.mercadolibre.android.andesui.moneyamount.factory.combo.AndesMoneyAmountComboConfigurationFactory
import com.mercadolibre.android.andesui.moneyamount.size.AndesMoneyAmountSize
import com.mercadolibre.android.andesui.utils.MockConfigProvider
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class AndesMoneyAmountComboConfiguration {

    init {
        MockConfigProvider.configure()
    }

    private var context = ApplicationProvider.getApplicationContext<Context>()
    private val configFactory = spy(AndesMoneyAmountComboConfigurationFactory)
    private lateinit var attrs: AndesMoneyAmountComboAttrs

    @Test
    fun `Discount 10`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.VEF,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.fromString("SIZE_24")
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discount, 10)
    }

    @Test
    fun `Discount size_14`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.PAB,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_24
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, AndesMoneyAmountSize.SIZE_14)
    }

    @Test
    fun `Discount size_18`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.PEN,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_36
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.discountSize, AndesMoneyAmountSize.SIZE_18)
    }

    @Test
    fun `Previous amount size_12`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.PYG,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.fromString("SIZE_24")
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.previousAmountSize, AndesMoneyAmountSize.SIZE_12)
    }

    @Test
    fun `Previous amount size_16`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.NIO,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.fromString("SIZE_36")
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.previousAmountSize, AndesMoneyAmountSize.SIZE_16)
    }

    @Test
    fun `Amount size_12`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.VEF,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_24
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, AndesMoneyAmountSize.SIZE_24)
    }

    @Test
    fun `Amount size_16`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.NIO,
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_36
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amountSize, AndesMoneyAmountSize.SIZE_36)
    }

    @Test
    fun `Amount`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.fromString("GTQ"),
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_36
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.amount, 1350.0, DELTA)
    }

    @Test
    fun `Previous amount`() {
        attrs = AndesMoneyAmountComboAttrs(
                andesMoneyAmountCurrency = AndesMoneyAmountCurrency.fromString("HNL"),
                andesMoneyAmount = 1350.0,
                andesMoneyPreviousAmount = 1500.0,
                andesMoneyDiscount = 10,
                andesMoneyAmountCountry = AndesCountry.AR,
                andesMoneyAmountSize = AndesMoneyAmountComboSize.SIZE_36
        )
        val config = configFactory.create(context, attrs)
        assertEquals(config.previousAmount, 1500.0, DELTA)
    }

    @Test
    fun `AndesMoneyAmountCombo with crypto currency throws exception`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            AndesMoneyAmountCombo(
                context = context,
                amount = 1350.0,
                currency = AndesMoneyAmountCurrency.USDP,
                country = AndesCountry.AR,
                previousAmount = 1500.0,
                discount = 10,
                size = AndesMoneyAmountComboSize.SIZE_36
            )
        }
    }

    @Test
    fun `AndesMoneyAmountCombo with crypto currency throws exception setter`() {
        val andesMoneyAmountCombo = AndesMoneyAmountCombo(
            context = context,
            amount = 1350.0,
            currency = AndesMoneyAmountCurrency.ARS,
            country = AndesCountry.AR,
            previousAmount = 1500.0,
            discount = 10,
            size = AndesMoneyAmountComboSize.SIZE_36
        )
        Assert.assertThrows(IllegalArgumentException::class.java) {
            andesMoneyAmountCombo.currency = AndesMoneyAmountCurrency.USDP
        }
    }

    companion object {
        private const val DELTA = 1e-15
    }
}
